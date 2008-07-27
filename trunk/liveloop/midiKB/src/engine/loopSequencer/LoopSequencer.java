package engine.loopSequencer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import com.marco.utils.JobProcessor;
import com.marco.utils.Log;
import com.marco.utils.Utils;



import sun.security.krb5.internal.Ticket;

import engine.api.MidiEffect;
import engine.boxes.output.KeyboardStatus;
import engine.loopSequencer.sequenceSystem.MultipleEvent;
import engine.loopSequencer.sequenceSystem.SimpleSequence;
import engine.loopSequencer.sequenceSystem.SimpleTrack;
import engine.utils.KeyBoardState;

public class LoopSequencer extends MidiEffect{

	protected SimpleSequence sequence;

	protected class SeqTask extends TimerTask{
		long max;
		long tick;
		SeqTask(){
			tick=0;
			max=sequence.getTickLength();
		}
		public void run(){
			doRun(tick);
			tick=(tick+1)%(max);//+1= MODIF pour jouer la derniere note de la sequence

		}
	}
	protected int tempo;
	protected SeqTask task;
	protected Timer timer;
	protected boolean isRecording;
	protected long timeRecording;
	
	protected int recordingTrack;
	
	protected KeyboardStatus kbstate;
	
	/*static class MidiEventInTrack{
		public MidiEvent event;
		public Track track;
		public MidiEventInTrack(MidiEvent event, Track track) {
			this.event = event;
			this.track = track;
		}	
	}*/
	
	/*static class TrackInfo{

		public long clearTick;//set when a track was started to be cleared
		//public float volume;
		public int eventNum;
		public TrackInfo(float volume) {
			//this.volume = volume;
		}
		
	}*/
	//protected List<TrackInfo> infos=new ArrayList<TrackInfo>();
	
	
	//protected Map<Long,List<MidiEventInTrack>> events=new HashMap<Long, List<MidiEventInTrack>>();
	
	
	protected JobProcessor fillEvents=new JobProcessor();
	
	public LoopSequencer(int tempo){
		this.tempo=tempo;
		this.kbstate=new KeyboardStatus();
		this.addReceiver(kbstate);		
	}
	public void setSequence(SimpleSequence s){
		this.sequence=s;
		
		if(this.isRecording)
			throw new IllegalStateException("DEvice is recording");
		/*this.infos.clear();
		for(Track t : this.sequence.getTracks()){
			this.infos.add(new TrackInfo(1.0f));
		}*/
		
	}
	
	public void close() {
		this.timer.cancel();
		this.timer.purge();
	}
	
	
	public void send(MidiMessage mes, long times) {
		if(!isRecording)
			return;
		
		
		
		//final Track t=this.sequence.getTracks()[this.recordingTrack];
		//System.out.println("________________"+this.sequence.getTickLength());
		/*if(kbstate.isPressed(mes)){
			return;
			//don't record a note that is already pressed
		}*/
		
		final MidiMessage mess=mes;
		final long po=getTickPosition();
		MidiEvent v=new MidiEvent(mess,po);
		sequence.getTrack(this.recordingTrack).add(v);
		
		
		/*int size=fillEvents.addJob(new Runnable(){
			public void run() {
				final MidiEvent v=new MidiEvent(mess,po);
				t.add(v);
			}
		}, false);*/
		//System.out.println(size);	
		//register(t,v);
		
		
	}
	
	
	

	
	public long tickToTime(long tick){
		return (long)((float)(1000.0f*60.0f/tempo)*(float)tick/(float)sequence.getResolution());
	}
	protected long timeToTick(long time){
		return (long)((float)(time*sequence.getResolution()*tempo)/((float)(1000*60)));
	}
	
	public void setRecordingTrack(int recordingTrack){
		if(recordingTrack<0){
			this.isRecording=false;
			return;			
		}
		this.recordingTrack=recordingTrack;
	}
	public void setRecording(boolean recording){
		System.out.println("Recording="+recording);
		isRecording=recording;		
	}

	protected int getPeriod(){
		return (int)(((float)(tempo*1000)/60.0f)/(float)sequence.getResolution());
	}
	public void start(){
		//fill();
		this.task=new SeqTask();
		timer=new Timer(false);

		timer.scheduleAtFixedRate(task,0, getPeriod());
		
		timeRecording=System.currentTimeMillis();//recording time is always at the begininning of the sequence

	}
	public void stop(){
		timer.cancel();
		timer.purge();
		timer=null;
	}

	/*protected List<MidiEventInTrack> get(long tick){
		List<MidiEventInTrack> l=events.get(tick);
		if(l==null){
			l=new ArrayList<MidiEventInTrack>();
			events.put(tick, l);
		}
		return l;
	}
	protected void fill(){
		events.clear();
		for(Track t : sequence.getTracks()){
			for(int i=0;i<t.size();i++){
				MidiEvent event=t.get(i);
				register(t,event);
			}
		}
	}
	public void doRun2(long tick){
		List<MidiEventInTrack> toplay=null;
		synchronized (events) {
			toplay=events.get(tick);
		}
		if(toplay!=null){
			synchronized (toplay) {
				for(MidiEventInTrack ev : toplay){					
					sendToAll(ev.event.getMessage(), 0);
				}
			}			
		}		
	}
	
	protected void register(Track parent,MidiEvent event){
		List<MidiEventInTrack> l;
		synchronized (events) {
			l=get(event.getTick());			
		}
		synchronized (l) {
			l.add(new MidiEventInTrack(event,parent));
		}
	}
	*/
	public void clearTrack(int num){
		this.sequence.createTrack(num);
	}

	
	/*public void doRun(long tick){
		Track[] tracks=this.sequence.getTracks();
		for(int i=0;i<tracks.length;i++){
			TrackInfo info=infos.get(i);
			
			if(info.eventNum==tracks[i].size()-2){//overpass the last unused midievent
				info.eventNum=0;
			}
			
			
			MidiEvent ev=tracks[i].get(info.eventNum);
			
			
			while(ev.getTick()<tick ){
				 ev=tracks[i].get(info.eventNum);
				 info.eventNum=(info.eventNum+1)%(tracks[i].size()-1);
			}
			if(ev.getTick()==tick){
				//event must be played
				sendToAll(ev.getMessage(), 0);
				info.eventNum=(info.eventNum+1)%(tracks[i].size()-1);
			}			
		}		
	}*/
	public void doRun(long tick){
		for(int i=0;i<sequence.getNbTrack();i++){
			SimpleTrack t=sequence.getTrack(i);
			if(t==null)
				continue;
			MultipleEvent ev=t.get(tick);
			Utils.initTime();
			if(ev==null)
				continue;
			if(ev.getLength()==1){
				sendToAll(ev.getMessage(), 0);
			}else{				
				List<MidiMessage> messages=ev.getMessages();
				synchronized (messages) {
					for(MidiMessage m:messages){
						sendToAll(m, 0);
					}
				}								
			}
			Utils.printTime("Event playing");
		}		
	}
	
	
	
	
	public long getTickLength() {
		if(this.sequence==null)
			return 0;
		return this.sequence.getTickLength();
	}
	public long getTickPosition() {
		return timeToTick((System.currentTimeMillis()-timeRecording))%sequence.getTickLength();
		//return this.task.tick;
	}
	
	public int getRecordingTrack(){
		return this.recordingTrack;
	}
	
	public float getTempoInBPM() {
		return this.tempo;
	}
	
	/*
	public int[] addControllerEventListener(ControllerEventListener listener,
			int[] controllers) {
		throw new IllegalStateException("Currently not supported");
	}
	public boolean addMetaEventListener(MetaEventListener listener) {
		throw new IllegalStateException("Currently not supported");
	}
	public Info getDeviceInfo() {
		throw new IllegalStateException("Currently not supported");
	}
	public int getLoopCount() {
		return Sequencer.LOOP_CONTINUOUSLY;
	}
	public long getLoopEndPoint() {
		return tickToTime(sequence.getTickLength());
	}
	public long getLoopStartPoint() {
		return 0;
	}
	public SyncMode getMasterSyncMode() {
		return SyncMode.INTERNAL_CLOCK;
	}
	public SyncMode[] getMasterSyncModes() {
		return new SyncMode[]{getMasterSyncMode()};
	}
	public int getMaxReceivers() {
		return Integer.MAX_VALUE;
	}
	public int getMaxTransmitters() {
		return Integer.MAX_VALUE;
	}
	public long getMicrosecondLength() {
		if(this.sequence==null)
			return 0;
		return tickToTime(sequence.getTickLength())*1000;
	}
	public long getMicrosecondPosition() {
		return (System.currentTimeMillis()-timeRecording)*1000;
	}
	
	public Receiver getReceiver() throws MidiUnavailableException {
		return this;
	}
	public Sequence getSequence() {
		return this.sequence;
	}
	public SyncMode getSlaveSyncMode() {
		return SyncMode.INTERNAL_CLOCK;
	}
	public SyncMode[] getSlaveSyncModes() {
		return new SyncMode[]{SyncMode.INTERNAL_CLOCK};
	}
	public float getTempoFactor() {
		return 1.0f;
	}
	
	public float getTempoInMPQ() {
		throw new IllegalStateException("Currently not supported");
	}
	
	public boolean getTrackMute(int track) {//not implemented
		return false;
	}
	public boolean getTrackSolo(int track) {//not implemented		
		return false;
	}
	public Transmitter getTransmitter() throws MidiUnavailableException {
		throw new IllegalStateException("Currently not supported");
	}
	public List<Transmitter> getTransmitters() {
		throw new IllegalStateException("Currently not supported");
	}
	public boolean isOpen() {
		return true;//always opened;
	}
	public boolean isRecording() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}
	public void open() throws MidiUnavailableException {
		// TODO Auto-generated method stub
		
	}
	public void recordDisable(Track track) {
		// TODO Auto-generated method stub
		
	}
	public void recordEnable(Track track, int channel) {
		// TODO Auto-generated method stub
		
	}
	public int[] removeControllerEventListener(
			ControllerEventListener listener, int[] controllers) {
		// TODO Auto-generated method stub
		return null;
	}
	public void removeMetaEventListener(MetaEventListener listener) {
		// TODO Auto-generated method stub
		
	}
	public void setLoopCount(int count) {
		// TODO Auto-generated method stub
		
	}
	public void setLoopEndPoint(long tick) {
		// TODO Auto-generated method stub
		
	}
	public void setLoopStartPoint(long tick) {
		// TODO Auto-generated method stub
		
	}
	public void setMasterSyncMode(SyncMode sync) {
		// TODO Auto-generated method stub
		
	}
	public void setMicrosecondPosition(long microseconds) {
		// TODO Auto-generated method stub
		
	}
	public void setSequence(InputStream stream) throws IOException,
			InvalidMidiDataException {
		// TODO Auto-generated method stub
		
	}
	public void setSlaveSyncMode(SyncMode sync) {
		// TODO Auto-generated method stub
		
	}
	public void setTempoFactor(float factor) {
		// TODO Auto-generated method stub
		
	}
	public void setTempoInBPM(float bpm) {
		// TODO Auto-generated method stub
		
	}
	public void setTempoInMPQ(float mpq) {
		// TODO Auto-generated method stub
		
	}
	public void setTickPosition(long tick) {
		// TODO Auto-generated method stub
		
	}
	public void setTrackMute(int track, boolean mute) {
		// TODO Auto-generated method stub
		
	}
	public void setTrackSolo(int track, boolean solo) {
		// TODO Auto-generated method stub
		
	}
	public void startRecording() {
		// TODO Auto-generated method stub
		
	}
	public void stopRecording() {
		// TODO Auto-generated method stub
		
	}
	*/
	
	
	
	
	
	
}

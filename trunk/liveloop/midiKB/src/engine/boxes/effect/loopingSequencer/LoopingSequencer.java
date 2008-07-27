package engine.boxes.effect.loopingSequencer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import engine.api.MidiEffect;
import engine.utils.MidiUtils;

public class LoopingSequencer extends MidiEffect{

	protected boolean recording;
	protected Timer timer;
	protected List<Track> tracks;
	protected int resolution;//number of tick by quarter note
	protected long currentTick;
	protected long bpm;
	protected long loopTime;//in milliseconds
	protected Track recordingTrack;
	protected TimerTask task=new TimerTask(){
		
		int counter;
		public void run() {
			counter=(counter+1)%(24/resolution);
			if(counter==0)
				sendTracks();
			sendClock();
		}		
	};
	
	public LoopingSequencer(long bpm,int resolution){
		this.bpm=bpm;
		this.resolution=resolution;		
		if(resolution>24 || resolution<1)
			throw new IllegalArgumentException();
				
	}
	
	public int getClockDelay(){
		return (int)((float)(24*bpm*60)/1000.0f);
	}
	
	public long getNbOfTick(){
		return getNbOfQuarterNote()*resolution;
	}
	
	public long getNbOfQuarterNote(){
		return loopTime*bpm/(60*1000);
	}
	
	protected void sendTracks(){
		currentTick=(currentTick+1)%getNbOfTick();
		for(Track t : tracks){
			ShortMessage mes=t.getMessage(currentTick);
			if(mes!=null)
				sendToAll(mes, System.currentTimeMillis());
		}
	}
	protected void sendClock() {		
		ShortMessage mes=new ShortMessage();
		MidiUtils.setStatus(mes,ShortMessage.TIMING_CLOCK);
		sendToAll(mes, System.currentTimeMillis());
	}
	

	public void addTrack(Track t){
		tracks.add(t);
	}
	
	public void clearCurrent(){
		recordingTrack.clear();
	}
	
	protected long getTickFromTimeStamp(long timeS){
		return timeS*bpm*resolution/(60*1000*1000);		
	}

	public void send(MidiMessage mes, long timeS) {
		if(!recording)
			return;
		if(!(mes instanceof ShortMessage))
			return;
		long tick=getTickFromTimeStamp(timeS);
		recordingTrack.addMessage(tick, (ShortMessage) mes);			
	}
	
	protected void sendStart() {
		ShortMessage mes=new ShortMessage();
		MidiUtils.setStatus(mes,ShortMessage.START);
		sendToAll(mes, 0);		
	}
	protected void sendStop() {
		ShortMessage mes=new ShortMessage();
		MidiUtils.setStatus(mes,ShortMessage.START);
		sendToAll(mes, 0);		
	}
	public void start(){
		sendStart();
		timer.purge();
		timer=new Timer();
		timer.schedule(task,1, getClockDelay());
	}
	
	public void stop(){
		sendStop();
		timer.cancel();
	}
	
	

}

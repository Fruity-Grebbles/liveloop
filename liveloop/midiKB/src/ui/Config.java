package ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.marco.utils.ObjectPropertySerializer;

import engine.api.MidiIn;
import engine.api.MidiOut;
import engine.boxes.effect.ChangeChannel;
import engine.boxes.effect.ChangeVolume;
import engine.boxes.effect.ChannelFilter;
import engine.boxes.effect.Filter;
import engine.boxes.effect.MidiTee;
import engine.boxes.effect.ShortMsgEffect;
import engine.boxes.effect.dispatch.Conditions;
import engine.boxes.input.ClassicInput;
import engine.boxes.output.ClassicOut;
import engine.boxes.output.DumpReceiver;
import engine.loopSequencer.LoopController;
import engine.loopSequencer.LoopPlayer;
import engine.loopSequencer.LoopSequencer;
import engine.loopSequencer.PlayStatus;
import engine.loopSequencer.sequenceSystem.SimpleSequence;
import engine.loopSequencer.sequenceSystem.SimpleTrack;
import engine.utils.MidiConnect;
import engine.utils.MidiUtils;
import engine.utils.Note;

public class Config implements Serializable{
	
	public int recordControl=0;
	public int clearControl=0;
	public int[] trackControls=new int[]{0};
	public int pauseControl=0;
	
	public String input="";
	public String output="";
	public int tempo=0;
	public int resolution=0;
	public int sequencerLen=0;
	
	protected transient Runnable stopTask;
	public void stop(){
		if(stopTask!=null)
			stopTask.run();
	}
	
	
	public void save(String file){		
		//ObjectPropertySerializer.storeObject(this,file);
		FileOutputStream st;
		try {
			st = new FileOutputStream(new File(file));
			ObjectOutputStream o=new ObjectOutputStream(st);
			o.writeObject(this);
			o.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	public static Config load(String file){		
		FileInputStream st;
		try {
			st = new FileInputStream(new File(file));
			ObjectInputStream o=new ObjectInputStream(st);
			Object res=o.readObject();
			return (Config)res;	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	
	
	public void startLooping() throws MidiUnavailableException{
		
		long realLen=this.sequencerLen*this.resolution;
		int nbTrack=this.trackControls.length;
		
		MidiConnect.listDevices();
		
		SimpleSequence s=new SimpleSequence(nbTrack,this.resolution);
		SimpleTrack t=s.createTrack(0);
	
		s.setFixedLength(realLen);
		t.add(new MidiEvent(Note.getMsgON(0, 45, 100),realLen));//never played
		
		
		//DumpKB kb=new DumpKB(100,0);
		final MidiIn in=new ClassicInput(this.input);
		this.stopTask=new Runnable(){
			public void run() {
				in.close();
			}			
		};
		final LoopSequencer sqs=new LoopSequencer(this.tempo);
		sqs.setSequence(s);
		//DumpReceiver r=new DumpReceiver();
		final MidiOut rout=new ClassicOut(this.output);//21
				
		
		sqs.setRecordingTrack(0);
		sqs.setRecording(true);
		
		
		//TODO : implements controllers !!
		PlayStatus st=new PlayStatus(nbTrack);		
		//int[] controls=new int[]{74,71,91,93};
		LoopController lcon=new LoopController(st,this.recordControl,this.pauseControl,this.clearControl,this.trackControls,-1,-1);
		LoopPlayer pl=new LoopPlayer(sqs,st);
		
		Filter notes=new Filter(Conditions.NOTE);
		Filter ctrl=new Filter(Conditions.CTRL);
			
		ShortMsgEffect ch11=new ShortMsgEffect(){
			public void send(ShortMessage msg, long timeS) {
				MidiUtils.setChannel(msg, 11);
				sendToAll(msg, timeS);
			}			
		};
		
		ChannelFilter[] fs=new ChannelFilter[nbTrack];
		for(int i=0;i<nbTrack;i++){
			fs[i]=new ChannelFilter(i);
		}
		/*ChannelFilter f1=new ChannelFilter(0);
		ChannelFilter f2=new ChannelFilter(1);
		ChannelFilter f3=new ChannelFilter(2);
		ChannelFilter f4=new ChannelFilter(3);
		*/
		
		ChangeVolume[] vs=new ChangeVolume[nbTrack];
		for(int i=0;i<nbTrack;i++){
			vs[i]=new ChangeVolume(this.trackControls[i]);
		}
		
		/*ChangeVolume v1=new ChangeVolume(74);
		ChangeVolume v2=new ChangeVolume(71);
		ChangeVolume v3=new ChangeVolume(91);
		ChangeVolume v4=new ChangeVolume(93);
		*/
		
		ChangeChannel cch=new ChangeChannel(this.trackControls);
		
		MidiTee out=new MidiTee();
		
		
		in.addReceiver(notes);
		in.addReceiver(ctrl);
		
		
		//note part
		notes.addReceiver(cch);
		cch.addReceiver(sqs);
		
		//ctrl part
		ctrl.addReceiver(cch);
		ctrl.addReceiver(lcon);
		ctrl.addReceiver(ch11);
		
		
		
		for(int i=0;i<nbTrack;i++){
			sqs.addReceiver(fs[i]);
		}
		/*sqs.addReceiver(f1);
		sqs.addReceiver(f2);
		sqs.addReceiver(f3);
		sqs.addReceiver(f4);
		*/
		
		for(int i=0;i<nbTrack;i++){
			fs[i].addReceiver(vs[i]);
		}
		/*f1.addReceiver(v1);
		f2.addReceiver(v2);
		f3.addReceiver(v3);
		f4.addReceiver(v4);
		*/
		
		for(int i=0;i<nbTrack;i++){
			ctrl.addReceiver(vs[i]);
		}
		/*ctrl.addReceiver(v1);//to send cotrl events
		ctrl.addReceiver(v2);
		ctrl.addReceiver(v3);
		ctrl.addReceiver(v4);
		*/
		
		for(int i=0;i<nbTrack;i++){
			vs[i].addReceiver(out);
		}
		/*v1.addReceiver(out);
		v2.addReceiver(out);
		v3.addReceiver(out);
		v4.addReceiver(out);
		*/
		
		//send the controls on channel11 to out
		ch11.addReceiver(out);
		notes.addReceiver(out);
		
		out.addReceiver(rout);
		out.addReceiver(new DumpReceiver());
		
		this.stopTask=new Runnable(){
			public void run(){
				sqs.close();
				rout.close();
				in.close();
			}
		};
		
		//Start the sequencer
		sqs.start();
		
	}
	
	public static void main(String[] args) {
		Config c=new Config();
		c.trackControls=new int[]{1,2,3};
		c.save("D:/a.txt");
		Config cc=load("D:/a.txt");
		System.out.println(cc.trackControls[0]);
	}

}

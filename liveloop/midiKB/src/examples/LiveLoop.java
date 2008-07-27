package examples;

import java.awt.event.ActionEvent;
import java.lang.management.ThreadInfo;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;


import engine.api.MidiIn;
import engine.api.MidiOut;
import engine.boxes.effect.ChangeChannel;
import engine.boxes.effect.ChangeVolume;
import engine.boxes.effect.ChannelFilter;
import engine.boxes.effect.Filter;
import engine.boxes.effect.MidiTee;
import engine.boxes.effect.ShortMsgEffect;
import engine.boxes.effect.dispatch.Conditions;
import engine.boxes.effect.dispatch.ShortMsgCondition;
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

public class LiveLoop {
	
	public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException {
		MidiConnect.listDevices();
		
		SimpleSequence s=new SimpleSequence(4,8);
		SimpleTrack t=s.createTrack(0);
	
		s.setFixedLength(32);
		t.add(new MidiEvent(Note.getMsgON(0, 45, 100),32));//never played
		
		
		//DumpKB kb=new DumpKB(100,0);
		final MidiIn in=new ClassicInput(8);
		final LoopSequencer sqs=new LoopSequencer(60);
		sqs.setSequence(s);
		//DumpReceiver r=new DumpReceiver();
		MidiOut rout=new ClassicOut(13);//21
				
		sqs.start();
		sqs.setRecordingTrack(0);
		sqs.setRecording(true);
		
		
		//TODO : implements controllers !!
		PlayStatus st=new PlayStatus(4);		
		int[] controls=new int[]{74,71,91,93};
		LoopController lcon=new LoopController(st,25,23,20,controls,22,21);
		LoopPlayer pl=new LoopPlayer(sqs,st);
		
		Filter notes=new Filter(Conditions.NOTE);
		Filter ctrl=new Filter(Conditions.CTRL);
			
		ShortMsgEffect ch11=new ShortMsgEffect(){
			public void send(ShortMessage msg, long timeS) {
				MidiUtils.setChannel(msg, 11);
				sendToAll(msg, timeS);
			}			
		};
		
		ChannelFilter f1=new ChannelFilter(0);
		ChannelFilter f2=new ChannelFilter(1);
		ChannelFilter f3=new ChannelFilter(2);
		ChannelFilter f4=new ChannelFilter(3);
		
		ChangeVolume v1=new ChangeVolume(74);
		ChangeVolume v2=new ChangeVolume(71);
		ChangeVolume v3=new ChangeVolume(91);
		ChangeVolume v4=new ChangeVolume(93);
		
		ChangeChannel cch=new ChangeChannel(controls);
		
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
		
		
		
				
		sqs.addReceiver(f1);
		sqs.addReceiver(f2);
		sqs.addReceiver(f3);
		sqs.addReceiver(f4);
		
		f1.addReceiver(v1);
		f2.addReceiver(v2);
		f3.addReceiver(v3);
		f4.addReceiver(v4);
		
		ctrl.addReceiver(v1);//to send cotrl events
		ctrl.addReceiver(v2);
		ctrl.addReceiver(v3);
		ctrl.addReceiver(v4);
		
		v1.addReceiver(out);
		v2.addReceiver(out);
		v3.addReceiver(out);
		v4.addReceiver(out);
		
		//send the controls on channel11 to out
		ch11.addReceiver(out);
		notes.addReceiver(out);
		
		out.addReceiver(rout);
		out.addReceiver(new DumpReceiver());
		
		JFrame j=new JFrame();
		j.getContentPane().add(new JButton(new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				in.close();
				sqs.close();				
			}			
		}));
		j.pack();
		j.setLocationRelativeTo(null);
		j.setVisible(true);
				
	}

}

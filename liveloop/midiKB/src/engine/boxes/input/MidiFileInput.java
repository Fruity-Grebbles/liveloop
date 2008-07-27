package engine.boxes.input;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import engine.api.MidiIn;
import engine.boxes.effect.MidiTee;
import engine.utils.MidiConnect;

public class MidiFileInput extends MidiIn {
	
	
	protected MidiTee tee;
	public MidiFileInput(File midiF) throws MidiUnavailableException, InvalidMidiDataException, IOException{
		tee=new MidiTee();
		final Sequencer s=MidiSystem.getSequencer(false);
		s.open();
		s.getTransmitter().setReceiver(tee);
		Sequence midi=MidiSystem.getSequence(midiF);
		s.setSequence(midi);
		s.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		s.start();
	}
	
	public void addReceiver(Receiver r) {
		tee.addReceiver(r);
	}
	
	
	
	

}

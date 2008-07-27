package examples;

import javax.sound.midi.MidiUnavailableException;

import engine.boxes.input.StupidMidiClock;
import engine.boxes.output.ClassicOut;
import engine.utils.MidiConnect;


/**
 * Simple Exemple a midi clock synchonisation
 * Its useful to synchronise LiveLoop to other software
 * @author Marc Haussaire
 *
 */
public class TestClock {
	
	public static void main(String[] args) throws MidiUnavailableException {
		MidiConnect.listDevices();
		StupidMidiClock cl=new StupidMidiClock();
		ClassicOut out=new ClassicOut(15);
		cl.addReceiver(out);
	}

}

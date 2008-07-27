package examples;

import javax.sound.midi.MidiUnavailableException;


import engine.boxes.effect.ChangeBank;
import engine.boxes.effect.ChangeChannel;
import engine.boxes.effect.MidiTee;
import engine.boxes.input.ClassicInput;
import engine.boxes.output.ClassicOut;
import engine.boxes.output.DumpReceiver;
import engine.loopSequencer.LoopController;
import engine.loopSequencer.PlayStatus;
import engine.utils.MidiConnect;

/**
 * Simple Exemple using some MidiEffects
 * @author Marc Haussaire
 *
 */
public class TestBank {
	
	public static void main(String[] args) throws MidiUnavailableException {

		MidiConnect.listDevices();
		int[] controls=new int[]{74,71,91,93,73,72,5,84};
		ClassicInput in=new ClassicInput(9);
		ChangeBank bank=new ChangeBank(75);
		ChangeChannel ch=new ChangeChannel(controls);
		
				
		ClassicOut out = new ClassicOut(12);
		DumpReceiver r=new DumpReceiver();
		
		MidiTee t=new MidiTee();
		
		
		in.addReceiver(ch);
		ch.addReceiver(bank);
		
		ch.addReceiver(t);
		bank.addReceiver(t);
		
		t.addReceiver(out);
		t.addReceiver(r);
		
	}

}

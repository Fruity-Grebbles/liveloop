package examples;
import javax.sound.midi.MidiUnavailableException;

import engine.boxes.input.ClassicInput;
import engine.boxes.input.RemoteInput;
import engine.boxes.output.ClassicOut;
import engine.boxes.output.DumpReceiver;
import engine.utils.MidiConnect;

/**
 * Exemple using a UDP remote connection beetween 2 PCS
 * @author Marc Haussaire
 *
 */
public class LocalMidi {
	public static void main(String[] args) throws MidiUnavailableException {
		MidiConnect.listDevices();
		//ClassicInput in =new ClassicInput(9);
		System.out.println("Server...");
		RemoteInput in2=new RemoteInput(8888);
		
		ClassicOut out=new ClassicOut(14);
		DumpReceiver r=new DumpReceiver();
		in2.addReceiver(out);
		in2.addReceiver(r);
	}

}

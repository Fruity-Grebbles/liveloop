package examples;
import engine.boxes.input.ClassicInput;
import engine.boxes.input.DumpKB;
import engine.boxes.input.RemoteInput;
import engine.boxes.output.ClassicOut;
import engine.boxes.output.DumpReceiver;
import engine.utils.MidiConnect;


/**
 * The simplest example connecting a keyboard to a loudspeaker
 * @author Marc Haussaire
 *
 */
public class SimpleTest {
	public static void main(String[] args) throws Exception {
		MidiConnect.listDevices();
		ClassicInput in=new ClassicInput("USB Axiom 61 In1");
		ClassicOut out=new ClassicOut("MIDI Yoke NT:  1");
		in.addReceiver(out);
		DumpReceiver re=new DumpReceiver();
		in.addReceiver(re);
		
	}
	//example using the PC keyboard
	public static void main2(String[] args) throws Exception {
		MidiConnect.listDevices();
		DumpKB in=new DumpKB(100,0);
		ClassicOut out=new ClassicOut(13);
		in.addReceiver(out);
		
	}
	
}

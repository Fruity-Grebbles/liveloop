package examples;
import engine.boxes.input.ClassicInput;
import engine.boxes.input.RemoteInput;
import engine.boxes.output.ClassicOut;
import engine.boxes.output.DumpReceiver;
import engine.boxes.output.RemoteOut;
import engine.utils.MidiConnect;

/**
 * Exemple using a UDP remote connection beetween 2 PCS
 * @author Marc Haussaire
 *
 */
public class RemoteMidi {
	
	public static void main2(String[] args) throws Exception {
		MidiConnect.listDevices();
		System.out.println("Client...");
		ClassicInput in =new ClassicInput(10);
		ClassicOut out=new ClassicOut(13);
		RemoteOut outFab=new RemoteOut("86.71.12.218",3501);
		//in.addReceiver(new DumpReceiver());
		in.addReceiver(outFab);
		in.addReceiver(out);
		
	}
	
	public static void main(String[] args) throws Exception {
		MidiConnect.listDevices();
		ClassicInput in =new ClassicInput(10);
		ClassicOut out=new ClassicOut(13);
		RemoteInput inFab=new RemoteInput(3500);
		DumpReceiver rec=new DumpReceiver();
		in.addReceiver(out);
		inFab.addReceiver(out);
		inFab.addReceiver(rec);
	}

}

package examples;

import java.io.File;

import utils.transport.ShortMsgSerializer;
import utils.transport.UDPComm;
import engine.boxes.input.ClassicInput;
import engine.boxes.input.MidiFileInput;
import engine.boxes.input.RemoteInput2;
import engine.boxes.output.ClassicOut;
import engine.boxes.output.DumpReceiver;
import engine.boxes.output.RemoteOut2;
import engine.utils.MidiConnect;
/**
 * Exemple using a UDP remote connection beetween 2 PCS
 * @author Marc Haussaire
 *
 */
public class NetClient {

	public static void main(String[] args) throws Exception {
		MidiConnect.listDevices();
		UDPComm comm=new UDPComm("86.71.12.218",3501,new ShortMsgSerializer());
		RemoteOut2 outFab=new RemoteOut2(comm);
		RemoteInput2 inFab=new RemoteInput2(comm);
		ClassicInput in=new ClassicInput(10);
		ClassicOut out=new ClassicOut(15);
		//MidiFileInput in=new MidiFileInput(new File("D:/test.midi"));
		DumpReceiver rec=new DumpReceiver();
		in.addReceiver(outFab);
		in.addReceiver(out);
		inFab.addReceiver(out);
		
	}
}

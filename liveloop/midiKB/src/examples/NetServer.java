package examples;

import java.io.IOException;

import utils.transport.ShortMsgSerializer;
import utils.transport.UDPComm;
import engine.boxes.effect.ChangeChannel;
import engine.boxes.input.ClassicInput;
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
public class NetServer {
	
	public static void main(String[] args) throws Exception {
		UDPComm comm=new UDPComm(3500,new ShortMsgSerializer());
		RemoteOut2 outFab=new RemoteOut2(comm);
		RemoteInput2 inFab=new RemoteInput2(comm);
		ClassicInput in=new ClassicInput(10);
		ClassicOut out=new ClassicOut(15);
		DumpReceiver rec=new DumpReceiver();
		ChangeChannel ch=new ChangeChannel(new int[]{71,91});
		in.addReceiver(ch);
		ch.addReceiver(outFab);
		ch.addReceiver(rec);
		inFab.addReceiver(rec);
		in.addReceiver(out);
		inFab.addReceiver(out);
	}

}

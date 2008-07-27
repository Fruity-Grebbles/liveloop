package engine.boxes.input;

import java.net.SocketException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import utils.transport.ObjectReceiver;
import utils.transport.ShortMsgSerializer;
import utils.transport.UDPServer;

import engine.api.MidiEffect;

@Deprecated
/*Remote out is deprecated because the in must be the UDP server, use the RemoteOut2 instead
 *  
 */
public class RemoteInput extends MidiEffect implements ObjectReceiver{

	
	private UDPServer server;
	public RemoteInput(int port){
		try {
			server=new UDPServer(port,new ShortMsgSerializer(),this);
			server.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void send(MidiMessage mes, long timeS) {
		sendToAll(mes,timeS);
	}


	public void objectReceived(Object obj) {
		ShortMessage mes=(ShortMessage)obj;
		send(mes, System.currentTimeMillis());
	}
	public void close() {		
		super.close();
		server.stop();
	}

	public void send(Object obj) {
		//never used;
		
	}
	

}

package engine.boxes.output;

import java.io.IOException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import utils.transport.SerializeException;
import utils.transport.ShortMsgSerializer;
import utils.transport.UDPClient;

import engine.api.MidiOut;

@Deprecated
/*Remote out is deprecated because the out must be the UDP client, use the RemoteOut2 instead
 *  
 */
public class RemoteOut implements MidiOut {

	
	private UDPClient client;
	public RemoteOut(String host,int port){
		client=new UDPClient(host,port,new ShortMsgSerializer());
	}
	

	public void close() {
		// TODO Auto-generated method stub

	}

	public void send(MidiMessage mes, long timeS) {
		//if(!mes instanceof ShortMessage)
		try {
			client.sendObject(mes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerializeException e) {
			e.printStackTrace();
		}
	}

}

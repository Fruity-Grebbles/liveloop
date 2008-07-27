package engine.boxes.output;

import java.io.IOException;
import java.net.SocketException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import utils.transport.SerializeException;
import utils.transport.ShortMsgSerializer;
import utils.transport.UDPClient;
import utils.transport.UDPComm;

import engine.api.MidiOut;

public class RemoteOut2 implements MidiOut {

	
	private UDPComm connection;
	public RemoteOut2(UDPComm connection){
		this.connection=connection;		
	}
	

	public void close() {
		// TODO Auto-generated method stub

	}

	public void send(MidiMessage mes, long timeS) {
		//if(!mes instanceof ShortMessage)
		try {
			connection.sendObject(mes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SerializeException e) {
			e.printStackTrace();
		}
	}

}

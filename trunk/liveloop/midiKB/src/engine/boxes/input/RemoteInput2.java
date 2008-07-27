package engine.boxes.input;

import java.net.SocketException;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import utils.transport.ObjectReceiver;
import utils.transport.ShortMsgSerializer;
import utils.transport.UDPComm;
import utils.transport.UDPServer;

import engine.api.MidiEffect;

public class RemoteInput2 extends MidiEffect implements ObjectReceiver{

	
	private UDPComm connection;
	public RemoteInput2(UDPComm connection){//-1 to be a client
		this.connection=connection;
		connection.setReceiver(this);
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
		connection.stop();
	}
	

}

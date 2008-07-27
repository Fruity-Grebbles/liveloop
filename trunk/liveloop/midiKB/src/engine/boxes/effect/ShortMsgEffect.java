package engine.boxes.effect;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import engine.api.MidiEffect;

public abstract class ShortMsgEffect extends MidiEffect{
	public void send(MidiMessage msg, long timeS) {
		if(!(msg instanceof ShortMessage)){
			sendToAll(msg, timeS);
			return;
		}
		send((ShortMessage)msg,timeS);
	}
	
	public abstract void send(ShortMessage msg, long timeS);
}

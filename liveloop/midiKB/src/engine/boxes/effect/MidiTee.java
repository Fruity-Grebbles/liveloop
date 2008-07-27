package engine.boxes.effect;

import javax.sound.midi.MidiMessage;

import engine.api.MidiEffect;

public class MidiTee extends MidiEffect{

	public void send(MidiMessage message, long timeStamp) {
		sendToAll(message, timeStamp);
	}

}

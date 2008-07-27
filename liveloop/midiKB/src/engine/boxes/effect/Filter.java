package engine.boxes.effect;

import javax.sound.midi.MidiMessage;

import engine.api.MidiEffect;
import engine.boxes.effect.dispatch.Condition;
import engine.boxes.effect.dispatch.Conditions;

public class Filter extends MidiEffect {
	
		
	protected Condition condition;
	public Filter(Condition condition){
		this.condition=condition;
	}
	public void send(MidiMessage message, long timeStamp) {
		if(condition.canPass(message))
			sendToAll(message, timeStamp);
	}
	
	

}

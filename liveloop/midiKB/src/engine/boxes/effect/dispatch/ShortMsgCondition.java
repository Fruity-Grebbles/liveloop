package engine.boxes.effect.dispatch;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class ShortMsgCondition implements Condition {

	public boolean canPass(MidiMessage message) {
		if(message instanceof ShortMessage)
			return canPass((ShortMessage)message);
		else
			return false;
	}
	public boolean canPass(ShortMessage message){
		return true;
	}


}

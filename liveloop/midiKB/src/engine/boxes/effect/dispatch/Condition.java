package engine.boxes.effect.dispatch;

import javax.sound.midi.MidiMessage;

public interface Condition {
	
	public boolean canPass(MidiMessage message);

}

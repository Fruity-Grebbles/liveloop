package engine.boxes.effect;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import engine.api.MidiEffect;
import engine.boxes.effect.dispatch.Conditions;
import engine.boxes.effect.dispatch.ConditionEffect;
import engine.boxes.effect.dispatch.MessageDispatcher;
import engine.boxes.effect.dispatch.MethodCaller;

public abstract class ControllableEffect extends ConditionEffect {

	
	
	public ControllableEffect(){
		this.addCondition(Conditions.CTRL,"sendCtrl");
		this.addCondition(Conditions.NOTE,"sendNote");		
	}
	
	
	public abstract void sendCtrl(ShortMessage mes,long timeS);
	public abstract void sendNote(ShortMessage mes,long timeS);

}

package engine.boxes.effect.loopingSequencer;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import engine.boxes.effect.dispatch.Conditions;
import engine.boxes.effect.dispatch.MessageDispatcher;
import engine.boxes.effect.dispatch.MethodCaller;
import engine.boxes.effect.dispatch.ShortMsgCondition;

public class ControlledSequencer extends LoopingSequencer{

	public ControlledSequencer(long bpm, int resolution) {
		super(bpm, resolution);
		MethodCaller.dispatch(this, Conditions.CTRL, "sendCtrl");
		MethodCaller.dispatch(this, Conditions.NOTE, "sendNote");
	}
	
	public void sendCtrl(ShortMessage mes, long timeStamp){
		
	}
	public void sendNote(ShortMessage mes, long timeStamp){
		
	}
	

}

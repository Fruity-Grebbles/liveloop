package engine.boxes.effect.dispatch;

import javax.sound.midi.ShortMessage;

public abstract class SimpleConditionEffect extends ConditionEffect{
	
	public SimpleConditionEffect(Condition condition){
		super();
		addCondition(condition, "onCondition");
	}
	

	public abstract void onCondition(ShortMessage mes,long timeS);

}

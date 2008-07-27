package engine.boxes.effect;

import javax.sound.midi.ShortMessage;

import engine.boxes.effect.dispatch.Conditions;

public abstract class OneControlEffect extends ControllableEffect {

	
	protected int ctrl;
	public OneControlEffect(int ctrl){
		this.ctrl=ctrl;
		this.addCondition(Conditions.CTRL,"sendCtrl");
	}
	
	public abstract void onControl(int value);
	public void sendCtrl(ShortMessage mes, long timeS) {
		if(mes.getData1()==this.ctrl){
			onControl(mes.getData2());
		}
	}

	public void sendNote(ShortMessage mes, long timeS) {
		// TODO Auto-generated method stub

	}

}

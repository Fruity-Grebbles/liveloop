package engine.boxes.effect;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import engine.api.MidiEffect;
import engine.utils.Note;

public class ChangeVolume extends OneControlEffect{

	protected float percent;
	public ChangeVolume(int ctrl){
		super(ctrl);
		this.percent=1.0f;
	}
	
	public void onControl(int value) {
		this.percent=(float)value/(float)127;
	}

	
	public void sendNote(ShortMessage mes, long timeS) {
		Note n=new Note(mes);	
			
		n.force=(int)(this.percent*n.force);
		
		sendToAll(n.toMsg(), timeS);
			
	}
	
	
	

}

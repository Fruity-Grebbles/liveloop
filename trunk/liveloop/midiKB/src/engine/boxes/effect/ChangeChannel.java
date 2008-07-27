package engine.boxes.effect;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import engine.utils.MidiUtils;

public class ChangeChannel extends ControllableEffect{

	protected List controls;
	protected int currentChannel=-1;
	public ChangeChannel(int[] controls){
		this.controls=new ArrayList();
		for(int i=0;i<controls.length;i++)
			this.controls.add(controls[i]);
	}
		
	public void sendCtrl(ShortMessage mes, long timeS) {
		int value=mes.getData1();
		int channel=this.controls.indexOf(new Integer(value));
		if(channel>=0)
			this.currentChannel=channel;
		if(currentChannel>=0)
			MidiUtils.setChannel(mes, currentChannel);
		sendToAll(mes, timeS);		
	}
	public void sendNote(ShortMessage mes, long timeS) {
		if(currentChannel>=0)
			MidiUtils.setChannel(mes, currentChannel);			
		sendToAll(mes, timeS);
		
			
		
		
	}

	
	
}

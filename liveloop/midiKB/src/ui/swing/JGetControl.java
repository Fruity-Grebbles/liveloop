package ui.swing;

import javax.sound.midi.ShortMessage;
import javax.swing.JTextField;


import engine.api.MidiIn;
import engine.boxes.effect.ControllableEffect;

public class JGetControl extends ControllableEffect{


	JTextField txt;
	public JGetControl(JTextField txt){
		this.txt=txt;		
	}
	public void sendCtrl(ShortMessage mes, long timeS) {
		final ShortMessage me=mes;
		if(txt.hasFocus()){
			txt.setText(""+me.getData1());
			txt.firePropertyChange("txt",-1, me.getData1());
		}
			
				
	}

	public void sendNote(ShortMessage mes, long timeS) {
		// never used

	}




}

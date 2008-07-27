package engine.utils;

import javax.sound.midi.ShortMessage;

public class KeyBoardState {
	
	protected boolean[] keyStates=new boolean[128];
	public void addNote(ShortMessage mes){
		if(mes.getCommand()!=ShortMessage.NOTE_ON)
			return;
		int note=mes.getData1();
		int str=mes.getData2();
		if(str==0){
			keyStates[note]=false;//key up
		}else{
			keyStates[note]=true;//key down
		}		
	}
	public boolean getState(int note){
		if(note<0 || note>=keyStates.length)
			throw new IllegalArgumentException();
		return keyStates[note];
	}
	
	public boolean getState(ShortMessage mes){
		if(mes.getCommand()!=ShortMessage.NOTE_ON)
			return true;
		return getState(mes.getData1());	
	}

}

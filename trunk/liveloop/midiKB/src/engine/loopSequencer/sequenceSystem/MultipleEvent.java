package engine.loopSequencer.sequenceSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;

public class MultipleEvent extends MidiEvent{

	
	protected List<MidiMessage> messages;
	public MultipleEvent(MidiMessage message, long tick) {
		super(message, tick);
	}
	public MultipleEvent(MidiEvent m){
		super(m.getMessage(),m.getTick());
	}
	
	public void addMessage(MidiMessage m){
		if(messages==null){
			messages=Collections.synchronizedList(new ArrayList<MidiMessage>());
			messages.add(this.getMessage());
		}			
		messages.add(m);	
	}
	public List<MidiMessage> getMessages(){
		return messages;
	}
	public int getLength(){
		if(messages==null)
			return 1;
		return messages.size();
		
	}
	

}

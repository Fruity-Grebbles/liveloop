package engine.boxes.effect.loopingSequencer;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.ShortMessage;

import engine.utils.KeyBoardState;

public class Track {
	
	
	class TrackElement{
		public long tick;
		public ShortMessage message;
	}
	
	protected Map<Long, ShortMessage> messages;
	protected KeyBoardState state;
	public Track(LoopingSequencer sequencer){
		messages=new HashMap<Long, ShortMessage>();
	}
	public ShortMessage getMessage(long tick){
		return messages.get(tick);
	}
	
	public void addMessage(long tick,ShortMessage message){
		if(!state.getState(message)){
			System.out.println("Not recording the note="+message.getData1()+" with strength="+message.getData2());
			return;		
		}
			
		messages.put(tick, message);
	}
	
	public void clear(){
		messages.clear();
	}

}

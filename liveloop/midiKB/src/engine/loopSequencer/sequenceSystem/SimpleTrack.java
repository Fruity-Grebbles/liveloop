package engine.loopSequencer.sequenceSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Track;

public class SimpleTrack{

	
	protected Map<Long,MultipleEvent> events=new HashMap<Long, MultipleEvent>();
	
	protected long ticklength=0;
	
	protected SimpleSequence sequence;//can also have a listener system (to slow for now)
	
	//protected int loopCount=1;//the number of time the loop is played
	
	SimpleTrack(SimpleSequence sequence){
		this.sequence=sequence;
	}
	
	public boolean add(MidiEvent event) {
		if(event==null)
			throw new IllegalArgumentException();
		synchronized (events) {
			MultipleEvent ev=events.get(event.getTick());
			this.ticklength=Math.max(this.ticklength, event.getTick());
			sequence.updateTickLen(this.ticklength);
			if(ev==null){
				events.put(event.getTick(), new MultipleEvent(event));				
			}else{
				ev.addMessage(event.getMessage());
			}
		}
		return true;
	}
	
	/*public void setLoopCount(int loopCount){
		this.loopCount=loopCount;
	}*/

	public MultipleEvent get(long tick){
		//long t=tick-this.ticklength*(loopCount-1);
		
		return events.get(tick);		
	}

	public long ticks() {
		return ticklength;
	}

	public void clear(){
		events.clear();
	}
	
}

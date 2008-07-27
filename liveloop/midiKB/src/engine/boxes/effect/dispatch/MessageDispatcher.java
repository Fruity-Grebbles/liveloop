package engine.boxes.effect.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import engine.api.MidiEffect;

public class MessageDispatcher extends MidiEffect{
	
	protected Map<Condition,List<Receiver>> receivers;
	public MessageDispatcher(){
		receivers=new HashMap<Condition,List<Receiver>>();
	}
	
	public void addReceiver(Condition cond,Receiver r){
		getReceivers(cond).add(r);
	}
	public List<Receiver> getReceivers(Condition cond){
		List<Receiver> l=receivers.get(cond);
		if(l==null){
			l=new ArrayList<Receiver>();
			receivers.put(cond, l);
		}
		return l;
	}
	
	
	public void addReceiver(Receiver r) {
		getReceivers(Expressions.TRUE).add(r);
	}

	public void send(MidiMessage mes, long timeS) {
		for(Condition c: receivers.keySet()){
			if(c.canPass(mes)){
				for(Receiver r : getReceivers(c)){
					r.send(mes, timeS);
				}				
			}			
		}
	}
	

}

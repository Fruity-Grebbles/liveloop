package engine.boxes.effect.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import engine.api.MidiEffect;

public class ConditionEffect extends MidiEffect{
	
	/*
	public ConditionEffect(){
	}
	
	public void addCondition(Condition condition,String method){
		this.addReceiver(condition,new MethodCaller(this,method));	
	}

	public void addReceiver(Receiver r) {
		for(List<Receiver> l: this.receivers.values()){
			for(Receiver r: l){
				r.
			}
		}
	}*/
	
	protected Map<Condition,MethodCaller> receivers;
	
	public ConditionEffect(){
		receivers=new HashMap<Condition,MethodCaller>();
	}
	public void addCondition(Condition cond,String method){
		MethodCaller call=new MethodCaller(this,method);
		receivers.put(cond,call);
	}
	
	public void send(MidiMessage mes, long timeS) {
		for(Condition c: receivers.keySet()){
			if(c.canPass(mes)){
				MethodCaller call=receivers.get(c);
				call.send(mes, timeS);
			}
		}
	}
	
	

}

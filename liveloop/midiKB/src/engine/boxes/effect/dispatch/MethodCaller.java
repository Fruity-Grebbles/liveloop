package engine.boxes.effect.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import engine.api.MidiEffect;

public class MethodCaller extends MidiEffect{
	
	protected Method meth;
	protected Receiver receiver;
	public MethodCaller(Receiver receiver,String method){
		this.receiver=receiver;
		for(Method m : receiver.getClass().getMethods()){
			if(m.getName().equals(method)){
				this.meth=m;
				return;
			}			
		}
		throw new NoSuchMethodError(method);
	}
	
	public void send(MidiMessage message, long timeStamp) {
		try {
			meth.invoke(receiver, message,timeStamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dispatch(MidiEffect effect,Condition condition, String method){
		MessageDispatcher disp=new MessageDispatcher();
		effect.addReceiver(disp);
		//disp.addReceiver(CommandCondition.CTRL, new MethodCaller(effect,method));
		disp.addReceiver(condition, new MethodCaller(effect,method));
		

	}

}

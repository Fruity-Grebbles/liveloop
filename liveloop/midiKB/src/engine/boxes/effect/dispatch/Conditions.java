package engine.boxes.effect.dispatch;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class Conditions {
	
	
	
	public static class ShortMsgCondition implements Condition {
		public boolean canPass(MidiMessage message) {
			if(message instanceof ShortMessage)
				return canPass((ShortMessage)message);
			else
				return false;
		}
		public boolean canPass(ShortMessage message){
			return true;
		}
	}
	public static class CommandCondition extends ShortMsgCondition{
		protected int command;
		public CommandCondition(int command){
			this.command=command;
		}
		public boolean canPass(ShortMessage message) {
			return message.getCommand()==command;
		}		
	}
	public static class ControlCondition extends CommandCondition{
		protected int ctrl;
		public ControlCondition(int ctrl){
			super(ShortMessage.CONTROL_CHANGE);
			this.ctrl=ctrl;
		}
		public boolean canPass(ShortMessage message) {
			return super.canPass(message) && message.getData1()==ctrl;
		}		
	}
	
	public static Condition NOTE= new CommandCondition(ShortMessage.NOTE_ON);
	public static final Condition CTRL=new CommandCondition(ShortMessage.CONTROL_CHANGE);
	
	

}

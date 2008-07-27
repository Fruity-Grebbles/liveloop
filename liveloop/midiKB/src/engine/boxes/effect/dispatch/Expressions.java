package engine.boxes.effect.dispatch;

import javax.sound.midi.MidiMessage;

public class Expressions{

	public static final Condition TRUE=new Condition(){
		public boolean canPass(MidiMessage message) {
			return true;
		}		
	};
	public static final Condition FALSE=new Condition(){
		public boolean canPass(MidiMessage message) {
			return true;
		}		
	};
	public static final Condition OR(final Condition c1,final Condition c2){
		return new Condition(){
			public boolean canPass(MidiMessage message) {
				return c1.canPass(message) || c2.canPass(message);
			}			
		};
	}
	public static final Condition AND(final Condition c1,final Condition c2){
		return new Condition(){
			public boolean canPass(MidiMessage message) {
				return c1.canPass(message) && c2.canPass(message);
			}			
		};
	}
	public static final Condition NOT(final Condition c){
		return new Condition(){
			public boolean canPass(MidiMessage message) {
				return !c.canPass(message);
			}			
		};
	}
	

}

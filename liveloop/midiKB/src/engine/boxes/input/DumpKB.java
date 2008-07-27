package engine.boxes.input;

import javax.sound.midi.MidiMessage;

import com.marco.utils.keyLogger.KeyListener;
import com.marco.utils.keyLogger.KeyLogger;

import engine.api.MidiIn;
import engine.utils.Note;

public class DumpKB extends MidiIn{
	
	int force;
	int channel;
	public DumpKB(int force,int channel){
		this.force=force;
		this.channel=channel;
		KeyLogger.addKeyListener(new KeyListener(){

			public void keyPressed(int k) {
				MidiMessage m=Note.getMsgON(DumpKB.this.channel, k%128,DumpKB.this.force);
				DumpKB.this.sendToAll(m, 0);
			}

			public void keyReleased(int k) {
				MidiMessage m=Note.getMsgON(DumpKB.this.channel, k%128,0);
				DumpKB.this.sendToAll(m, 0);				
			}
			
		});
	}

}

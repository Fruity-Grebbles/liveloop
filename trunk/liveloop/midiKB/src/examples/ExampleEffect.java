package examples;

import javax.sound.midi.MidiMessage;

import engine.api.MidiEffect;
import engine.utils.Note;

/**
 * ExampleEffect just translate each note to the next octave, 
 * 
 * 
 * @author Marc Haussaire
 *
 */
public class ExampleEffect extends MidiEffect{

	public void send(MidiMessage message, long timeStamp) {
		Note n=new Note(message);
		if(!n.valid){//if message is not a Note, just send it to other modules
			sendToAll(message, timeStamp);
			return ;
		}
		//else
		n.note=(n.note+12)%128;//set the note to the next octave
		sendToAll(n.toMsg(), timeStamp);
	}
	
	

}

package engine.boxes.input;

import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import engine.api.MidiEffect;
import engine.api.MidiIn;
import engine.utils.MidiUtils;

public class StupidMidiClock extends MidiIn {

	protected TimerTask task;
	public StupidMidiClock(){
		
		task=new TimerTask(){
			int counter=0;
			public void run() {
				if(counter>1000){
					counter=0;
					sendStop();
					sendStart();
					System.out.println("___________________________________");
				}else{
					counter++;
					sendClock();
				}
			}			
		};
		Timer t=new Timer();
		t.purge();
		sendStart();
		//t.schedule(task, 1000,10);
		t.schedule(task, 100, 40);
		
		
	}
	protected void sendClock() {
		ShortMessage mes=new ShortMessage();
		MidiUtils.setStatus(mes,ShortMessage.TIMING_CLOCK);
		sendToAll(mes, 0);
		System.out.println("0");
	}
	protected void sendStart() {
		ShortMessage mes=new ShortMessage();
		MidiUtils.setStatus(mes,ShortMessage.START);
		sendToAll(mes, 0);		
	}
	protected void sendStop() {
		ShortMessage mes=new ShortMessage();
		MidiUtils.setStatus(mes,ShortMessage.STOP);
		sendToAll(mes, 0);		
	}

}

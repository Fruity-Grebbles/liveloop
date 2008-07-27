package engine.boxes.effect;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;

import engine.api.MidiEffect;
import engine.api.MidiIn;
import engine.api.MidiOut;

public class DefaultSequencer extends MidiIn{
	
	protected Sequencer s;
	protected MidiTee tee;
	protected Receiver rSeq;
	public DefaultSequencer(Sequencer s){
		this.s=s;
		this.tee=new MidiTee(){

			public void send(MidiMessage message, long timeStamp) {
				//System.out.println("out="+message);
				super.send(message, timeStamp);
			}		
		};
		try {
			s.getTransmitter().setReceiver(tee);
			rSeq=s.getReceiver();
		} catch (MidiUnavailableException e) {
			throw new Error(e);
		}
	}
	
	public void addReceiver(Receiver r) {
		tee.addReceiver(r);
	}

	

}

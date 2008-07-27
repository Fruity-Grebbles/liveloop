/*
 * Créé le 24 juin 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package engine.boxes.input;

import java.util.ArrayList;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

import engine.api.MidiEffect;
import engine.utils.MidiConnect;

/**
 * classe MidiPipe.java
 * @author Marc Haussaire
 */
public class ClassicInput extends MidiEffect{

	ArrayList receivers;
	MidiDevice dev;
	public ClassicInput(int inputNumber) throws MidiUnavailableException{
		super();
		dev=MidiConnect.connectToInputNumber(this,inputNumber);
	}
	public ClassicInput(String deviceName) throws MidiUnavailableException{
		super();
		dev=MidiConnect.connectToInputName(this,deviceName);
	}
	
	public void send(MidiMessage mes, long timeS){				
		sendToAll(mes,timeS);
	}

	public void close() {
		dev.close();
	}
	
	
	
	

	
	
}

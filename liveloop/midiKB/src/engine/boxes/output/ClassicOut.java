/*
 * Créé le 6 sept. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package engine.boxes.output;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

import engine.api.MidiOut;
import engine.utils.MidiConnect;


/**
 * classe ClassicOut.java
 * @author Marc Haussaire
 */
public class ClassicOut implements MidiOut{
	Receiver out;
	public ClassicOut(int number) throws MidiUnavailableException{
		out=MidiConnect.getReceiverFromNumber(number);		
	}
	public ClassicOut(String name) throws MidiUnavailableException{		
		out=MidiConnect.getReceiverFromString(name);		
	}
	public void send(MidiMessage mes,long timeS){
		out.send(mes,timeS);
	}
	
	public void close(){
		out.close();
	}
}

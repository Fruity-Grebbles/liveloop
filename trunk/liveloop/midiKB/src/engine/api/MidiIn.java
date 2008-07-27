/*
 * Créé le 3 sept. 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package engine.api;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;


/**
 * classe MidiIn.java
 * MidiIn is an input module, no module can be connected to it
 * For exemple a device like a midi Keyboard is a MidiIn
 * @author Marc Haussaire
 */
public abstract class MidiIn implements MidiBox{
	protected int mode;
	public static final int CHANGE_MODE=0;
	public static final int MODE_NORMAL=0;
	public static final int MODE_IDENT=1;
	public static final int MODE_NUL=2;
	protected List<Receiver> receivers;
	public MidiIn(){
		receivers=new ArrayList();
	}
	/**
	 * Connect the current module to another module
	 * @param r
	 */
	public void addReceiver(Receiver r){
		receivers.add(r);
	}
	/**
	 * Disconnect the current module to specified module
	 * @param r
	 */
	public void removeReceiver(Receiver r){
		receivers.remove(r);
	}
	
	/**
	 * Send a midiMessage to each other connected module
	 * @param mes : the message to send
	 * @param timeS : the timestamp
	 */
	public void sendToAll(MidiMessage mes,long timeS){
		for(int i=0;i<receivers.size();i++){
			((Receiver)(receivers.get(i))).send(mes,timeS);
		}
	}
	
	/**
	 * override this method to free resources used by your module	 * 
	 */
	public void close(){
		receivers.clear();
	}
	
	
	/*public void connectTheSameSource(MidiIn source){
		for(int i=0;i<source.receivers.size();i++){
			addReceiver((Receiver)source.receivers.get(i));
		}
	}*/
	
	/**
	 * return the connected modules
	 */
	public List<Receiver> getReceivers(){
		return receivers;
	}
	
	
}

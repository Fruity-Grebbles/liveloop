/*
 * Cr�� le 3 sept. 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package engine.api;

import javax.sound.midi.Receiver;

/**
 * classe MidiOut.java
 * @author Marc Haussaire
 * 
 * A midi receiver, for example a device like a PCI sound driver can be a MidiOut
 */
public interface MidiOut extends MidiBox,Receiver{

}

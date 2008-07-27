/*
 * Cr�� le 3 sept. 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package engine.api;



/**
 * classe MidiEffect.java
 * @author Marc Haussaire
 * 
 * A MidiEffect is a module that is Input and Output
 * You can write your own midiEffect by ovverrinding this class (or other subclass)
 * the method send(MidiMessage, long) is called 
 */
public abstract class MidiEffect extends MidiIn implements MidiOut{


}

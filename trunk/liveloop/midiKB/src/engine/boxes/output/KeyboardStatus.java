/*
 * Créé le 4 août 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package engine.boxes.output;

import javax.sound.midi.MidiMessage;

import javax.sound.midi.ShortMessage;

import engine.api.MidiOut;
import engine.utils.Note;



/**
 * classe KeyBoardStatus.java
 * @author Marc Haussaire
 */
public class KeyboardStatus implements MidiOut{
	
	int[] touches;
	int[] keyboard;
	int intervalle=-1;	//si l'intervalle est positif, il correspond alors au 
					//temps en millisecondes apres lequel une touche enfoncee
					//est consideree comme relevee
	public KeyboardStatus(int[] touches,int intervalle){
		this(intervalle);
		this.touches=touches;
	}
	public KeyboardStatus(int intervalle){
		super();
		this.intervalle=intervalle;
		init();
	}
	public KeyboardStatus(){
		super();
		init();
	}
	
	public int getControlVal(){
		if (isAllPressed(touches)) return 1;
		return 0;	
	}
	public void setIntervalle(int intervalle){
		this.intervalle=intervalle;
	}

	private void init(){
		keyboard=new int[128];
		for(int i=0;i<128;i++){
			keyboard[i]=0;
		}
	}
	
	public boolean isPressed(MidiMessage message){
		Note n=new Note(message);
		if(!n.valid)
			return false;
		return keyboard[n.note]>0;
	}
	
	public boolean isPressed(int touche){
		//System.out.println(touche+" "+(touches[touche]>0));
		return keyboard[touche]>0;
	}
	public boolean isAllPressed(int[] touches){
		for(int i=0;i<touches.length;i++){
			if (!isPressed(touches[i])) return false;
		}
		return true;
	}
	//indique si au moins une touche est enfoncee
	public boolean isPressed(){
		for(int i=0;i<256;i++){
			if (isPressed(i) )return true;
		}
		return false;
	}
	public boolean notePressed(int note){
		note=note%12;
		for(int i=0;i<256-12;i+=12){
			if (isPressed(i+note)){
				 return true; }
		}
		return false;
	}
	
	public void send(MidiMessage mes,long timeStamp){
		int note;
		if (!(mes instanceof ShortMessage))
			return;
		ShortMessage mes2 = (ShortMessage) mes;
		if (mes2.getCommand() != ShortMessage.NOTE_ON)
			return;
		note=mes2.getData1();
		
		keyboard[note]=mes2.getData2();//vaut 0 si la touche est relevee
		
		
	}
	public void close(){
		keyboard=null;
	}
	

}

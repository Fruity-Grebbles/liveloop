/*
 * Créé le 22 juin 2004
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package engine.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;



public class MidiConnect {

	ArrayList l;


	/*marche pas*/
	public static void setSoundBank(String file) throws Exception{
		Soundbank sb=MidiSystem.getSoundbank(new File(file));
		MidiSystem.getSynthesizer().loadAllInstruments(sb);
	}
	
	public static MidiDevice.Info getInfoFromString(String name){
		MidiDevice.Info[] infos=MidiSystem.getMidiDeviceInfo();
		for(MidiDevice.Info i : infos){			
			if(i.getName().equals(name)){
				return i;
			}
		}
		throw new IllegalArgumentException();		
	}
	
	public static MidiDevice openDeviceFromString(String name,boolean isInput){
		MidiDevice res=null;
		try{
			MidiDevice.Info[] infos=MidiSystem.getMidiDeviceInfo();
			for(MidiDevice.Info i : infos){			
				if(i.getName().equals(name)){
					MidiDevice device=MidiSystem.getMidiDevice(i);
					boolean in= (device.getMaxTransmitters() != 0);
					if(in==isInput){
						res=device;
						break;
					}
				}
			}
			if(res==null)
				throw new IllegalArgumentException();	
			
		}catch(Exception e){
			System.out.println(e);
		}
		try{
			res.open();
		}catch(Exception e){
				System.out.println(e);
		}
		return res;
	}
	
	public static MidiDevice openDeviceFromNumber(int number){
		MidiDevice res=null;
		MidiDevice.Info[] infos=MidiSystem.getMidiDeviceInfo();
		try{
			res = MidiSystem.getMidiDevice(infos[number]);
		}catch(Exception e){
			System.out.println(e);
		}
		try{
		res.open();
		}catch(Exception e){
				System.out.println(e);
		}
		return res;
	}
	public static Receiver getReceiverFromNumber(int number) throws MidiUnavailableException {
		return openDeviceFromNumber(number).getReceiver();
	}
	public static Receiver getReceiverFromString(String name) throws MidiUnavailableException {
		return openDeviceFromString(name,false).getReceiver();
	}

	public static MidiDevice connectToInputNumber(Receiver r,int number) throws MidiUnavailableException{
		MidiDevice d=openDeviceFromNumber(number);
		d.getTransmitter().setReceiver(r);
		return d;
	}
	public static MidiDevice connectToInputName(Receiver r,String name) throws MidiUnavailableException{
		MidiDevice d=openDeviceFromString(name,true);
		d.getTransmitter().setReceiver(r);
		return d;
	}
	public static List<String> getInputs(){
		List<String> res=new ArrayList<String>();
		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++){
			boolean in=true;			
			try{
				MidiDevice	device = MidiSystem.getMidiDevice(aInfos[i]);
				in= (device.getMaxTransmitters() != 0);
			}catch(Exception e){
				in=false;
			}
			if(in){
				res.add(aInfos[i].getName());
			}
		}
		return res;
	}
	public static List<String> getOutputs(){
		List<String> res=new ArrayList<String>();
		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++){
			boolean out=true;			
			try{
				MidiDevice	device = MidiSystem.getMidiDevice(aInfos[i]);
				out = (device.getMaxReceivers() != 0);
			}catch(Exception e){
				out=false;
			}
			if(out){
				res.add(aInfos[i].getName());
			}
		}
		return res;
	}
	
	public static String listDevices(){
		String res="Liste du materiel :\nnum\tin/out?\tnom\n";
		boolean in=false,out=false;
		MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++){
			try{
				MidiDevice	device = MidiSystem.getMidiDevice(aInfos[i]);
				in= (device.getMaxTransmitters() != 0);
				out = (device.getMaxReceivers() != 0);
			}catch(Exception e){
				in=false;
				out=false;
				System.out.println(e);
			}
			res=res+i+"\t"+inOut(in,out)+"\t\t"+aInfos[i].getName()+"\n";
		}
		System.out.println(res);
		return res;
	}
	private static String inOut(boolean in,boolean out){
		String res="";
		if (in && out) return "I/O";
		if (!in && !out) return "BAD";
		if (in) return "IN";
		return "OUT";
	}
	
	public static void setChannelInstru(int channel,int instru,Receiver r) throws Exception{
		ShortMessage mes=new ShortMessage();
		mes.setMessage(ShortMessage.PROGRAM_CHANGE,channel,instru,-1);
		r.send(mes,0);
	}

	public static void setChannelInstru(int channel,int section,int number,Receiver r) throws Exception{
		setChannelInstru(channel,section*8+number,r);
	}
	public static String printSections(){
		String rep="";
		rep+="1: pianos";
		rep+="2: xylos";
		rep+="3: orgues";
		rep+="4: guitares";
		rep+="5: xylos";
		return rep;
	
	}
	public static String coolSons(){
		String rep="";
		rep+="0:piano";
		rep+="38:techno";
		rep+="39:techno2";
		rep+="100:orgueFromStars";
		return rep;
	}
	
	/*marche pas*/
	public static String listInstru() throws Exception{
		Instrument[] ins=MidiSystem.getSynthesizer().getAvailableInstruments();
		String rep=""+ins.length;
		for(int i=0;i<ins.length;i++){
			rep=rep+ins[i].getName()+"\n";
		}
		return rep;
	}
	/* rend la note a partie d'un midimessage
	 * positif si la touche est enfoncee
	 * negatif sinon
	 * nul si ce n'est pas un message de note
	 */
	public static int getNote(MidiMessage mes){
		if (!(mes instanceof ShortMessage))
			return 0;
		ShortMessage mes2 = (ShortMessage) mes;
		if (mes2.getCommand() != ShortMessage.NOTE_ON)
			return 0;	
		if (mes2.getData2() ==0) return -mes2.getData1();
		return mes2.getData1();
	}
	
}



//DumpReceiver db=new DumpReceiver();
//MidiPipe mp=new MidiPipe();
//mp.ajoutReceiver(db);
//mp.ajoutReceiver(out);

//MidiPipe mp=new MidiPipe();
//setChannelInstru(0,0,out);
//setChannelInstru(1,0,out);
//setChannelInstru(2,5,out);
//EffetFilter ef1=new EffetFilter(36,48);
//EffetFilter ef2=new EffetFilter(49,83);
//EffetFilter ef3=new EffetFilter(84,85);
//EffetInstru ei1=new EffetInstru(1);
//EffetInstru ei2=new EffetInstru(1);
//ei2.ajoutInstrument(2);
//EffetInstru ei3=new EffetInstru(3);
//EffetMonoNote mn=new EffetMonoNote(15);
//EffetVolume vol=new EffetVolume(1.5f);
//EffetAccord acc=new EffetAccord();
//EffetNoLache noL=new EffetNoLache();
//EffetArpege earp=new EffetArpege();
//earp.ajoutNote(0,0);
//earp.ajoutNote(5,100);
//earp.ajoutNote(-4,200);
//earp.ajoutNote(5,300);
//earp.ajoutNote(-4,400);
//		
////acc.ajoutNote(+5);
////acc.ajoutNote(-4);
//ef1.setReceiver(ei1);
//ef2.setReceiver(ei2);
//ef3.setReceiver(ei3);
//ei3.setReceiver(mn);
//ei1.setReceiver(out);
//ei2.setReceiver(acc);
//acc.setReceiver(earp);
//earp.setReceiver(out);
//mn.setReceiver(vol);
//vol.setReceiver(noL);
//noL.setReceiver(out);	
//mp.ajoutReceiver(ef1);
//mp.ajoutReceiver(ef2);
//mp.ajoutReceiver(ef3);


//broullions de test

//System.out.println(listDevices());
//connect(8,5);
		
//MidiConnect md=new MidiConnect(8,5);
//md.insertEffect(new EffetIdent());
//EffetInstru m=new EffetInstru();
//m.ajoutInstrument(0);
//m.ajoutInstrument(2);
//m.ajoutInstrument(10);
//		
//md.insertEffect(m);
//EffetFilter f=new EffetFilter();
//f.setFilter(50,75);
//md.insertEffect(f);
//md.connect();


//
//public static void main(String[] args) throws Exception{
//	System.out.println(listDevices());
//	
//	/*fabrique un receiver a partir des enceintes*/
//	Receiver out =getReceiverFromNumber(13);
//	/*change le channel 0 avec l'instrument 82*/
//	setChannelInstru(0,82,out);
//	ImproCool ipc=new ImproCool(800);
//	EffetNoteFilter efnf=new EffetNoteFilter();
//	KeyboardStatus kb=new KeyboardStatus();
//	//if (kb==null) System.out.println("jhkhllqsdqsdhmkljhlqkf");
//	//MidiSave ms=new MidiSave(kb,"./out.midi");
//	MidiPipe mp=new MidiPipe();
//	MidiPipe mp2=new MidiPipe();
//	/*connecte le premier effet au clavier*/
//	connectToInputNumber(mp2,16);
//	//connectToInputNumber(mp2,10);
//	mp2.ajoutReceiver(ipc);
//	mp2.ajoutReceiver(kb);
//	ipc.setReceiver(efnf);
//	efnf.setReceiver(mp);
//	mp.ajoutReceiver(out);
//	//mp.ajoutReceiver(ms);
//	
//	
//	
//	//MsgsLoop ll=new MsgsLoop(mp2,10,3,800,1000,60,.2);
//
//

package engine.loopSequencer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;

import com.sun.media.sound.MidiOutDeviceProvider;

import engine.api.MidiOut;
import engine.utils.MidiConnect;
import engine.utils.MidiUtils;

public class LoopController implements MidiOut{

	protected int recordControl;
	protected int noRecordControl;
	protected int doubleLoopControl;
	protected int reduceLoopControl;
	protected int clearControl;
	//protected int bankControl;
	protected PlayStatus status;
	protected List trackControls;
	public LoopController(PlayStatus status,int recordControl, int noRecordControl,int clearControl,int[] trackControls,int doubleLoopControl,int reduceLoopControl) {
		this.recordControl = recordControl;
		this.noRecordControl = noRecordControl;
		this.clearControl=clearControl;
		this.status=status;
		
		
		this.trackControls=new ArrayList();
		for(int i=0;i<trackControls.length;i++)
			this.trackControls.add(trackControls[i]);
		//Collections.addAll(this.trackControls, trackControls);
		this.doubleLoopControl = doubleLoopControl;
		this.reduceLoopControl = reduceLoopControl;
	}
	


	public void close() {
		
	}



	public void send(MidiMessage mes, long timeS) {
		if(mes instanceof ShortMessage)
			processControls((ShortMessage)mes);
	}



	public void processControls(ShortMessage message){
		if(message.getCommand()!=ShortMessage.CONTROL_CHANGE){
			//System.out.println("warn : no control change!");
			return;
		}
		int nbControl=message.getData1();
		int value=message.getData2();
		int controlIndex=trackControls.indexOf(new Integer(nbControl));
		if(nbControl==recordControl){
			System.out.println("Start recording");
			status.setRecording(true);
		}else if(nbControl==noRecordControl){
			System.out.println("Stop recording");
			status.setRecording(false);
		}else if(nbControl==clearControl){
			status.clearTrack();
			System.out.println("Clear track");
		}else if(controlIndex>=0){

			int nbTrack=controlIndex;
			status.setCurrentTrack(nbTrack);
			System.out.println("Change track = "+nbTrack);
			float newVolume=1.0f-(float)(127-value)/127.0f;
			newVolume*=1.5;
			status.setCurrentVolume(newVolume);
		}else if(nbControl==doubleLoopControl){	
			
			if(status.getCurrentTrackInfo()!=null){
				System.out.println("Double loop");
				status.getCurrentTrackInfo().nbOfLoop*=2;
			}
				
		}else if(nbControl==reduceLoopControl){
			
			if(status.getCurrentTrackInfo()!=null && status.getCurrentTrackInfo().nbOfLoop>1){
				System.out.println("Reduce loop");
				status.getCurrentTrackInfo().nbOfLoop/=2;
			}
				
		}else{
			System.out.println("info : no control change!");
		}

	}
	

	/*public void setLoopPlayer(LoopPlayer player){
		this.player=player;
	}*/
	

	public int getNbOfTrack(){
		return trackControls.size();
	}

}

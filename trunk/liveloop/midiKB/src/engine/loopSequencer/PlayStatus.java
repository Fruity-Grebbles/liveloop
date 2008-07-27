package engine.loopSequencer;

import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import com.marco.mvc.MVCModel;

public class PlayStatus extends MVCModel {
	
	
	static class TrackInfo extends MVCModel{
		float volume=1.0f;
		int nbOfLoop=1;
		int channel;
		int bank;
		int speed;//not yet implemented
		
	}
	protected TrackInfo[] trackInfos;
	protected TrackInfo currentTrackInfo;
	protected int currentTrackIndex;
	protected boolean isRecording;
	//protected Sequence sequence;
	
	public PlayStatus(int nbTrack){
		if(nbTrack<=0)
			throw new IllegalArgumentException(""+nbTrack);
		trackInfos=new TrackInfo[nbTrack];
		for(int i=0;i<trackInfos.length;i++){
			trackInfos[i]=new TrackInfo();
		}
	}
	
	public void setCurrentVolume(float volume){
		currentTrackInfo.volume=volume;
	}
	public void setCurrentLoopNumber(int number){
		currentTrackInfo.nbOfLoop=number;
	}
	
	public TrackInfo getCurrentTrackInfo() {
		return currentTrackInfo;
	}
	
	public void setCurrentBank(int bank){
		getCurrentTrackInfo().bank=bank;
		fireMethodCall("setBank", bank);
	}
	
	//-1 to set no current track
	public void setCurrentTrack(int track){		
		if(track<0){
			currentTrackInfo=null;
			return;
		}
		currentTrackInfo=trackInfos[track];
		this.fireMethodCall("setCurrentTrack", track);
	}

	public boolean isRecording() {
		return isRecording;
	}

	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
		this.fireMethodCall("setRecording", isRecording);
	}
	
	public void clearTrack(){
		this.fireMethodCall("clearTrack");
	}

	public TrackInfo[] getTrackInfos() {
		return trackInfos;
	}
	
	
	
	
	
	

}

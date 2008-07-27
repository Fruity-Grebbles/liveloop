package engine.loopSequencer.sequenceSystem;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Track;


public class SimpleSequence {
	
	
	
	
	protected SimpleTrack[] tracks;
	protected long ticklength=0;
	
	protected long fixedLength=-1;//indique que la sequence est de taille fixe-> TODO : remove this !!
	protected Object synch=new Object();
	
	protected int resolution;//resolution in PPQ mode ie the number of tick per Quarter Node ('une noire')
	
	public SimpleSequence(int nbTrack,int resolution){
		tracks=new SimpleTrack[nbTrack];
		for(int i=0;i<nbTrack;i++){
			createTrack(i);
		}
		this.resolution=resolution;
	}
	public void setFixedLength(long len){
		this.fixedLength=len;
	}
	
	public int getResolution(){
		return this.resolution;
	}
	
	public SimpleTrack createTrack(int trackNumber){
		if(trackNumber>tracks.length)
			throw new IllegalArgumentException(""+trackNumber);
		clearTrack(trackNumber);
		SimpleTrack t= new SimpleTrack(this);
		tracks[trackNumber]=t;
		return t;
	}
	public void clearTrack(int trackNumber){
		SimpleTrack t=tracks[trackNumber];
		if(t==null)
			return;
		t.clear();
		tracks[trackNumber]=null;
		updateTicklength();
	}
	
	public SimpleTrack getTrack(int trackNumber){
		return tracks[trackNumber];
	}
	
	public int getNbTrack(){
		return tracks.length;
	}
	
	protected void updateTicklength(){//Note : possible syncho problem if updating length while new events are created
		this.ticklength=0;
		for(int i=0;i<tracks.length;i++){
			SimpleTrack t=tracks[i];
			if(t!=null){
				this.ticklength=Math.max(this.ticklength, t.ticklength);
			}
		}		
	}
	public long getTickLength(){//Note : possible syncho problem if updating length of a track while new events are created
		if(this.fixedLength>0)
			return this.fixedLength;
		return this.ticklength;
	}
	void updateTickLen(long ticklength){
		this.ticklength=Math.max(ticklength, this.ticklength);		
	}

}

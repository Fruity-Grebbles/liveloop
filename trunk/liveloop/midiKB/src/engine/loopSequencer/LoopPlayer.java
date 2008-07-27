package engine.loopSequencer;

import javax.sound.midi.Sequencer;

import com.marco.mvc.ModelListener;




public class LoopPlayer {
	
	protected LoopSequencer sequencer;
	protected PlayStatus status;
	public LoopPlayer(LoopSequencer sequencer, PlayStatus status) {		
		this.sequencer = sequencer;
		this.status = status;
		init();
	}
	
	protected void init(){
		this.status.addListener("setRecording", new ModelListener<PlayStatus>(){
			public void onMethodCall(PlayStatus model, Object[] params) {
				sequencer.setRecording((Boolean)params[0]);
			}			
		});
		
		this.status.addListener("setCurrentTrack", new ModelListener<PlayStatus>(){
			public void onMethodCall(PlayStatus model, Object[] params) {
				sequencer.setRecordingTrack((Integer)params[0]);
			}			
		});
		this.status.addListener("clearTrack", new ModelListener<PlayStatus>(){
			public void onMethodCall(PlayStatus model, Object[] params) {
				sequencer.clearTrack(sequencer.getRecordingTrack());
			}			
		});
		
		
	}
	
	
	

}

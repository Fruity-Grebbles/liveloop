package com.marco.automate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class DefaultAutomate implements Automate{
	
	
	protected AutomateModel model;
	protected State currentState;
	protected int stateSleep;
	protected boolean stopped;
	public DefaultAutomate(int stateSleep){
		this.model=newModel();
	}
	
	public AutomateModel getAutomateModel() {
		return model;
	}
	protected AutomateModel newModel(){
		return new DefaultModel();
	}

	public State getCurrentState() {
		return currentState;
	}

	public void run() {
		State state=model.getEnterState();
		while(!stopped){
			this.currentState=state;
			state.enterState(model);
			
			List<Transition> t=model.getTransitions(state);
			for(Transition ti : t){
				if(ti.pass(model)){
					
					try {
						Thread.sleep(stateSleep);
					} catch (InterruptedException e) {						
					}
					state.exitState(model);
					
					state=model.getToState(ti);					
				}
			}			
		}
	}

	public void stop() {
		this.stopped=true;
	}
	
	public static void main(String[] args) {
		
		State forward=new EnterState() {		
			public void enterState(AutomateModel automate) {
						
			}		
		};
		State right=new EnterState() {	
			public void enterState(AutomateModel automate) {
				
			}		
		};
		State left=new EnterState() {	
			public void enterState(AutomateModel automate) {
				// TODO Auto-generated method stub
		
			}		
		};
		
		
	}
	

}

package com.marco.automate;

public interface Automate {
	
	public State getCurrentState();
	public void run();
	public void stop();
	public AutomateModel getAutomateModel();
	

}

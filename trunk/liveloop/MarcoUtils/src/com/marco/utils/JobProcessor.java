package com.marco.utils;

import java.util.LinkedList;
import java.util.List;
/**
 * 
 * Used to process runnables in asynchronous or synchronous way, in a job waiting queue
 * @author Marc Haussaire
 *
 */
public class JobProcessor {
	
	protected List fifo;
	class Job{
		Thread callingThread;//null if not in join mode
		Runnable toRun;
		boolean executed=false;
		Job(Runnable toRun,boolean join){
			if(join)
				callingThread=Thread.currentThread();
			this.toRun=toRun;
		}
		
	}
	public JobProcessor(){
		fifo=new LinkedList();
		Thread t=new Thread(){
			public void run(){
				while(true){
					processJob();
					//System.out.println(""+JobProcessor.this.waitingTime);
					synchronized (fifo) {
						if(fifo.size()==0){
							try {
								fifo.wait();
							} catch (InterruptedException e) {
							}
						}
					}
					
					
				}				
			}			
		};
		t.start();
	}


	/**
	 * Method to add a job to run.
	 * the method is thread safe
	 * @param : toRun : the job to run
	 * @join : 	if true, the calling thread wait until the job is correctly executed
	 * 			if false, the calling thread don't wait for the job execution 
	 * 
	 */
	public int addJob(Runnable toRun,boolean join){
		int size;
		Job j;
		synchronized (fifo) {
			j=new Job(toRun,join);
			fifo.add(j);
			size= fifo.size();
			fifo.notifyAll();
		}
		if(join){
			synchronized (j) {			
				while(!j.executed){
					try {
						j.wait();
					} catch (InterruptedException e) {
					}					
				}				
			}
		}

		return size;
	}
	
	public int getQueueSize(){
		synchronized (fifo) {
			return fifo.size();
		}
	}

	protected void processJob(){
		Job j;
		synchronized (fifo) {
			if(fifo.size()==0)
				return;
			j=(Job) fifo.remove(0);			
		}
		try{
			j.toRun.run();
			j.executed=true;
		}catch (Throwable e) {
			e.printStackTrace();
		}
		if(j.callingThread!=null){
			synchronized (j) {						
				j.notify();
			}			
		}		
	}
	
	
}

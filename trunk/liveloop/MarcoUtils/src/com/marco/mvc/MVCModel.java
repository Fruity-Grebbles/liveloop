package com.marco.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVCModel {
	
	
	protected Map<String,List<ModelListener>> listeners=new HashMap<String, List<ModelListener>>();
	public void addListener(String method,ModelListener listener){
		getListeners(method).add(listener);
	}
	public void removeListener(String method,ModelListener listener){
		getListeners(method).remove(listener);
	}
	protected List<ModelListener> getListeners(String method){
		List<ModelListener> l=listeners.get(method);
		if(l==null){
			l=new ArrayList<ModelListener>();
			listeners.put(method, l);
		}
		return l;
	}
	
	protected void fireMethodCall(String method,Object ... args){
		for(ModelListener l : getListeners(method)){
			System.out.println("Method called : "+method);
			l.onMethodCall(this,args);
		}
	}
	

}

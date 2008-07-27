package com.marco.mvc;

public interface ModelListener<C> {
	
	public void onMethodCall(C model,Object[] params);

}

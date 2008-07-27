package com.marco.utils.math.objects;

public interface Vector {
	
	
	public Object get(int index);
	public void set(int index,Object obj);
	public int getDimension();
	public void add(Object obj);
	
	/*public Object add(Object c);
	public Object mul(Object c);
	public Vector forEach(Operation c,Object ... arguments);*/
	

}

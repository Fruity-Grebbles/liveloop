package com.marco.utils.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.List;

import com.marco.utils.math.objects.Vector;
import com.marco.utils.math.operations.Ave;
import com.marco.utils.math.operations.Ops;
import com.marco.utils.math.operations.OpsTypes;
import com.marco.utils.math.operations.Sum;
import com.marco.utils.math.operations.vectors.ScalarProduct;

public class DefaultVector implements Vector{

	protected List<Object> elements;
	
	
	/*public DefaultVector(int dimension){
		elements=new ArrayList<Object>(dimension);
		for(int i=0;i<dimension;i++){
			elements.add(null);
		}
	}*/
	public DefaultVector(Object[] objs){
		elements=new ArrayList();
		Collections.addAll(elements, objs);
	}
	public DefaultVector(List objs){
		elements=objs;
	}
	public DefaultVector(double ... objs){
		elements=new ArrayList();
		for(int i=0;i<objs.length;i++){
			elements.add(objs[i]);
		}
	}
	
	public String toString(){
		return elements.toString();
	}
	
	/*public Object add(Object c) {
		if(c instanceof Vector){
			return Ops.VECT_ADD.calculate(this,(Vector)c);
		}else if(c instanceof RealNumber){
			return Ops.VECT_ADDSCALAR.calculate(this,(RealNumber)c);
		}
		throw new IllegalArgumentException("Cannot add with this class of element "+c.getClass());
	}
	public Object mul(Object c) {
		if(c instanceof Vector){
			return Ops.VECT_MUL.calculate(this,(Vector)c);
		}else if(c instanceof RealNumber){
			return Ops.VECT_MULSCALAR.calculate(this,(RealNumber)c);
		}
		throw new IllegalArgumentException("Cannot mul with this class of element "+c.getClass());
	}*/
	
	
	
	/*
	public Vector add(RealNumber n){
		Vector res=(Vector) this.copy();
		for(int i=0;i<res.getDimension();i++){
			res.set(i, n.add(this.get(i)));
		}
		return res;		
	}
	public Vector add(Vector v){
		if(v.getDimension()!=this.getDimension())
			throw new IllegalArgumentException("Dimensions must be the same");
		Vector res=new DefaultVector(this.getDimension());
		for(int i=0;i<v.getDimension();i++){
			Calculable c=v.get(i);
			Calculable th=this.get(i).copy();
			Calculable resi=th.add(c);
			res.set(i, resi);
		}
		return res;
	}
	
	public Vector multiply(Vector v){
		return new ScalarProduct().calculate(this,v);		
	}*/

	
	public Object get(int index) {
		return elements.get(index);
	}

	public void set(int index, Object obj) {
		for(int i=elements.size();i<=index;i++){
			elements.add(null);
		}
		elements.set(index, obj);
		
	}

	public int getDimension() {
		return elements.size();
	}
	

	/*public Calculable op(OpsTypes op, Calculable... arguments) {
		switch (op) {
		case ADD:
			return Ops.
		case MUL:
			
		case IDENT:
			
		case SQR:
			

		default:
			break;
		}
	}*/
	
	
	
	
	/*public Calculable copy() {
		DefaultVector v=new DefaultVector(this.getDimension());
		for(int i=0;i<v.getDimension();i++){
			v.set(i, this.get(i).copy());
		}
		return v;
	}

	public Calculable add(Calculable c) {
		if(c instanceof RealNumber){
			return Ops.
		}
	}

	public Calculable multiply(Calculable m) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	

	
	
	
	/*public Calculable getZeroAdd() {
		Vector c=new DefaultVector(this.getDimension());
		for(int i=0;i<c.getDimension();i++){
			
		}
	}
	public Calculable getZeroMul() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	public void add(Object obj) {
		elements.add(obj);
	}
	
	public static void main(String[] args) {
		System.out.println("Test 1");
		Vector v1=new DefaultVector();
		v1.add(new DefaultVector(3,2,1));
		v1.add(3);
		System.out.println("v1="+v1);
		Vector v2=new DefaultVector();
		v2.add( new DefaultVector(1,2,1));
		v2.add( new DefaultVector(1,2,1));
		System.out.println("v2="+v2);
		Vector v3=(Vector) Ops.ADD.calculate(v1,v2);
		System.out.println("v1+v2="+v3);
		Vector v4=(Vector) Ops.MUL.calculate(v1,v2);
		System.out.println("v1*v2="+v4);
		Operation geti=Ops.lambalise(Ops.GET, v4);
		System.out.println("moy(v1*v2)="+Ops.AVE.calculate(0,2,geti));
		
		System.out.println("Test 2");
		final Vector a=new DefaultVector();
		a.add(new DefaultVector(1));
		a.add(new DefaultVector(2));
		a.add(new DefaultVector(-1));
		a.add(new DefaultVector(5));
		final Vector x=new DefaultVector(2,5,-2,8);
		
		Operation aixi=new Operation(){
			public Object calculate(Object... params) {
				int i=(Integer) params[0];
				return Ops.MUL.calculate(a.get(i),x.get(i));
			}			
		};
		Operation ai2=new Operation(){
			public Object calculate(Object... params) {
				int i=(Integer) params[0];
				return Ops.MUL.calculate(a.get(i),a.get(i));
			}			
		};
		Object saixi=new Sum(aixi).calculate(0,3);
		Object sai2=new Sum(ai2).calculate(0,3);
		Object resul=Ops.DIV.calculate(saixi,sai2);
		System.out.println(resul);
		
		
		
		/*Operation aixi=new Operation(){
			public Object calculate(Object... params) {
				// TODO Auto-generated method stub
				return null;
			}
			
		};*/
	}
	
	

}

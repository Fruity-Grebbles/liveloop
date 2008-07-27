package com.marco.utils.math;

import java.util.List;

public class MathUtils {
	
	public static double[] cast(List<Double> l){
		double[] d=new double[l.size()];
		for(int i=0;i<l.size();i++){
			d[i]=l.get(i);
		}
		return d;
	}
	public static double scalarProduct(double[] v1,double[] v2){
		double res=0;
		if(v1.length!=v2.length)
			throw new IllegalArgumentException();
		for(int i=0;i<v1.length;i++){
			res+=v1[i]*v2[i];
		}
		return res;
	}
	public static double scalarProduct(List<Double> v1,List<Double> v2){
		return scalarProduct(cast(v1),cast(v2));
	}
	
	public static double[] multiply(double[] v,double scalar){
		double[] res=new double[v.length];
		for(int i=0;i<v.length;i++){
			res[i]=v[i]*scalar;
		}
		return res;
	}
	public static double[] add(double[] v,double scalar){
		double[] res=new double[v.length];
		for(int i=0;i<v.length;i++){
			res[i]=v[i]+scalar;
		}
		return v;
	}
	public static double[] add(double[] v1,double[] v2){
		if(v1.length!=v2.length)
			throw new IllegalArgumentException();
		double[] res=new double[v1.length];
		for(int i=0;i<v1.length;i++){
			res[i]=v1[i]+v2[i];
		}
		return res;
	}
	public static double[] less(double[] v1){
		double[] res=new double[v1.length];
		for(int i=0;i<v1.length;i++){
			res[i]=-v1[i];
		}
		return res;
	}
	public static double[] less(double[] v1,double[] v2){
		return add(v1,less(v2));
	}
	
	public static double[] inv(double[] v1){
		double[] res=new double[v1.length];
		for(int i=0;i<v1.length;i++){
			res[i]=1/v1[i];
		}
		return res;
	}
	
	
	

}

package com.marco.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ObjectPropertySerializer {
	
	public static final String CLASS_NAME_PROP="_Class_Name";
	/*public static interface CustomWrapper{
		
		public String getType();
		public String serialize(Object );
		public 
		
		
		
	}*/
	
	
	public static class FieldsNotSetException extends Exception{
		private List fields;
		private Object result;
		private FieldsNotSetException(Object result,List fields){
			this.fields=fields;
			this.result=result;
		}
		public List getFields(){
			return fields;
		}
		public Object getPropertyObject(){
			return result;
		}
		public String getMessage() {
			return "Some fields doesn't exist in parent object "+fields;
		}
		
	}
	
	private static String serialize(Object obj){
		if(obj.getClass().isArray()){
			return Arrays.asList((Object[])obj).toString();
		}
		return ""+obj;
	}

	
	public static Properties storeObject(Object obj,boolean accessible){
		Properties prop=new Properties();
		Field[] fields=obj.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field f=fields[i];
			f.setAccessible(accessible);
			Object value;
			try {
				value=f.get(obj);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Error(e);//shoul not happen
			}
			prop.setProperty(f.getName(), serialize(value));			
		}
		prop.setProperty(CLASS_NAME_PROP, obj.getClass().getName());
		return prop;
	}
	public static void storeObject(Object obj,String propertyFile){
		Properties prop=storeObject(obj,true);
		File f=new File(propertyFile);
		FileOutputStream s;
		try {
			s = new FileOutputStream(f);
			String comment="property file for the class "+obj.getClass().getName();
			prop.store(s, comment);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}		
		
	}
	
	private static Properties load(String propertyFile){
		Properties prop=new Properties();
		try {
			prop.load(new FileInputStream(new File(propertyFile)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("File not found or not a valid property file");
		}
		return prop;
	}
	public static Object loadObject(String propertyFile) throws FieldsNotSetException{
		Properties prop=load(propertyFile);
		String clazzN=prop.getProperty(CLASS_NAME_PROP);
		if(clazzN==null)
			throw new IllegalArgumentException("Not a valid property file for this loading");
		Object obj;
		Class clazz;
		try {
			clazz=Class.forName(clazzN);
			obj=clazz.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Class "+clazzN+" not found or no default constructor.");
		}
		loadObject(obj,prop);
		return obj;
	}
	private static void loadObject(Object obj,Properties prop) throws FieldsNotSetException{
		Class clazz=obj.getClass();
		Enumeration enume=prop.propertyNames();
		List fieldsNotFound=new ArrayList();
		while(enume.hasMoreElements()){
			String name=(String)enume.nextElement();
			if(CLASS_NAME_PROP.equals(name))
				continue;//class name is a "special" property
			String value=prop.getProperty(name);
			Field f;
			try {
				f = clazz.getDeclaredField(name);
				f.setAccessible(true);
				setField(obj, f, value);
			} catch (Exception e) {
				fieldsNotFound.add(name);
			}			
		}
		if(fieldsNotFound.size()>0)
			throw new FieldsNotSetException(obj,fieldsNotFound);		
	}
	public static void loadObject(String propertyFile,Object obj) throws FieldsNotSetException{
		Properties prop=load(propertyFile);
		loadObject(obj,prop);
	}
	
	/*protected static Object parse(String value,Field f){
		if(f.getType()==String.class)
			return value;
		else if(f.getType()==int.class)
			return Integer.parseInt(value);
		else if(f.getType()==boolean.class)
			return Boolean.valueOf(value).booleanValue();
		else if(f.getType()==long.class)
			return Long.parseLong(value);
		else if(f.getType()==short.class)
			return Short.parseShort(value);
		else if(f.getType()==float.class)
			return Float.parseFloat(value);
		else if(f.getType()==double.class)
			return Double.parseDouble(value);
		else if(f.getType()==byte.class)
			return Byte.parseByte(value);
		if(f.getType().isArray()){
			String[] tab=value.split(",");
			if(f.getType().)
			
			
		}
	}*/
	
	public static void setField(Object obj,Field f,String value) throws NumberFormatException, IllegalArgumentException, IllegalAccessException{
		if(f.getType()==String.class)
			f.set(obj,value);
		else if(f.getType()==int.class)
			f.setInt(obj,Integer.parseInt(value));
		else if(f.getType()==boolean.class)
			f.setBoolean(obj, Boolean.valueOf(value).booleanValue());
		else if(f.getType()==long.class)
			f.setLong(obj, Long.parseLong(value));
		else if(f.getType()==short.class)
			f.setShort(obj, Short.parseShort(value));
		else if(f.getType()==float.class)
			f.setFloat(obj, Float.parseFloat(value));
		else if(f.getType()==double.class)
			f.setDouble(obj, Double.parseDouble(value));
		else if(f.getType()==byte.class)
			f.setByte(obj, Byte.parseByte(value));		
		else
			throw new IllegalArgumentException("Field cannot be set : "+f.getName());			
		
	}

}

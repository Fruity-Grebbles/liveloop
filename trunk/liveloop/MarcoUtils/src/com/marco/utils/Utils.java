package com.marco.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class Utils {
	
	public static String printMap(Map map){
		Map.Entry ent=null;
		String res="";
		Iterator i=map.entrySet().iterator();
		
		ent=(Map.Entry)i.next();
		for(;i.hasNext();ent=(Map.Entry)i.next()){
			res+=ent.getKey()+":\n"+ent.getValue()+"\n";
		}
		res+=ent.getKey()+":\n"+ent.getValue()+"\n";
		return res;
	}
	
	
	public static Object createInstance(String clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class c=Class.forName(clazz);
		return c.newInstance();
	}
	
	public static Class[] getClasses(Object[] args){
		Class[] tab=new Class[args.length];
		for(int i=0;i<args.length;i++){
			tab[i]=args[i].getClass();
		}
		return tab;
	}
	public static double getVersion(){
		String version=System.getProperty("java.specification.version");
		return Double.parseDouble(version);
	}
	
	public static Object callMethod(Object obj,String methName,Object[] params) throws NoSuchMethodException{
		Method m=findMethod(obj, methName, params);
		if(m==null)
			throw new NoSuchMethodException("No such method "+methName+"was found on "+obj.getClass().getName());
		try {
			return m.invoke(obj, params);
			
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The method that was found doesnt have goods arguments, don't use methods with the same name and same number of arguments");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Error(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}
	public static Object callMethod(Object obj,Method m,Object[] args){
		try {
			return m.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}			
	}
	
	public static Method findMethodStandard(Object obj,String methName,Object[] params){
		return findMethodStandard(obj.getClass(), methName, params);
	}
	public static Method findMethodStandard(Class c,String methName,Object[] params){		
		try {
			Method meth=c.getMethod(methName, getClasses(params));
			return meth;
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new Error(e);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	public static Method findMethod(Class c,String methName,Object[] params){
		//try the standard
		Method m=findMethodStandard(c, methName, params);
		if(m!=null)
			return m;
		
		//try another way
		//if an argument is primitive (ex : int), the autoboxing will make an Wrapper instead (ex: Integer)
		//and the method will not be found
		//try to compare only with the methName and the number of arguments;
		//todo : use a comparator for primitive and wrappers types to find the correct method ...
		Method methFound=null;
		for(int i=0;i<c.getMethods().length;i++){
			Method me=c.getMethods()[i];
			//same name and same number of parameters
			String meName=me.getName();
			if(meName.equals(methName) && params.length==me.getParameterTypes().length){
				if(methFound!=null)
					throw new IllegalArgumentException("2 methods with primitives params and same number of params are found in the class "+c.getName()+" this is not yet implemented.");
				methFound=me;
			}			
		}
		return methFound;//return null if nothing was found	
	}
	public static Method findMethod(Object obj,String methName,Object[] params){
		return findMethod(obj.getClass(), methName, params);
	}
	//find a method only with the name, return the first found
	public static Method findMethodByName(String methName,Class c){
		for(int i=0;i<c.getMethods().length;i++){
			Method m=c.getMethods()[i];
			if(m.getName().equals(methName))
				return m;
		}
		return null;
	}
	
	public static String replaceVar(String aString,String varName,String varValue){
		varName="\\{"+varName+"\\}";
		String result=aString.replaceAll(varName,varValue);
		return result;	
	}
	public static String replaceVars(String aString,String[] varNames,String[] values){
		if(varNames.length!=values.length)
			throw new IllegalArgumentException();
		for(int i=0;i<varNames.length;i++){
			aString=replaceVar(aString, varNames[i],values[i]);
		}
		return aString;
	}
	
		//remove the last string
	 public static String reLast(String s,String last){
		if(s.length()<last.length())
			return s;
		return s.substring(0, s.length()-last.length());
	}
	 
	 public static String getFileExtension(String file){
		 String[] split=file.split("\\.");
		 return split[split.length-1];		 
	 }
	 //find a method in the specified class or try to find also in it's parent class
	 public static Method findMethodRec(Class clazz,String name,Class[] paramClasses) throws SecurityException, NoSuchMethodException{
		 try{
			 Method m=clazz.getDeclaredMethod(name, paramClasses);
			 return m;
		 }catch (NoSuchMethodException e) {
			Class parent=clazz.getSuperclass();
			if(parent==null)
				throw e;
			return findMethodRec(parent, name, paramClasses);
		}
	 }
	 
	 public static String getFileNoExtension(String file){
		 String ext=getFileExtension(file);
		 return file.replace("."+ext, "");
	 }
    public static String getStackTrace(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
	
	public static boolean isWindows(){
		String os=System.getProperty ( "os.name");
		if(os==null)
			return false;
		return os.startsWith("Windows");
	}
	
	//says if a file is a link or not
	//TODO : test this function on unix file system
	  public static boolean isLink(File file) {
		  if(!file.exists())
			  return false;
	    try {
	      if (!file.exists())
		return true;
	      else
		{
		  String cnnpath = file.getCanonicalPath();
		  String abspath = file.getAbsolutePath();
		  return !abspath.equals(cnnpath);
		}
	    }
	    catch(IOException ex) {
	      System.err.println(ex);
	      return true;
	    }
	  }
	  
	  
	  protected static long time;
	  public static void printTime(){
		  printTime("Time");
	  }
	  public static void printTime(String msg){
			 Log.debug("---"+msg+"="+(System.currentTimeMillis()-time));
			 initTime();
		  }
	  public static void initTime(){
		  time=System.currentTimeMillis();
	  }
	  
	  
	  private static String addZ(int t){
		 String da=""+t;
		 if(da.length()==1)
			 da="0"+da;
		 return da;
	  }
	public static String dateToString(Calendar c) {
		//MHS 06/05/2008: pb de compatibilité avec la plateforme: on enleve le -1
		//return addZ(c.get(Calendar.DAY_OF_MONTH))+"-"+addZ(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.YEAR);
		return addZ(c.get(Calendar.DAY_OF_MONTH))+"-"+addZ(c.get(Calendar.MONTH))+"-"+c.get(Calendar.YEAR);
	}
	public static String dateToString(long time){
		Calendar c=new GregorianCalendar();
		c.setTimeInMillis(time);
		return dateToString(c);		
	}
	public static Calendar stringToDate(String date){
		String[] split=date.split("[\\-\\\\/]");// caraters \,/ or -
		Calendar c=new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split[0]));
		//c.set(Calendar.MONTH, Integer.parseInt(split[1])-1);//MHS 06/05/2008: pb de compatibilité avec la plateforme: on enleve le -1
		c.set(Calendar.MONTH, Integer.parseInt(split[1]));
		c.set(Calendar.YEAR, Integer.parseInt(split[2]));
		return c;
	}
	/**
	 * @author Marc
	 * @param folder : répertoire à purger
	 * @param rubishTime : temps (en ms) à partir duquel les fichiers sont considérés comme vieux
	 */
	public static void purgeOldFile(File folder,long rubishTime){
		File f=folder;
		long today=System.currentTimeMillis();
		for(File child : f.listFiles()){
			long date=child.lastModified();
			if(today-date>rubishTime){
				if(!child.delete())
					Log.warn("Cannot remove pdf file: "+child);
				else
					Log.debug("Pdf file removed");				
			}
		}
	}
	
	public static String newFileName(String name){
		return name+"-"+(""+Math.random()).substring(5);
	}

}

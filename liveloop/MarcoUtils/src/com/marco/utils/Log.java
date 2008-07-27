package com.marco.utils;


public class Log {
	

	public static final int LEVEL_DEBUG=3;
	public static final int LEVEL_WARN=2;
	public static final int LEVEL_ERROR=1;
	public static final int LEVEL_NOLOG=0;
	public static int level=LEVEL_DEBUG;
	public static boolean showTrace=true;
	
	
	public static void debug(String text){
		if(level>=LEVEL_DEBUG){
			System.out.println(getTrace()+" "+text);			
		}			
	}
	public static void warn(String text){
		if(level>=LEVEL_WARN){
			System.out.println(getTrace()+" "+text);			
		}			
	}
	public static void error(String text){
		if(level>=LEVEL_ERROR){
			System.out.println(getTrace()+" "+text);			
		}			
	}
	public static void error(Throwable error){
		if(level>=LEVEL_ERROR){
			error.printStackTrace();			
		}
	}
	public static void warn(Throwable error){
		if(level>=LEVEL_WARN){
			error.printStackTrace();			
		}
	}
	public static void printTraceWarn(Throwable error){
		if(level>=LEVEL_WARN){
			error.printStackTrace();			
		}
	}
	public static void printTraceError(Throwable error){
		if(level>=LEVEL_ERROR){
			error.printStackTrace();			
		}
	}
	
	
	//get the current method called
	//the stack element is the 2:
	//0->this method
	//1->the debug method
	//2->the calling method (that is useful)
	public static String getTrace(){
		if(!showTrace)
			return "";
		Exception e=new Exception();
		StackTraceElement ele=e.getStackTrace()[2];
		String trace=ele.getClassName()+":"+ele.getMethodName()+":"+ele.getLineNumber();
		return trace;		
	}

}

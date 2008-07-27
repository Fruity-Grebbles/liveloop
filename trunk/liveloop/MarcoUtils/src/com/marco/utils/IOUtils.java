package com.marco.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
	
	
	public static String streamToString(InputStream stream) throws IOException{
		byte[] buf=new byte[1024];
		int av=stream.available();
		StringBuffer res=new StringBuffer();
		while(av>0){
			int r=stream.read (buf,0,Math.min(buf.length,av));
			res.append(new String(buf,0,r));
			av=stream.available();
		}
		return res.toString();
	}
	
	public static String shortStreamToString(InputStream stream) throws IOException{
		BufferedReader in = new BufferedReader(
                new InputStreamReader(stream));
		String res="";
		String inputLine;
        while ((inputLine = in.readLine()) != null){        	
        	res+=inputLine+"\n";      		
        }
        	
        in.close();
        return res;
	}

}

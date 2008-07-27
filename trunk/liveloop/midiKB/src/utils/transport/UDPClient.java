package utils.transport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	
	protected DatagramSocket socket;
	protected UDPSerializer serializer;
	protected InetAddress addr;
	protected int port;
	public UDPClient(String host,int port,UDPSerializer serializer){
        try {
        		
        	this.socket = new DatagramSocket();//port,InetAddress.getByName(host));
        	
			this.port=port;
			this.addr=InetAddress.getByName(host);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        this.serializer=serializer;   
	}
	public void close(){
		 socket.close();
	}
	public void sendObject(Object obj) throws IOException,SerializeException{
		byte[] bytes=serializer.serialize(obj);
		DatagramPacket pk=new DatagramPacket(bytes,serializer.getPacketSize(),addr,port);
		socket.send(pk);
	}
	
	

}

package utils.transport;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer{


	protected Runnable udpThread=new Runnable() {
		public void run() {
			doRun();			
		}
	};
	protected DatagramSocket socket = null;
	protected boolean stopped=false;
	protected UDPSerializer serializer;
	protected ObjectReceiver receiver;
	
	protected int remotePort;
	protected InetAddress remoteip;
	public UDPServer(int port,UDPSerializer serializer,ObjectReceiver receiver) throws SocketException{
		if(port>0)
			this.socket=new DatagramSocket(port);
		else
			this.socket=new DatagramSocket();//client
		this.serializer=serializer;
		this.receiver=receiver;
	}
	public void start(){
		stopped=false;
		Thread t=new Thread(udpThread);
		t.start();		
	}

	public void stop(){
		stopped=true;
		socket.close();
	}

	protected void doRun(){
		while(!stopped){
			byte[] buf=new byte[serializer.getPacketSize()];
			DatagramPacket pk=new DatagramPacket(buf,buf.length);
			try {
				socket.receive(pk);
				remoteip=pk.getAddress();
				remotePort=pk.getPort();
			} catch (IOException e) {
				if(e instanceof SocketException){
					System.out.println("Connection closed");
					return;
				}
				e.printStackTrace();//bad receive
			}


			Object obj;
			try {
				obj = serializer.unserialize(pk.getData(), pk.getOffset(), pk.getLength());
				receiver.objectReceived(obj);
			} catch (SerializeException e) {
				e.printStackTrace();
			}

			


		}
	}

	
	public void sendObject(Object obj) throws IOException,SerializeException{
		byte[] bytes=serializer.serialize(obj);
		DatagramPacket pk=new DatagramPacket(bytes,serializer.getPacketSize(),remoteip,remotePort);
		socket.send(pk);
	}


}

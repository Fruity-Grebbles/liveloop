package utils.transport;

public interface UDPSerializer {
	
	public byte[] serialize(Object obj) throws SerializeException;
	public Object unserialize(byte[] bytes,int offset,int length) throws SerializeException;
	public int getPacketSize();

}

package utils.transport;

public interface Communicator {
	
	public void send(Object obj);
	public void receive(Object obj);

}

package sharedModels;

//import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

/*
 * purpose of readThread: receive input from a socket and put it in a queue, the owner examines the queue when they get free
 *
 * */
public class ReadThread extends Thread {
	private DataInputStream in;
	private Queue<byte[]> out;
	byte[] msg;
	// LoginRequest lr;
	// Serialization.SerializedObject<LoginRequest> sobj;

	/**
	 * @return
	 */
	public Queue<byte[]> getOut() {
		return out;
	}

	/**
	 * @param inputSocket
	 * @throws IOException
	 */
	public ReadThread(Socket inputSocket) throws IOException {
		this.in = new DataInputStream(inputSocket.getInputStream());
		this.out = new LinkedList<byte[]>();
		this.msg = new byte[1000000];
		// lr = new LoginRequest();
		// sobj = new SerializedObject<LoginRequest>();
		start();
	}

	/**
	 * @return
	 */
	public boolean execution() {
		try {
			byte[] array = new byte[25 * 1024]; // max size is 25kb
			in.read(array); // read from stream
			out.add(array); // throw it in the queue
			return true;
		} catch (SocketException e) {
			//System.out.println("Socket exception encountered");
			//System.out.println(e.getMessage());
			//TODO: find cause of connection reset
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *
	 */
	public void run() {
		while (execution()) {

		}
	}
}
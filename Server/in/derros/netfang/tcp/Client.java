package in.derros.netfang.tcp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Client extends Thread {
	private boolean indicator;
	private String statusListener;
	private InetAddress host;
	private int port;
	private InetAddress localAddress;
	private int localPort;
	private byte[] handin;
	private byte[] handout;
	
	public void runClient(int i, byte[] sendingPacket) {
		handin = sendingPacket;
		this.start();
	}
	
	public void run() {
		try {
			host = InetAddress.getByName("");
			Socket socket = new Socket(host, port, localAddress, localPort);
			OutputStream out = socket.getOutputStream();
			DataOutputStream dout = new DataOutputStream(out);
			dout.write(handout);
			
		} catch(Exception e) {
			indicator = false;
			statusListener = "[startClient] Failed to start client";
		}
	}
	
	public void handIn(byte[] data) {
		handin = data;
	}
	
	public byte[] handOut() {
		return handout;
	}
}

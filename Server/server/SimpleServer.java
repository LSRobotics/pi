package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SimpleServer extends Thread {
	
	public volatile double currentCenterX;
	public volatile double currentCenterY;
	private ServerSocket serverSocket;
	private int port = 5805;
	
	public SimpleServer() throws IOException {
		serverSocket = new ServerSocket(port);
		currentCenterX = 0;
		currentCenterY = 0;
		this.start();
	}
	
	public void run() {
		try {
			Socket server = serverSocket.accept();
			DataOutputStream dOut = new DataOutputStream(server.getOutputStream());
			while (true) {
				byte[] packet = new byte[8]; //length of two doubles
				ByteBuffer buffer = ByteBuffer.wrap(packet);
				buffer.putDouble(0, currentCenterX);
				buffer.putDouble(1, currentCenterY);
				dOut.write(packet);
				dOut.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
}

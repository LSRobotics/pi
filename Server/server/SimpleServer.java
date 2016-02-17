package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer extends Thread {
	
	public volatile double currentCenterX;
	public volatile double currentCenterY;
	private ServerSocket serverSocket;
	private int port;
	
	public SimpleServer() throws IOException {
		serverSocket = new ServerSocket(port);
		currentCenterX = 0;
		currentCenterY = 0;
		this.start();
	}
	
	public void run() {
		try {
			while (true) {
				Socket server = serverSocket.accept();
				DataOutputStream dOut = new DataOutputStream(server.getOutputStream());
				dOut.writeDouble(currentCenterX);
				dOut.writeDouble(currentCenterY);
				dOut.flush();
				dOut.close();
				server.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

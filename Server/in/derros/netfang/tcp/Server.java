package in.derros.netfang.tcp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

//backlog shall be 8192
public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private int backlog = 8192;
	public InetAddress ip;
	public int port;
	private boolean appIndicator;
	public String statusListener;
	public byte[] handoff;
	public byte[] handin;
	public boolean suspended = false;// the flag indicates whether thread is suspended or not.

	/**
	 * 
	 * @param instruction
	 * 	0: start the server;
	 *  1: stop the server;
	 */
	public void runServer(int instruction) {
		appIndicator = true;
		
		if (instruction == 0){
			this.start();
		}
		if (instruction == 1) {
			if(!suspended) {
				this.suspendServer();
			}
		}
		if (instruction == 2) {
			if(suspended) {
				this.resumeServer();
			}
		}
	}
	
	public void run(){
		try{
			Socket server = serverSocket.accept();
			serverSocket = new ServerSocket(port, backlog);
			serverSocket.setReuseAddress(true);
			serverSocket.setSoTimeout(2500);
			serverSocket.setPerformancePreferences(1, 2, 0);
			serverSocket.setReceiveBufferSize(128);
			
			while (appIndicator){
				//Server
				DataInputStream dataIn = new DataInputStream(server.getInputStream());
				dataIn.read(handoff);
				OutputStream out = server.getOutputStream();
				DataOutputStream dout = new DataOutputStream(out);
				dout.write(handin);
			
				//suspend control
				synchronized(this) {
		            while(suspended) {
						wait();
		            }
		        }
			}
			
			server.close();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		catch(SocketTimeoutException s ) {
			statusListener = "[startServer] Socket timed out \n";
		} 
		catch(Exception e) {
			statusListener = "[startServer] Unknown error \n";
		}
	}

	 private void suspendServer() {
         suspended = true;
     }
     private void resumeServer() {
         suspended = false;
         notify();
     }
     public void startReading() {
    	 appIndicator = true;
     }
     public void stopReading() {
    	 appIndicator = false;
     }
}

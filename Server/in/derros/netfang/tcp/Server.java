package in.derros.netfang.tcp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

//backlog shall be 8192
public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private int backlog = 8192;
	private InetAddress ip;
	private int port;
	private boolean appIndicator;
	private String statusListener;
	private byte[] handout;
	private byte[] handin;
	private boolean suspended = false;// the flag indicates whether thread is suspended or not.
	public enum Instruction {
		START,
		SUSPEND,
		RESUME
	}
	
	public void runServer(Instruction i) {
		appIndicator = true;
		
		if (i == Instruction.START){
			this.start();
		}
		if (i == Instruction.SUSPEND) {
			if(!suspended) {
				this.suspendServer();
			}
		}
		if (i == Instruction.RESUME) {
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
				dataIn.read(handout);
				OutputStream out = server.getOutputStream();
				DataOutputStream dout = new DataOutputStream(out);
				dout.write(handin);
			
				//suspend control	
	            while(suspended) {
					wait();
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
     
     public void handIn(byte[] data) {
    	 handin = data;
     }
     
     public byte[] handout() {
    	 return handout;
     }
}

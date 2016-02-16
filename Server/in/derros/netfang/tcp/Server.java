package in.derros.netfang.tcp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

//backlog shall be 8192
public class Server extends Thread implements Runnable{
	
	private ServerSocket serverSocket;
	private int backlog = 8192;
	public InetAddress ip;
	public int port;
	public boolean indicator;
	public boolean appIndicator;
	private String statusListener;
	public byte[] handoff;
	public byte[] handin;
	public Thread t = new Thread(this, "pi Server");
	public boolean suspendFlag = false;// the flag indicates whether thread need to suspended or not.
	
	
	/**
	 * 
	 * @param instruction
	 * 	0: start the server;
	 *  1: stop the server;
	 * @param data
	 */
	public void runServer(int instruction, byte[] data) {
		//TODO add something?
		if (instruction == 0){
			try {
				this.startServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.start();
		}
		if (instruction == 1)
			this.suspendServer();
		if (instruction == 2)
			this.resumeServer();
	}
		
	private void startServer() throws IOException {
		while(indicator == true) {
			try {
				Socket server = serverSocket.accept();
				serverSocket = new ServerSocket(port, backlog);
				serverSocket.setReuseAddress(true);
				serverSocket.setSoTimeout(2500);
				serverSocket.setPerformancePreferences(1, 2, 0);
				serverSocket.setReceiveBufferSize(128);
				while(appIndicator == true) {
					DataInputStream dataIn = new DataInputStream(server.getInputStream());
					dataIn.read(handoff);
					OutputStream out = server.getOutputStream();
					DataOutputStream dout = new DataOutputStream(out);
					dout.write(handin);
				}
				server.close();
				indicator = false;
			} catch(SocketTimeoutException s ) {
				indicator = false;
				statusListener = "[startServer] Socket timed out \n";
			} catch(Exception e) {
				indicator = false;
				statusListener = "[startServer] Unknown error \n";
			}
		}
	}
	
	public void run(){
		try{
			while (true){
				//Server
				//suspend control
				synchronized(this) {
		            while(suspendFlag) {
						wait();
		            }
		        }
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	 private void suspendServer() {
         suspendFlag = true;
     }
     private synchronized void resumeServer() {
         suspendFlag = false;
         notify();
     }
}

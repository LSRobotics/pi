import in.derros.netfang.tcp.Server;

/**
 * Main.java
 * starting file for pi server
 * @author Team 5181
 *
 */
public class main {

	public static void main(String[] args){
		Server server = new Server();
		server.runServer(0, null);
		
	}
	
}

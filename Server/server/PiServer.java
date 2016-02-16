package server;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import in.derros.netfang.tcp.Server;

/**
 * Main.java
 * starting file for pi server
 * @author Team 5181
 *
 */
public class PiServer {

	static String pathToImages = "/var/www/phooolishstream/";
	public static void main(String[] args){
		Server server = new Server();
		server.runServer(Server.Instruction.START);
		BufferedImage img = ImageIO.read(new File(pathToImages));
	}
	
	
}

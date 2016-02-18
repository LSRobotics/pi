package server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

/**
 * Main.java
 * starting file for pi server
 * @author Team 5181
 *
 */
public class PiMain {
	
	static String pathToImages = "/var/www/phooolishstream/default.jpg";
	
	public static void main(String[] args){
		Vision vision = new Vision();
		try {
			SimpleServer server = new SimpleServer();
			while (true) {
				BufferedImage img = ImageIO.read(new File(pathToImages));
				double[] boulderCord = vision.getBoulderCoord(img);
				server.currentCenterX = boulderCord[0];
				server.currentCenterY = boulderCord[1];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

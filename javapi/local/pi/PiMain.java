package local.pi;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

/**
 * Imports essential modules
 */
import org.lschs.utilities.*;

/**
 * Imports command execution module for *nix cmd
import in.derros.cmd.*;

/**
 * Imports external modules
 */
import local.pi.extern.*;


/**
 * PiMain.java
 * starting file for pi server
 * @author Team 5181
 *
 */
public class PiMain {
	
	static String pathToImages = "/var/www/phooolishstream/default.jpg";
	
	public static void main(String[] args){
		Old vision = new org.lschs.utilities.vision.Old();
		try {
			SimpleServer server = new org.lschs.utilities.net.SimpleServer();
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


package server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.opencv.imgcodecs.Imgcodecs;
import javax.imageio.ImageIO;
import org.opencv.videoio.VideoCapture;
import org.opencv.core.Mat;
/**
 * Main.java
 * starting file for pi server
 * @author Team 5181
 *
 */
public class PiMain {
	
	static String pathToImages = "/var/www/html/phoolishstream2/default.jpg";
	
	public static void main(String[] args) {
		Vision vision = new Vision();
		try {
			SimpleServer server = new SimpleServer();
			VideoCapture video = new VideoCapture(0);
			while (true) {
				Mat img = new Mat();
				video.read(img);
				int[] boulderCord = vision.getBoulderCoord(img);
				server.currentCenterX = boulderCord[0];
				server.currentCenterY = boulderCord[1];
				//System.out.println("CenterX: " + server.currentCenterX + " CenterY: " + server.currentCenterY);
				Thread.sleep(100);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

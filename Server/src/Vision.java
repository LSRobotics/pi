package server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Size;
public class Vision {
	
	static double[] BGRUpper = {255, 255, 255}; //In BGR Order
	static double[] BGRLower = {156, 137, 138}; //In BGR Order
	
	public Vision() {
		//System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//System.loadLibrary("/usr/local/share/OpenCV/java");
	}
	
	public int[] getBoulderCoord(Mat img) {
		//byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		//Mat image_final= new Mat(img.getWidth(), img.getHeight(), CvType.CV_8UC3);
		//image_final.put(0, 0, pixels);
		Mat rgbFiltered = RGBThresh(img, new Scalar(BGRLower), new Scalar(BGRUpper));
		Mat eroded = new Mat();
		Imgproc.erode(rgbFiltered, eroded, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2)));
		Imgcodecs.imwrite("/var/www/html/phoolishstream2/default.jpg", eroded);
		ArrayList<MatOfPoint> contours = findContours(eroded);
		if (contours.size() == 0) {
			return new int[] {-1, -1};
		}
		MatOfPoint biggestContour = findBiggestContour(contours);
		Rect boundingBox = Imgproc.boundingRect(biggestContour);
		return new int[] {getCenterX(boundingBox), getCenterY(boundingBox)};
	}
	
	private ArrayList<MatOfPoint> findContours(Mat src) {
		Mat tmp = new Mat();
		src.copyTo(tmp);
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(tmp, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		return contours;
	}
	
	private MatOfPoint findBiggestContour(ArrayList<MatOfPoint> contours) {
		int maxAreaIndex = 0;
		double maxArea = 0;
		for (int i = 0; i < contours.size(); i++) {
			double area = Imgproc.contourArea(contours.get(i));
			if (area > maxArea) {
				maxArea = area;
				maxAreaIndex = i;
			}
		}
		return contours.get(maxAreaIndex);
	}
	
	private int getCenterY(Rect r) {
		return r.y + (r.height / 2);
	}
	
	private int getCenterX(Rect r) {
		return r.x + (r.width / 2);
	}
	
	private Mat RGBThresh(Mat src, Scalar lowerb, Scalar upperb) {
		Mat dst = new Mat();
		Core.inRange(src, lowerb, upperb, dst);
		//Imgcodecs.imwrite("/var/www/html/phoolishstream2/default.jpg", dst);
		return dst;
	}
	
	private BufferedImage matToImg(Mat src, int width, int height) {
		BufferedImage out;
		byte[] data = new byte[width * height * (int)src.elemSize()];
		int type;
		src.get(0, 0, data);
		
		if (src.channels() == 1) {
			type = BufferedImage.TYPE_BYTE_GRAY;
		}
		else {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		out = new BufferedImage(width, height, type);
		out.getRaster().setDataElements(0, 0, width, height, data);
		return out;
	}
}

package org.lschs.utilites.vision;

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

public class LinearAlg {
	public double[] getBoulderCoord(BufferedImage img) {
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		Mat image_final= new Mat(img.getWidth(), img.getHeight(), CvType.CV_8UC3);
		image_final.put(0, 0, pixels);
		Mat rgbFiltered = RGBThresh(image_final, new Scalar(BGRLower), new Scalar(BGRUpper));
		Mat eroded = new Mat();
		Imgproc.erode(rgbFiltered, eroded, new Mat(), new Point(-1, -1), 2);
		ArrayList<MatOfPoint> contours = findContours(eroded);
		if (contours.size() == 0) {
			return new double[] {-1, -1};
		}
		MatOfPoint biggestContour = findBiggestContour(contours);
		Rect boundingBox = Imgproc.boundingRect(biggestContour);
		return new double[] {getCenterX(boundingBox), getCenterY(boundingBox)};
	}
}

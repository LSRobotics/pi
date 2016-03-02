package org.lschs.utilities.vision;

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

public class Contours extends Vision {
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
}

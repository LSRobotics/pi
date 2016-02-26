package org.lschs.utilities.vision;

//TODO import stuffs
public class Contours {
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

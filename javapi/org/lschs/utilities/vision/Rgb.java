package org.lschs.utilities.vision;

public class Rgb {

	public Mat RGBThresh(Mat src, Scalar lowerb, Scalar upperb) {
		Mat dst = new Mat();
		Core.inRange(src, lowerb, upperb, dst);
		return dst;
	}
}

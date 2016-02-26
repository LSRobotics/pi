package org.lschs.utilites.vision;

//TODO import stuffs
public class GetBoulderCoord {
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

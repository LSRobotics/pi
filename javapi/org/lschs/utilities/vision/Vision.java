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

public class Vision {
	
	static double[] BGRUpper = {255, 255, 255}; //In BGR Order
	static double[] BGRLower = {156, 137, 138}; //In BGR Order
	
	public Vision() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

}


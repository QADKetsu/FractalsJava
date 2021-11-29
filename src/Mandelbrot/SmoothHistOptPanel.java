package Mandelbrot;

import MathHelper.LinearMapping;
import java.awt.*;

import Colours.Generated;
import Colours.Hardcoded;


public class SmoothHistOptPanel extends MandelbrotPanel {
    private Color[] map;
    
    public SmoothHistOptPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
    }
    
    @Override
    public void draw() {
        MandelbrotCalc smoothHistOptCalc;
        if (calc == null) {
            smoothHistOptCalc = new SmoothHistOptCalc(width, height);
            super.calc = smoothHistOptCalc;
            System.out.println("Smoothed Histogram Optimized Mandelbrot created");
            map = Generated.firstMap();
        } else {
            smoothHistOptCalc = super.calc;
        }

        if (xMin == 0 && xMax == 0 && yMin == 0 && yMax == 0) { // if the bounds haven't been set yet
            xMin = smoothHistOptCalc.getXMin();
            xMax = smoothHistOptCalc.getXMax();
            yMin = smoothHistOptCalc.getYMin();
            yMax = smoothHistOptCalc.getYMax();
        } else { // else, ensure that the calculator uses the latest bounds
            smoothHistOptCalc.setMinMax(xMin, xMax, yMin, yMax);
        }

        double [][] hue = smoothHistOptCalc.calculate();
        

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double hueVal = hue[x][y];
                int colourVal;
                // Color[] map = Hardcoded.firstMap();
                // switch (colourVersion % 5) {
                //     case 0:
                //         colourVal = (int) LinearMapping.map(hueVal, 0, 1, 0, 255);
                //         fractalImage.setRGB(x, y, new Color(colourVal, colourVal, colourVal).getRGB());
                //         break;
                //     case 1:
                //         if (hueVal == 0) {
                //             fractalImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                //         } else {
                //             colourVal = (int) LinearMapping.map(hueVal, 0, 1, 15, 0);
                //             fractalImage.setRGB(x, y, map[colourVal%16].getRGB());
                //         }
                //         break;
                //     case 2:
                //         if (hueVal == 0) {
                //             fractalImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                //         } else {
                //             colourVal = (int) LinearMapping.map(hueVal, 0, 1, 19, 4);
                //             fractalImage.setRGB(x, y, map[colourVal%16].getRGB());
                //         }
                //         break;
                //     case 3:
                //         if (hueVal == 0) {
                //             fractalImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                //         } else {
                //             colourVal = (int) LinearMapping.map(hueVal, 0, 1, 23, 8);
                //             fractalImage.setRGB(x, y, map[colourVal%16].getRGB());
                //         }
                //         break;
                //     case 4:
                //         if (hueVal == 0) {
                //             fractalImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                //         } else {
                //             colourVal = (int) LinearMapping.map(hueVal, 0, 1, 27, 12);
                //             fractalImage.setRGB(x, y, map[colourVal%16].getRGB());
                //         }
                //         break;

                // }
                colourVal = (int) LinearMapping.map(hueVal, 0, 1, 255, 0);
                fractalImage.setRGB(x, y, map[colourVal].getRGB());
            }
        }
    }
}

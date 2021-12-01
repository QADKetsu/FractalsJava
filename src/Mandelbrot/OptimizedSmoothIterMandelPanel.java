package Mandelbrot;

import java.util.HashMap;

import Colours.Generated;
import MathHelper.LinearMapping;
import java.awt.*;

public class OptimizedSmoothIterMandelPanel extends MandelbrotPanel{
    
    // private Color[] colorMap;
    // private HashMap<Integer, Color> colorMap;
    HashMap<Integer, Color> colorMap;
    
    public OptimizedSmoothIterMandelPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void draw() {
        MandelbrotCalc smoothOptCalc;
        if (calc == null) {
            smoothOptCalc = new OptimizedSmoothIterMandelCalc(width, height);
            super.calc = smoothOptCalc;
            System.out.println("SHO Mandelbrot (Generated Colour Scheme) created");
        } else {
            smoothOptCalc = super.calc;
        }

        if (xMin == 0 && xMax == 0 && yMin == 0 && yMax == 0) { // if the bounds haven't been set yet
            xMin = smoothOptCalc.getXMin();
            xMax = smoothOptCalc.getXMax();
            yMin = smoothOptCalc.getYMin();
            yMax = smoothOptCalc.getYMax();
        } else { // else, ensure that the calculator uses the latest bounds
            smoothOptCalc.setMinMax(xMin, xMax, yMin, yMax);
        }

        double [][] hue = smoothOptCalc.calculate();
        // colorMap = Generated.firstMap();
        // colourOptOne(hue, colorMap);
        colorMap = Generated.seventhMap();
        colourOptTwo(hue, colorMap);
    }

    // colour options
    private void colourOptOne(double[][] hue, HashMap<Integer, Color> colorMap) {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double hueVal = hue[i][j];
                if (hueVal != 0) {
                    int colorI = (int) LinearMapping.map(hueVal, 0, 1, 0, 256);
                    Color color = colorMap.get(colorI);
                    fractalImage.setRGB(i, j, color.getRGB());
                } else {
                    fractalImage.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
    }

    private void colourOptTwo(double[][] hue, HashMap<Integer, Color> colorMap) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double hueVal = hue[i][j];
                if (hueVal != 0) {
                    int colorI = (int) LinearMapping.map(hueVal, 0, 1, 0, 50) % colorMap.size();
                    Color color = colorMap.get(colorI);
                    fractalImage.setRGB(i, j, color.getRGB());
                } else {
                    fractalImage.setRGB(i, j, Color.getHSBColor((float) 0.0, (float) 0.1,(float) 0.1).getRGB());
                }
            }
        }
    }
}
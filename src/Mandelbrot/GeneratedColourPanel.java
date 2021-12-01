package Mandelbrot;

import java.awt.Color;
import java.util.HashMap;

import Colours.*;
import MathHelper.LinearMapping;

// uses generated colours
// calc uses Smooth Hist Opt Calc
public class GeneratedColourPanel extends MandelbrotPanel{
    // private Color[] colorMap;
    // private HashMap<Integer, Color> colorMap;
    HashMap<Integer, Color> colorMap;

    public GeneratedColourPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
        // todo get user input for colour scheme
    }

    @Override
    public void draw() {
        MandelbrotCalc smoothHistOptCalc;
        if (calc == null) {
            smoothHistOptCalc = new SmoothHistOptCalc(width, height);
            super.calc = smoothHistOptCalc;
            // colorMap = Generated.fourthMap(); 
            // colorMap = Hardcoded.firstMap();
            System.out.println("SHO Mandelbrot (Generated Colour Scheme) created");
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
                    int colorI = (int) LinearMapping.map(hueVal, 0, 1, 0, 704) % colorMap.size();
                    Color color = colorMap.get(colorI);
                    fractalImage.setRGB(i, j, color.getRGB());
                } else {
                    fractalImage.setRGB(i, j, Color.getHSBColor((float) 0.0, (float) 0.1,(float) 0.1).getRGB());
                }
            }
        }
    }
}

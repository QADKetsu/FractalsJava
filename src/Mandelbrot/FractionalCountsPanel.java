package Mandelbrot;

import java.util.HashMap;
import java.awt.*;

import Colours.Generated;
import MathHelper.LinearMapping;

public class FractionalCountsPanel extends MandelbrotPanel{
    private HashMap<Integer, Color> colorMap;
    private FractionalCountsCalc fractCalc;

    public FractionalCountsPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
    }

    @Override
    public void draw() {
        if (calc == null) {
            fractCalc = new FractionalCountsCalc(width, height);
            super.calc = fractCalc;
            System.out.println("SHO Mandelbrot (Generated Colour Scheme) created");
        } else {
            // fractCalc = super.calc;
        }

        if (xMin == 0 && xMax == 0 && yMin == 0 && yMax == 0) { // if the bounds haven't been set yet
            xMin = fractCalc.getXMin();
            xMax = fractCalc.getXMax();
            yMin = fractCalc.getYMin();
            yMax = fractCalc.getYMax();
        } else { // else, ensure that the calculator uses the latest bounds
            fractCalc.setMinMax(xMin, xMax, yMin, yMax);
        }

        double [][] hue = fractCalc.calculate();
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
                    int colorI = (int) LinearMapping.map(hueVal, 0, 1, 0, 255) % colorMap.size();
                    Color color = colorMap.get(colorI);
                    fractalImage.setRGB(i, j, color.getRGB());
                } else {
                    fractalImage.setRGB(i, j, Color.getHSBColor((float) 0.0, (float) 0.1,(float) 0.1).getRGB());
                }
            }
        }
    }
    
}

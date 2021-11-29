package Mandelbrot;

import java.awt.Color;
import java.util.HashMap;

import Colours.*;
import MathHelper.LinearMapping;

// uses generated colours
// calc uses Smooth Hist Opt Calc
public class GeneratedColourPanel extends MandelbrotPanel{
    // private Color[] colorMap;
    private HashMap<Integer, Color> colorMap;

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
            colorMap = Generated.firstMap();
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double hueVal = hue[i][j];
                // if (hueVal != 0) { // ? 4th map
                //     int colorI = (int) LinearMapping.map(hueVal, 0, 1, 0, 256) % 256;
                //     Color color = colorMap[colorI] ;
                //     fractalImage.setRGB(i, j, color.getRGB());
                //     // int colourVal = (int) LinearMapping.map(hueVal, 0, 1, 15, 0);
                //     // Color color = colorMap[colourVal];
                //     fractalImage.setRGB(i, j, color.getRGB());
                // } else {
                //     fractalImage.setRGB(i, j, Color.BLACK.getRGB());
                // }

                if (hueVal != 0) { // ? 1st map
                    int colorI = (int) LinearMapping.map(hueVal, 0, 1, 256, 0);
                    Color color = colorMap.get(colorI);
                    fractalImage.setRGB(i, j, color.getRGB());
                } else {
                    fractalImage.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
    }
    
}

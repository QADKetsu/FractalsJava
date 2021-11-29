package Mandelbrot;

import MathHelper.LinearMapping;
import java.awt.*;


public class SmoothHistOptPanel extends MandelbrotPanel {
    
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
                int colourVal = (int) LinearMapping.map(hueVal, 0, 1, 0, 255);
                switch (colourVersion % 4) {
                    case 0:
                        fractalImage.setRGB(x, y, new Color(colourVal, colourVal, colourVal).getRGB());
                        break;
                    case 1:
                        fractalImage.setRGB(x, y, new Color(colourVal, colourVal/120, colourVal/120).getRGB());
                        break;
                    case 2:
                        fractalImage.setRGB(x, y, new Color(colourVal/120, colourVal, colourVal/120).getRGB());
                        break;
                    case 3:
                        fractalImage.setRGB(x, y, new Color(colourVal/120, colourVal/120, colourVal).getRGB());
                        break;
    
                }
                
            }
        }
    }
}

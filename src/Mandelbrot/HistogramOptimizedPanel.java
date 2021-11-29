package Mandelbrot;

import MathHelper.LinearMapping;
import java.awt.*;

public class HistogramOptimizedPanel extends MandelbrotPanel{
    
    public HistogramOptimizedPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
    }

    @Override
    public void draw() {
        MandelbrotCalc histOptiCalc;
        if (calc == null) {
            histOptiCalc = new HistogramOptimizedCalc(width, height);
            super.calc = histOptiCalc;
            System.out.println("Histogram Optimized Mandelbrot created");
        } else {
            histOptiCalc = super.calc;
        }

        if (xMin == 0 && xMax == 0 && yMin == 0 && yMax == 0) { // if the bounds haven't been set yet
            xMin = histOptiCalc.getXMin();
            xMax = histOptiCalc.getXMax();
            yMin = histOptiCalc.getYMin();
            yMax = histOptiCalc.getYMax();
        } else { // else, ensure that the calculator uses the latest bounds
            histOptiCalc.setMinMax(xMin, xMax, yMin, yMax);
        }

        double[][] hue = histOptiCalc.calculate(); // has been normalized. ranges from 0 to 1

        // for each pixel, set the color to the corresponding hue
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double hueValue = hue[x][y];
                // map from 0 to 1 to 0 to 255
                int colourValue = (int) LinearMapping.map(hueValue, 0, 1, 255, 0);
                switch (colourVersion % 4) {
                    case 0:
                        Color colour = new Color(colourValue, colourValue, colourValue);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                    case 1:
                        colour = new Color(colourValue, 0, 0);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                    case 2:
                        colour = new Color(0, colourValue, 0);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                    case 3:
                        colour = new Color(0, 0, colourValue);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                }
            }
        }
    }
}

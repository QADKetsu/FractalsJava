package Mandelbrot;
import MathHelper.LinearMapping;
import java.awt.*;

/**
 *  Uses the optimal escape time algorithm to calculate the Mandelbrot set.
 *  Still uses default colours
 */
public class OptimizedEscapePanel extends MandelbrotPanel {

    public OptimizedEscapePanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);

    }

    @Override
    public void draw() {
        MandelbrotCalc optiCalc;
        if (calc == null) {
            optiCalc = new OptimizedEscapeCalc(width, height);
            super.calc = optiCalc;
            System.out.println("Optimized Escape Mandelbrot created");
        } else {
            optiCalc = super.calc;
        }

        if (xMin == 0 && xMax == 0 && yMin == 0 && yMax == 0) { // if the bounds haven't been set yet
            xMin = optiCalc.getXMin();
            xMax = optiCalc.getXMax();
            yMin = optiCalc.getYMin();
            yMax = optiCalc.getYMax();
        } else { // else, ensure that the calculator uses the latest bounds
            optiCalc.setMinMax(xMin, xMax, yMin, yMax);
        }

        double[][] numIterationsPerPixel = optiCalc.calculate();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int iterationCount = (int) numIterationsPerPixel[x][y];
                int colourValue = (int) LinearMapping.map(iterationCount, 0, optiCalc.getMaxIterations(), 255, 0);
                
                // decide if it is default, red, green, or blue
                switch (colourVersion % 4) {
                    case 0:
                        Color colour = new Color(colourValue, colourValue, colourValue);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                    case 1:
                        colour = new Color(colourValue, 0, 0);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                    case 2:
                        colour = new Color(0, colourValue, 0);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                    case 3:
                        colour = new Color(0, 0, colourValue);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                }
            }
        }
        repaint();
    }
    
}

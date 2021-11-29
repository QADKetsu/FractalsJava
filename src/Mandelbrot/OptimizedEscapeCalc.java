package Mandelbrot;

import MathHelper.LinearMapping;

public class OptimizedEscapeCalc extends MandelbrotCalc {

    public OptimizedEscapeCalc(int w, int h) {
        super(w, h);
    }

    @Override
    public double[][] calculate() {
        double[][] toReturn = new double[w][h];
        // iterate over all pixels
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double x0 = LinearMapping.map(i, 0, w, xMin, xMax);
                double y0 = LinearMapping.map(j, 0, h, yMin, yMax);
                double x = 0;
                double y = 0;
                int iteration = 0;
                while (x * x + y * y < 4 && iteration < maxIterations) {
                    double xtemp = x * x - y * y + x0;
                    y = 2 * x * y + y0;
                    x = xtemp;
                    iteration++;
                }
                toReturn[i][j] = iteration;
            }
        }
        return toReturn;
    }

}

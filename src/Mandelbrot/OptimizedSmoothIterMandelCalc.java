package Mandelbrot;

import java.util.HashMap;

import MathHelper.LinearMapping;

public class OptimizedSmoothIterMandelCalc extends MandelbrotCalc {

    public OptimizedSmoothIterMandelCalc(int w, int h) {
        super(w, h);
    }
    
    @Override
    public double[][] calculate() {
        double[][] smoothedIterationsPerPixel = new double[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double x0 = LinearMapping.map(x, 0, w, xMin, xMax);
                double y0 = LinearMapping.map(y, 0, h, yMin, yMax);
                double X = 0;
                double Y = 0;
                double smoothedIteration = 0;
                while (X * X + Y * Y <= (1 << 16) && smoothedIteration < maxIterations) {
                    double xtemp = X * X - Y * Y + x0;
                    Y = 2 * X * Y + y0;
                    X = xtemp;
                    smoothedIteration++; 
                } // optimized mandelbrot calculation

                
                if (smoothedIteration == maxIterations) {
                    smoothedIteration = 0;
                } else {
                    double log2 = Math.log(2);
                    double log2Abs = Math.log(X * X + Y * Y) / log2;
                    double log2log2abs = Math.log(log2Abs) / log2;
                    smoothedIteration = smoothedIteration - log2log2abs + 4;
                }

                smoothedIterationsPerPixel[x][y] = smoothedIteration;
            }
        }
        return smoothedIterationsPerPixel;
    }
    
}

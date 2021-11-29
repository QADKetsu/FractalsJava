package Mandelbrot;

import java.util.*;

import MathHelper.LinearInterpolation;
import MathHelper.LinearMapping;

// first perform logarithms on the iterations to smooth them out
// then perform the histogram normalization
public class SmoothHistOptCalc extends MandelbrotCalc {

    public SmoothHistOptCalc(int w, int h) {
        super(w, h);
    }
    
    @Override
    public double[][] calculate() {
        double[][] smoothedIterationsPerPixel = new double[w][h];
        HashMap<Integer, Integer> histogram = new HashMap<Integer, Integer>(); // key is smoothed iteration, value is number of pixels with that iteration

        // first pass
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

                // smoothing component of the algorithm
                if (smoothedIteration < maxIterations) {
                    double logOfZn = Math.log(X * X + Y * Y)/2;
                    double nu = Math.log(logOfZn/ Math.log(2))/ Math.log(2);
                    smoothedIteration = smoothedIteration + 1 - nu;
                    // check if the smoothed iteration is already in the histogram
                    int flooredIteration = (int) Math.floor(smoothedIteration);
                    if(!histogram.keySet().contains(flooredIteration)) {
                        histogram.put(flooredIteration, 1);
                    } else {
                        histogram.put(flooredIteration, histogram.get(flooredIteration) + 1);
                    }
                }
                smoothedIterationsPerPixel[x][y] = smoothedIteration; // range from 0 to maxIterations
            }
        }

        // find total value of the histogram using stream
        int total = (int)histogram.values().stream().mapToInt(Integer::intValue).sum();

        // final pass
        double[][] toReturn = new double[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double smoothedIteration = smoothedIterationsPerPixel[i][j];
                if (smoothedIteration < maxIterations) {
                    int flooredIteration = (int) Math.floor(smoothedIteration);
                    for (int k = 0; k < flooredIteration; k++) {
                        if (histogram.keySet().contains(k)) {
                            toReturn[i][j] += (double)histogram.get(k)/(double)total;
                        }
                    }
                } else {
                    toReturn[i][j] = 0;
                }
            }
        }

        return toReturn;
    }
    
}

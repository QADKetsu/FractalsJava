package Mandelbrot;

import java.util.*; 

import MathHelper.LinearMapping;

public class FractionalCountsCalc extends MandelbrotCalc {
    public double min,max;
    private ArrayList<Double> counts;
    public FractionalCountsCalc(int w, int h) {
        super(w, h);
        //TODO Auto-generated constructor stub
    }

    @Override
    public double[][] calculate() {
        double[][] smoothedIterationsPerPixel = new double[w][h];
        counts = new ArrayList<Double>();
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

                
                // N prime = F * (N + W * dx)
                // F = 1000, W = 1.0
                // dx = loglog(esc) - loglog(absz) / log(2)
                double F = 1000;
                double W = 1.0;
                double logEsc = Math.log(2);
                double logLogEsc = Math.log(Math.log(2));
                double logAbsZ = 0.5 * Math.log(X * X + Y * Y);
                double loglogAbsZ = Math.log(logAbsZ);
                double dx = logLogEsc - (loglogAbsZ / logEsc);
                double Nprime = F * (smoothedIteration + W * dx);

                smoothedIterationsPerPixel[x][y] = Nprime;
                counts.add(Nprime);
            }
        }

        Collections.sort(counts);
        // for each Nprime, find its percentile
        
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double Nprime = smoothedIterationsPerPixel[i][j];
                // find index of Nprime in counts
                int index = Collections.binarySearch(counts, Nprime);
                double percentile = (double)index / (double)counts.size();
                smoothedIterationsPerPixel[i][j] = percentile;
            }
        }
        


        // double logMin = Math.log(min);
        // double logMax = Math.log(max);
        // // log map
        // double[][] logMappedIterationsPerPixel = new double[w][h];
        // for (int x = 0; x < w; x++) {
        //     for (int y = 0; y < h; y++) {
        //         double n = smoothedIterationsPerPixel[x][y];
        //         double logN = Math.log(n);
        //         double logNprime = (logN - logMin) / (logMax - logMin);
        //         logMappedIterationsPerPixel[x][y] = logNprime;
        //     }
        // }
        
        // // find min max
        // min = Double.MAX_VALUE;
        // max = Double.MIN_VALUE;
        // for (int x = 0; x < w; x++) {
        //     for (int y = 0; y < h; y++) {
        //         if (logMappedIterationsPerPixel[x][y] < min) {
        //             min = logMappedIterationsPerPixel[x][y];
        //         }
        //         if (logMappedIterationsPerPixel[x][y] > max) {
        //             max = logMappedIterationsPerPixel[x][y];
        //         }
        //     }
        // }
        // System.out.println();
        
        // return logMappedIterationsPerPixel;

        return smoothedIterationsPerPixel;
    }
    
}

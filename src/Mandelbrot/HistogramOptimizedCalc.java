package Mandelbrot;

public class HistogramOptimizedCalc extends OptimizedEscapeCalc{
    // this uses the optimized escape time algorithm, but for colouring, it divides by MAX_ITERATIONS to normalize the values
    // and then uses a linear mapping to map the values to the range 0 to 255

    public HistogramOptimizedCalc(int w, int h) {
        super(w, h);
    }

    @Override
    public double[][] calculate() {
        // first pass
        double[][] numIterationsPerPixel = super.calculate(); // get the escape time values first

        // second pass
        int[] occurancesPerPixel = new int[super.getMaxIterations()]; // create an array to store the occurances of each value 
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                occurancesPerPixel[(int)numIterationsPerPixel[x][y]-1]++; // increment the occurance of the value
            }
        }

        // third pass
        int total = 0;
        for (int i = 0; i < occurancesPerPixel.length; i++) {
            total += occurancesPerPixel[i];
        }

        // fourth pass
        double[][] toReturn = new double[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int iteration = (int)numIterationsPerPixel[i][j];
                for (int k = 0; k < iteration; k++) {
                    toReturn[i][j] += (double)occurancesPerPixel[k]/(double)total;
                }
            }
        }

        return toReturn;
    }

}

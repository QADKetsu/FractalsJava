package Julia;

import java.util.*;

import MathHelper.Complex;
import MathHelper.LinearMapping;

public class JuliaCalc {
    // f(z) = z^2 + c
    private int width = 1000;
    private int height = 1000;
    private Complex c = new Complex(-0.4337, -1); // arbitrary
    private int maxIterations = 1000;
    double escapeRadius;
    private double minX, maxX, minY, maxY;
    protected Stack<Double[]> undoStack;
    protected Stack<Double[]> redoStack;
    private ArrayList<Double> counts;

    public JuliaCalc(int width, int height) {
        this.width = width;
        this.height = height;
        escapeRadius = 2;
        minX = -escapeRadius;
        maxX = escapeRadius;
        minY = -escapeRadius;
        maxY = escapeRadius;
        undoStack = new Stack<Double[]>();
        redoStack = new Stack<Double[]>();
        addUndo(minX, maxX, minY, maxY);

        while (Math.pow(escapeRadius, 2) - escapeRadius < c.magnitude()) {
            escapeRadius += 1;
        }
        escapeRadius = 1000;
    }

    public double[][] calculate() {
        double[][] result = new double[width][height];
        counts = new ArrayList<Double>();

        // for each pixel (x,y) on the screen
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double scaledX = LinearMapping.map(x, 0, width, minX, maxX);
                double scaledY = LinearMapping.map(y, 0, height, minY, maxY);

                int iteration = 0;
                while (scaledX * scaledX + scaledY * scaledY < escapeRadius * escapeRadius && iteration < maxIterations) {
                    double tempX = scaledX * scaledX - scaledY * scaledY + c.getReal();
                    scaledY = 2 * scaledX * scaledY + c.getImaginary();
                    scaledX = tempX;
                    iteration++;
                }
                // N prime = F * (N + W *dx)
                // dx = loglog(ESC) - loglog(zABS)/log(2)
                double N = iteration;
                double F = 1000;
                double W = 1.0;
                double logEsc = Math.log(1000);
                double logLogEsc = Math.log(logEsc);
                double logZabs = 0.5 * Math.log((scaledX * scaledX + scaledY * scaledY));
                double logLogZabs = Math.log(logZabs);
                double dx = logLogEsc - ( logLogZabs / Math.log(2)) ;
                double Nprime = F * (N + W * dx);
                result[x][y] = Nprime;
                counts.add(Nprime);
            }

        }

        Collections.sort(counts);
        // for each Nprime, find its percentile
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double Nprime = result[i][j];
                if (Nprime != 0) {
                    // find index of Nprime in counts
                    int index = Collections.binarySearch(counts, Nprime);
                    double percentile = (double)index / (double)counts.size();
                    result[i][j] = percentile;
                }
            }
        }

        // find min max of result
        double min = result[0][0];
        double max = result[0][0];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (result[i][j] < min) {
                    min = result[i][j];
                }
                if (result[i][j] > max) {
                    max = result[i][j];
                }
            }
        }

        return result;
    }

    // * getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Complex getC() {
        return c;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getEscapeRadius() {
        return escapeRadius;
    }

    public double[] getMinMax() {
        return new double[]{minX, maxX, minY, maxY};
    }

    public Stack<Double[]> getUndoStack() {
        return undoStack;
    }

    public Stack<Double[]> getRedoStack() {
        return redoStack;
    }

    // * setters
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setC(Complex c) {
        this.c = c;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setEscapeRadius(double escapeRadius) {
        this.escapeRadius = escapeRadius;
    }

    public void setMinMax(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void addUndo(double xMin, double xMax, double yMin, double yMax) {
        Double[] toAdd = new Double[4];
        toAdd[0] = xMin;
        toAdd[1] = xMax;
        toAdd[2] = yMin;
        toAdd[3] = yMax;
        undoStack.push(toAdd);
    }

    public void addRedo(double xMin, double xMax, double yMin, double yMax) {
        Double[] toAdd = new Double[4];
        toAdd[0] = xMin;
        toAdd[1] = xMax;
        toAdd[2] = yMin;
        toAdd[3] = yMax;
        redoStack.push(toAdd);
    }

    public double[] getInitialBounds() {
        return new double[]{-2, 2, -2, 2, 1000};
    }

}

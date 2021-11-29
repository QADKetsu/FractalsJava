package Mandelbrot;

import MathHelper.Complex;
import MathHelper.LinearMapping;
import java.util.*;

/**
 * Default formula for Mandelbrot set.
 * Uses custom complex numbers
 * Not optimized, and not normalized
 * @params w - width of the image
 * @params h - height of the image
 * @returns array of colors
 */

public class MandelbrotCalc {
    private int w;
    private int h;
    private int maxIterations = 25;
    private double xMin = -2;
    private double xMax = 2;
    private double yMin = -2;
    private double yMax = 2;
    private double[] initialBounds;
    private Stack<Double[]> undoStack;
    private Stack<Double[]> redoStack;
    
    // constructor
    public MandelbrotCalc(int w, int h) {
        this.w = w;
        this.h = h;
        undoStack = new Stack<Double[]>();
        redoStack = new Stack<Double[]>();
        addUndo(xMin, xMax, yMin, yMax);
        initialBounds = new double[] {xMin, xMax, yMin, yMax, maxIterations};
    }

    // overloaders
    public MandelbrotCalc(int w, int h, int maxIterations) {
        this.w = w;
        this.h = h;
        this.maxIterations = maxIterations;
        undoStack = new Stack<Double[]>();
        redoStack = new Stack<Double[]>();
        addUndo(xMin, xMax, yMin, yMax);
        initialBounds = new double[] {xMin, xMax, yMin, yMax, maxIterations};
    }

    public MandelbrotCalc(int w, int h, int maxIterations, double xMin, double xMax, double yMin, double yMax) {
        this.w = w;
        this.h = h;
        this.maxIterations = maxIterations;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        undoStack = new Stack<Double[]>();
        redoStack = new Stack<Double[]>();
        addUndo(xMin, xMax, yMin, yMax);
        initialBounds = new double[] {xMin, xMax, yMin, yMax, maxIterations};
    }

    //****************************** METHODS ******************************//
    
    // the function c
    public Complex fC(Complex z, Complex c) { // returns fC(z) = previous z squared + c
        Complex toReturn = z.power(2).add(c);
        return toReturn;
    }

    // the iteration
    public double[][] calculate() { // calculates for each pixel, how many iterations it takes to escape
        double[][] toReturn = new double[h][w];
        // iterate over all pixels
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                // map from pixel domain to complex domain
                double x = LinearMapping.map(j, 0, w, xMin, xMax);
                double y = LinearMapping.map(i, 0, h, yMin, yMax);

                // create complex number
                Complex c = new Complex(x, y);
                Complex z = new Complex(0, 0);

                // iterate
                int iteration = 0;
                while (z.magnitude() < 2 && iteration < maxIterations) {
                    z = fC(z, c);
                    iteration++;
                }

                // store number of iterations
                toReturn[j][i] = iteration;
                // System.out.println();
            }
        }
        return toReturn;
    }

    public double mapX(double x) {
        return LinearMapping.map(x, 0, w, xMin, xMax);
    }

    public double mapY(double y) {
        return LinearMapping.map(y, 0, h, yMin, yMax);
    }

    // getters
    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public Stack<Double[]> getUndoStack() {
        return undoStack;
    }

    public Stack<Double[]> getRedoStack() {
        return redoStack;
    }

    public double[] getInitialBounds() {
        return initialBounds;
    }

    // setters
    public void setMinMax(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
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
    }

    public void setMaxIterations(int parseInt) {
        maxIterations = parseInt;
    }

}

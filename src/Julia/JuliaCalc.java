package Julia;

import java.util.*;

import MathHelper.Complex;
import MathHelper.LinearMapping;

public class JuliaCalc {
    // f(z) = z^2 + c
    private int width = 1000;
    private int height = 1000;
    private Complex c = new Complex(-0.4337, -0.5823); // arbitrary
    private int maxIterations = 1000;
    double escapeRadius;
    private double minX, maxX, minY, maxY;
    protected Stack<Double[]> undoStack;
    protected Stack<Double[]> redoStack;

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
    }

    public double[][] calculate() {
        double[][] result = new double[width][height];
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
                if (iteration == maxIterations) {
                    result[x][y] = 0;
                } else {
                    result[x][y] = iteration;
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

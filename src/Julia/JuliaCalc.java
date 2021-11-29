package Julia;

import MathHelper.Complex;
import MathHelper.LinearMapping;

public class JuliaCalc {
    // f(z) = z^2 + c
    private int width = 1000;
    private int height = 1000;
    private Complex c = new Complex(-0.4337, -0.5823); // arbitrary
    private int maxIterations = 1000;
    double escapeRadius;

    public JuliaCalc(int width, int height) {
        this.width = width;
        this.height = height;
        escapeRadius = Math.ceil(c.magnitude());

        while (Math.pow(escapeRadius, 2) - escapeRadius < c.magnitude()) {
            escapeRadius += 1;
        }
    }

    public double[][] calculate() {
        double[][] result = new double[width][height];
        // for each pixel (x,y) on the screen
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double scaledX = LinearMapping.map(x, 0, width, -escapeRadius, escapeRadius);
                double scaledY = LinearMapping.map(y, 0, height, -escapeRadius, escapeRadius);

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

    

}

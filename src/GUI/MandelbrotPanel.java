package GUI;

import Mandelbrot.MandelbrotCalc;
import MathHelper.LinearMapping;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MandelbrotPanel extends FractalPanel {
    private int colourVersion = 0; // 0, 1, 2, 3. 0 is default, 1 is red, 2 is green, 3 is blue
    private double xMin, xMax, yMin, yMax;
    private MandelbrotCalc calc;

    public MandelbrotPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
        drawSimpleMandel();
        repaint();
    }

    public void setMinMax(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    //* simple mandelbrot set
    public void drawSimpleMandel() {
        // uses the basic mandelbrot algorithm, unoptimized, unsmoothed, and not histogram-based
        // colouring uses mapping. value maps from 0 to MAX_ITERATIONS to 255 to 0
        
        MandelbrotCalc simpleMandel;
        if (calc == null) {
            simpleMandel = new MandelbrotCalc(width, height);
            this.calc = simpleMandel;
        } else {
            simpleMandel = calc;
        }

        if (xMin == 0 && xMax == 0 && yMin == 0 && yMax == 0) { // if the bounds haven't been set yet
            xMin = simpleMandel.getXMin();
            xMax = simpleMandel.getXMax();
            yMin = simpleMandel.getYMin();
            yMax = simpleMandel.getYMax();
        } else { // else, ensure that the calculator uses the latest bounds
            simpleMandel.setMinMax(xMin, xMax, yMin, yMax);
        }

        double[][] numIterationsPerPixel = simpleMandel.calculate();

        // for each pixel, map its iteration count to a number from 255 to 0 // ? max iterations should be black.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int iterationCount = (int) numIterationsPerPixel[x][y];
                int colourValue = (int) LinearMapping.map(iterationCount, 0, simpleMandel.getMaxIterations(), 255, 0);
                
                // decide if it is default, red, green, or blue
                switch (colourVersion % 4) {
                    case 0:
                        Color colour = new Color(colourValue, colourValue, colourValue);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                    case 1:
                        colour = new Color(colourValue, 0, 0);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                    case 2:
                        colour = new Color(0, colourValue, 0);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                    case 3:
                        colour = new Color(0, 0, colourValue);
                        fractalImage.setRGB(x, y, colour.getRGB());
                        break;
                }
            }
        }
        repaint();
    }




    // * JPanel methods
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fractalImage, 0, 0, null);
    }

    @Override
    protected void zoom(MouseEvent e) {
        // check is left mouse button is pressed
        if (clickedButton == MouseEvent.BUTTON1) {
            Point currentPoint = e.getPoint();

            // convert from screen coordinates into image coordinates
            double prevX = clickedPoint.getX();
            double prevY = clickedPoint.getY();

            double currX = currentPoint.getX();

            // convert from image coordinates to complex number coordinates
            double startX = LinearMapping.map(prevX, 0, width, xMin, xMax); 
            double startY = LinearMapping.map(prevY, 0, height, yMin, yMax);


            // same for end point
            double endX = LinearMapping.map(currX, 0, width, xMin, xMax);

            // flip if selection was made in the wrong direction
            if (endX < startX) {
                double temp = startX;
                startX = endX;
                endX = temp;
            }

            // find distance from start x to end x to make it a square
            double distanceX = Math.abs(endX - startX);
            // set end y to be start y + distance
            double endY = startY + distanceX;

            // swap if end y is less than start y
            if (endY < startY) {
                double temp = startY;
                startY = endY;
                endY = temp;
            }

            // set the new min and max values
            xMin = startX;
            xMax = endX;
            yMin = startY;
            yMax = endY;
            calc.setMinMax(xMin, xMax, yMin, yMax);
            drawSimpleMandel();
            repaint();
        }
    }

    @Override
    protected void pan(MouseEvent e) {
        // check if right mouse button is pressed
        if (clickedButton == MouseEvent.BUTTON3) {
            // ensure undo stack is updated
            calc.addUndo(xMin, xMax, yMin, yMax); // ? add the current bounds to the undo stack
            Point currentPoint = e.getPoint();
            
            double currentX = LinearMapping.map(currentPoint.getX(), 0, width, xMin, xMax);
            double currentY = LinearMapping.map(currentPoint.getY(), 0, height, yMin, yMax);

            double previousX = LinearMapping.map(clickedPoint.getX(), 0, width, xMin, xMax);
            double previousY = LinearMapping.map(clickedPoint.getY(), 0, height, yMin, yMax);

            double xDiff = currentX - previousX;
            xDiff /= 10;
            double yDiff = currentY - previousY;
            yDiff /= 10;

            xMin -= xDiff;
            xMax -= xDiff;
            yMin -= yDiff;
            yMax -= yDiff;

            calc.setMinMax(xMin, xMax, yMin, yMax);
            drawSimpleMandel();
            repaint();
        }
    }

    // **************** TOOLBAR ****************//

    @Override
    public void changeColour() {
        colourVersion++;
        drawSimpleMandel();
        repaint();
    }

    public void undo() {
        // if undo stack is empty, do nothing
        if (calc.getUndoStack().isEmpty()) {
            return;
        }
        // add to redo stack
        calc.addRedo(xMin, xMax, yMin, yMax);
        // pop the last bounds from the undo stack
        Double[] lastBounds = calc.getUndoStack().pop();
        xMin = lastBounds[0];
        xMax = lastBounds[1];
        yMin = lastBounds[2];
        yMax = lastBounds[3];

        calc.setMinMax(xMin, xMax, yMin, yMax);
        drawSimpleMandel();
        repaint();
    }

    public void redo() {
        // if redo stack is empty, do nothing
        if (calc.getRedoStack().isEmpty()) {
            return;
        }
        // add to undo stack
        calc.addUndo(xMin, xMax, yMin, yMax);
        // pop the last bounds from the redo stack
        Double[] lastBounds = calc.getRedoStack().pop();
        xMin = lastBounds[0];
        xMax = lastBounds[1];
        yMin = lastBounds[2];
        yMax = lastBounds[3];

        calc.setMinMax(xMin, xMax, yMin, yMax);
        drawSimpleMandel();
        repaint();
    }

    public void reset() {
        // add to undo stack
        calc.addUndo(xMin, xMax, yMin, yMax);
        // reset the min and max values
        double[] newBounds = calc.getInitialBounds();
        xMin = newBounds[0];
        xMax = newBounds[1];
        yMin = newBounds[2];
        yMax = newBounds[3];
        int maxIterations = (int) newBounds[4];

        calc.setMinMax(xMin, xMax, yMin, yMax);
        calc.setMaxIterations(maxIterations);
        drawSimpleMandel();
        repaint();
    }

    public void setMaxIterations(int parseInt) {
        calc.setMaxIterations(parseInt);
        drawSimpleMandel();
        repaint();
    } 
}

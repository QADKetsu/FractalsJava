package Julia;

import MathHelper.LinearMapping;
import java.awt.*;
import java.awt.event.*;

import GUI.FractalPanel;

public class JuliaPanel extends FractalPanel {
    private JuliaCalc calc;
    int colourVersion;
    private double minX, maxX, minY, maxY;

    public JuliaPanel(int imageWidth, int imageHeight) {
        super(imageWidth, imageHeight);
        draw();
    }
    
    public void draw() {
        JuliaCalc simpleJulia;
        if (calc == null) {
            simpleJulia = new JuliaCalc(width, height);
            calc = simpleJulia;
            System.out.println("Simple Julia Set created");
        } else {
            simpleJulia = calc;
        }

        double[] bounds = simpleJulia.getMinMax();
        minX = bounds[0];
        maxX = bounds[1];
        minY = bounds[2];
        maxY = bounds[3];

        double[][] numIterationsPerPixel = calc.calculate();
        // for each pixel, map iteration to range of 0 - 255
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int iterations = (int) numIterationsPerPixel[x][y];
                int colourValue = (int) LinearMapping.map(iterations, 0, calc.getMaxIterations(), 255, 0);
                switch (colourVersion % 4) {
                    case 0:
                        Color colour = new Color(colourValue, colourValue, colourValue);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                    case 1:
                        colour = new Color(colourValue, 0, 0);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                    case 2:
                        colour = new Color(0, colourValue, 0);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
                        break;
                    case 3:
                        colour = new Color(0, 0, colourValue);
                        fractalImage.setRGB(x, y, colour.darker().getRGB());
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
        if (clickedButton == MouseEvent.BUTTON1) {
            System.out.println("Zooming in");
            Point currentPoint = e.getPoint();
            // convert to fractal coordinates
            // for now it is mapped from -bound to bound
            double currX = LinearMapping.map(currentPoint.x, 0, width, minX, maxX);
            double prevX = LinearMapping.map(clickedPoint.x, 0, width, minX, maxX);

            // swap if necessary
            if (currX > prevX) {
                double temp = currX;
                currX = prevX;
                prevX = temp;
            }

            // find distance
            double distance = currX - prevX;
            
            // find prevY
            double prevY = LinearMapping.map(clickedPoint.y, 0, height, minY, maxY);
            // add distance to prevY
            double currY = prevY + distance;

            // set new bounds
            calc.setMinMax(prevX, currX, prevY, currY);
            double[] bounds = calc.getMinMax();
            minX = bounds[0];
            maxX = bounds[1];
            minY = bounds[2];
            maxY = bounds[3];

            draw();
            repaint();
        }
    }

    @Override
    protected void pan(MouseEvent e) {
        if (clickedButton == MouseEvent.BUTTON3) {
            Point currentPoint = e.getPoint();
            double currX = LinearMapping.map(currentPoint.x, 0, width, minX, maxX);
            double prevX = LinearMapping.map(clickedPoint.x, 0, width, minX, maxX);
            double currY = LinearMapping.map(currentPoint.y, 0, height, minY, maxY);
            double prevY = LinearMapping.map(clickedPoint.y, 0, height, minY, maxY);
            double distanceX = currX - prevX;
            distanceX /= 2;
            double distanceY = currY - prevY;
            distanceY /= 2;

            minX -= distanceX;
            maxX -= distanceX;
            minY -= distanceY;
            maxY -= distanceY;

            calc.setMinMax(minX, maxX, minY, maxY);
            draw();
            repaint();
        }
    }

    // **************** TOOLBAR ****************//

    @Override
    public void changeColour() {
        colourVersion++;
        draw();
        repaint();
    }

    public void undo() {
        // if undo stack is empty, do nothing
        if (calc.getUndoStack().isEmpty()) {
            return;
        }
        // add to redo stack
        calc.addRedo(minX, maxX, minY, maxY);
        // pop the last bounds from the undo stack
        Double[] lastBounds = calc.getUndoStack().pop();
        minX = lastBounds[0];
        maxX = lastBounds[1];
        minY = lastBounds[2];
        maxY = lastBounds[3];

        calc.setMinMax(minX, maxX, minY, maxY);
        draw();
        repaint();
    }

    public void redo() {
        // if redo stack is empty, do nothing
        if (calc.getRedoStack().isEmpty()) {
            return;
        }
        // add to undo stack
        calc.addUndo(minX, maxX, minY, maxY);
        // pop the last bounds from the redo stack
        Double[] lastBounds = calc.getRedoStack().pop();
        minX = lastBounds[0];
        maxX = lastBounds[1];
        minY = lastBounds[2];
        maxY = lastBounds[3];

        calc.setMinMax(minX, maxX, minY, maxY);
        draw();
        repaint();
    }

    public void reset() {
        // add to undo stack
        calc.addUndo(minX, maxX, minY, maxY);
        // reset the min and max values
        double[] newBounds = calc.getInitialBounds();
        minX = newBounds[0];
        maxX = newBounds[1];
        minY = newBounds[2];
        maxY = newBounds[3];
        int maxIterations = (int) newBounds[4];

        calc.setMinMax(minX, maxX, minY, maxY);
        calc.setMaxIterations(maxIterations);
        draw();
        repaint();
    }

    public void setMaxIterations(int parseInt) {
        calc.setMaxIterations(parseInt);
        draw();
        repaint();
    }

}

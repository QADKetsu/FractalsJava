package Julia;

import MathHelper.LinearMapping;
import java.awt.*;
import java.awt.event.*;

import GUI.FractalPanel;

public class JuliaPanel extends FractalPanel {
    private JuliaCalc calc;
    int colourVersion;

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

        // todo deal with bounds

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
            Point currentPoint = e.getPoint();
            // convert to fractal coordinates
            // todo figure out zoom
        }
    }
}

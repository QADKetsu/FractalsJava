package GUI;

import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FractalPanel extends JPanel {
    protected int width;
    protected int height;
    BufferedImage fractalImage;
    protected int clickedButton;
    protected Point clickedPoint;

    // constructor
    public FractalPanel(int imageWidth, int imageHeight) {
        // standard JPanel setup
        width = imageWidth;
        height = imageHeight;
        setSize(width, height);
        setVisible(true);

        // fractal setup
        fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // add mouse listeners
        this.addMouseListener(new ClickListener());
        this.addMouseListener(new ZoomListener());
        this.addMouseMotionListener(new PanListener());

    }

    // getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // setters
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // mouse listeners
    protected class ClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e){
            clickedPoint = e.getPoint();
            clickedButton = e.getButton();
        }
    }
    // zoom

    protected class ZoomListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e){
            zoom(e);
        }
    }

    protected void zoom(MouseEvent e) { // to be implemented by subclasses
    }

    // pan
    protected class PanListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e){
            pan(e);
        }
    }

    protected void pan(MouseEvent e) { // to be implemented by subclasses
    }

    // **************** TOOLBAR ****************//

    public void changeColour() {
    }

    public void undo() {
    }

    public void redo() {
    }

    public void reset() {
    }

    public void setMaxIterations(int parseInt) {
    }

}

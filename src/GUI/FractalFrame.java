package GUI;

import java.util.Scanner;

import javax.swing.*;

import Julia.*;
import Mandelbrot.*;

import java.awt.event.*;
import java.awt.*;

public class FractalFrame extends JFrame implements ActionListener {
    private int width;
    private int height;
    private FractalPanel panel;
    private Timer timer;
    
    
    // constructor
    public FractalFrame(int windowWidth, int windowHeight) {
        // standard JFrame setup
        width = windowWidth;
        height = windowHeight;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setResizable(false);
        setVisible(false);
        FractalBar toolbar = new FractalBar(this);
        getContentPane().add(toolbar, BorderLayout.NORTH);
        this.timer = new Timer(10, this);
        this.timer.setRepeats(false);
    }

    public void run() { // chooses what kind of fractal to draw
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a fractal to draw: ");
        System.out.println("1. Simple Mandelbrot");
        System.out.println("2. Optimized Escape Mandelbrot");
        System.out.println("3. Histogram Mandelbrot");
        System.out.println("4. Smoothed Histogram Mandelbrot");
        System.out.println("5. SHO Generated Mandelbrot");
        // int choice = Integer.parseInt(scanner.nextLine());
        int choice = 6;
        switch (choice) {
            case 1:
                panel = new MandelbrotPanel(width, height);
                break;
            case 2:
                panel = new OptimizedEscapePanel(width, height);
                break;
            case 3:
                panel = new HistogramOptimizedPanel(width, height);
                break;
            case 4:
                panel = new SmoothHistOptPanel(width, height);
                break;
            case 5:
                panel = new GeneratedColourPanel(width, height);
                break;
            case 6:
                panel = new JuliaPanel(width, height);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        add(panel);
        panel.repaint();
        timer.start();
        setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        revalidate();
        repaint();
    }

    // **************** TOOLBAR ****************//
    public void changeColour() {
        panel.changeColour();
        repaint();
    }

    public void undo() {
        panel.undo();
        repaint();
    }

    public void redo() {
        panel.redo();
        repaint();
    }

    public void reset() {
        panel.reset();
        repaint();
    }

    public void setMaxIterations(int parseInt) {
        panel.setMaxIterations(parseInt);
        repaint();
    }
}

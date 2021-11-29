package GUI;

import java.util.Scanner;

import javax.swing.*;
import java.awt.event.*;

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
        this.timer = new Timer(10, this);
        this.timer.setRepeats(false);
    }

    public void run() { // chooses what kind of fractal to draw
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a fractal to draw: ");
        System.out.println("1. Simple Mandelbrot");
        // int choice = Integer.parseInt(scanner.nextLine());
        int choice = 1;
        switch (choice) {
            case 1:
                panel = new MandelbrotPanel(width, height);
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
}

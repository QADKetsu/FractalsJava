package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class FractalBar extends JToolBar {
    private FractalFrame parentFrame;

    public FractalBar(FractalFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
        setFloatable(false);
        setRollover(true);

        // change colour
        JButton colourButton = new JButton("Change Colour");
        colourButton.addActionListener(parentFrame);
        colourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.changeColour();
            }
        });
        add(colourButton);

        // undo button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(parentFrame);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.undo();
            }
        });
        add(undoButton);

        // redo button
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(parentFrame);
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.redo();
            }
        });
        add(redoButton);
        
        // reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(parentFrame);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.reset();
            }
        });
        add(resetButton);

        // changing of iterations
        JTextField maxIterations = new JTextField("100");
        maxIterations.setPreferredSize(new Dimension(10,10));
        maxIterations.addActionListener(parentFrame);
        maxIterations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setMaxIterations(Integer.parseInt(maxIterations.getText()));
            }
        });
        this.add(maxIterations);
    }

}

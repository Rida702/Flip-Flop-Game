/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flipflop;

/**
 *
 * @author abu musa traders
 */

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class NewClass {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final int TOTAL_TILES = ROWS * COLS;

    private JFrame frame;
    private JButton[] buttons;
    private boolean[] flipStates;

    public NewClass() {
        frame = new JFrame("Flip Flop Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        buttons = new JButton[TOTAL_TILES];
        flipStates = new boolean[TOTAL_TILES];

        frame.setLayout(new GridLayout(ROWS, COLS, 5,5));

        for (int i = 0; i < TOTAL_TILES; i++) {
            buttons[i] = new JButton();
            //buttons[i].addActionListener(new TileClickListener(i));
            frame.add(buttons[i]);
        }
        frame.setVisible(true);
        initializeGame();
    }

    private void initializeGame() {
        // Set the initial state of the game, assign images to the buttons, etc.
        // You can customize this method to suit your game logic.
        for (int i = 0; i < TOTAL_TILES; i++) {
            flipStates[i] = false;
            ImageIcon imageIcon = new ImageIcon("back.png");
            buttons[i].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[i].getWidth(), buttons[i].getHeight())));
            buttons[i].addActionListener(new TileClickListener(i));
        }
    }

       private void flipTile(int index) {
        flipStates[index] = !flipStates[index];
        ImageIcon imageIcon;
        if ( flipStates[index] && (index == 6 || index == 9 || index == 13 || index == 15) ) {
            imageIcon = new ImageIcon("3.jpeg");
        } 
        else if (flipStates[index] && (index == 1 || index == 2 || index == 4 || index == 7 
                || index == 12 || index == 14 )) {
            imageIcon = new ImageIcon("2.jpg");
        } 
        else if (flipStates[index] && (index == 0 || index == 3 || index == 5 || index == 8 ||
                index == 10 || index == 11)) {
            imageIcon = new ImageIcon("1.png");
        }
        else {
            imageIcon = new ImageIcon("back.png");
        }
        buttons[index].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[index].getWidth(), buttons[index].getHeight())));
   }
    
    private Image getScaledImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private class TileClickListener implements ActionListener {
        private final int index;

        public TileClickListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {    
            flipTile(index);
        }
    }

    public static void main(String[] args) {
        // Ensure GUI updates are performed on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NewClass();
            }
        });
    }
}
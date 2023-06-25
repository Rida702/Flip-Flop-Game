/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flipflop;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import java.util.*;


//Add Pop Sound at each Click
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 *
 * @author abu musa traders
 */

public class FlipImage {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final int TOTAL_TILES = ROWS * COLS;
    private boolean[] lockedButtons;

    private JFrame frame;
    private JButton[] buttons;
    private boolean[] flipStates;
    int index1 = -1;
    int index2 = -1;
    int flag=0;
    private Clip clip;
    private JLabel scoreLabel;
    int score=0;
    int visited=0;
    
    int[] arr = {1,2,3,4,2,1,3,4,1,3,4,2,4,3,2,1};

    public FlipImage() {
        frame = new JFrame("Flip Flop Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        buttons = new JButton[TOTAL_TILES];
        flipStates = new boolean[TOTAL_TILES];
        lockedButtons = new boolean[TOTAL_TILES];
        Arrays.fill(lockedButtons, false);
        
        JPanel mainPanel = new JPanel(new GridLayout(ROWS, COLS, 4, 4)); // Panel for the main grid

        // Create a panel for the score label/button
        JPanel scorePanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: 0");
        scorePanel.add(scoreLabel, BorderLayout.NORTH); // Add the score label to the panel

        // Add the score panel and the main panel to the frame
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        
        /*JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.add(scoreLabel, BorderLayout.NORTH); // Add the score label to the panel

        frame.setLayout(new GridLayout(ROWS, COLS, 5,5));
        frame.add(scoreLabel, GridLayout.NORTH);*/
        
        for (int i = 0; i < TOTAL_TILES; i++) {
            buttons[i] = new JButton();
            //frame.add(buttons[i]);
            mainPanel.add(buttons[i]);
        }
        frame.setVisible(true);
        
        //initializeGame();
        
        Thread initializationThread = new Thread(() -> {
            initializeGame();
        });
        initializationThread.start();
    }
    
    public static void shuffleArray(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }
        Collections.shuffle(list);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
    }
    

    // Load the sound file
    public void loadSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("pop2.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Play the sound
    public void playSound() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0); // Reset to the beginning
            clip.start();
        }
    }

     public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Score: " + score);
    }
    
    private void initializeGame() {
        //call the loadSound() method before your JFrame is displayed to ensure the sound is loaded properly.
        loadSound();
        shuffleArray(arr);
        //First Show What is Underneath the Tiles 
        for (int i = 0; i < TOTAL_TILES; i++) {
        String filename = arr[i] + ".png";
        ImageIcon imageIcon = new ImageIcon(filename);
            buttons[i].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[i].getWidth(), buttons[i].getHeight())));
        }
        
        //Add a time delay in between so that User can Memorize what the tiles had underneath
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        //Then Flip All the Tiles to start the Game 
        
        for (int i = 0; i < TOTAL_TILES; i++) {
            flipStates[i] = false;
            ImageIcon imageIcon = new ImageIcon("b3.png");
            buttons[i].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[i].getWidth(), buttons[i].getHeight())));
            buttons[i].addActionListener(new TileClickListener(i));
        }
    }
    
private void flipTile(int index) {
    flipStates[index] = true;  
    ImageIcon imageIcon;
    if( flipStates[index] && (arr[index]==1 || arr[index]==2 || arr[index]==3) || arr[index]==4){
        String filename = arr[index] + ".png";
        imageIcon = new ImageIcon(filename);
    }
    else {
        imageIcon = new ImageIcon("b3.png");
    }
    buttons[index].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[index].getWidth(), buttons[index].getHeight())));
    lockedButtons[index] = true;

    if( flag==0 ) {
        index1 = index;
        flag = 1;
    }
    else if( flag==1 ) {
        index2 = index;
        flag = 2;
    }
    
    if( flag==2 ){
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Index 1:  " + index1);
        System.out.println("Index 2:  " + index2);
        System.out.println("\n----------------\n");
        if( index1 != index2 && arr[index1]==arr[index2]  ) {
            lockedButtons[index1] = true;
            lockedButtons[index2] = true;
            score+=20;
            visited+=2;
            updateScore(score);
        }
        else if( arr[index1]!=arr[index2] ){
            lockedButtons[index1] = false;
            lockedButtons[index2] = false;
            imageIcon = new ImageIcon("b3.png");
            playSound();
            buttons[index1].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[index1].getWidth(), buttons[index1].getHeight())));
            buttons[index2].setIcon(new ImageIcon(getScaledImage(imageIcon.getImage(), buttons[index2].getWidth(), buttons[index2].getHeight())));
            score-=5;
            updateScore(score);
        }
        flag=0;
    }
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
            playSound();
            if (lockedButtons[index]) {
            return;
            }
        //flipTile(index);
        Thread flipThread = new Thread(() -> {
            flipTile(index);
        });
        flipThread.start();
        }
    }

    public static void main(String[] args) {
        // Ensure GUI updates are performed on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlipImage();
            }
        });
    }
}
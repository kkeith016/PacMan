package com.kyleekeith.pacman;

import com.kyleekeith.pacman.core.PacmanGamePanel;

import javax.swing.*;


public class GameFrame extends JFrame {
    public GameFrame(){
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new PacmanGamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

package com.kyleekeith.pacman.Core;

import javax.swing.*;

public class GameFrame {
    public static void main(String[] args){
        JFrame frame = new JFrame("Pac-Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PacmanGamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

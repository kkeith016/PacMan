package com.kyleekeith.pacman;

import com.kyleekeith.pacman.core.GameFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(GameFrame::new);
    }
}

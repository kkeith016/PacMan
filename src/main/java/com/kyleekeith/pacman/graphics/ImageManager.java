package com.kyleekeith.pacman.graphics;

import javax.swing.*;
import java.awt.*;

public class ImageManager {
    public Image wall, blueGhost, redGhost, pinkGhost, orangeGhost, scaredGhost;
    public Image pacUp, pacDown, pacLeft, pacRight;

    public ImageManager() {
        wall = load("/wall.png");
        blueGhost = load("/blueGhost.png");
        redGhost = load("/redGhost.png");
        pinkGhost = load("/pinkGhost.png");
        orangeGhost = load("/orangeGhost.png");
        scaredGhost = load("/scaredGhost.png");

        pacUp = load("/pacmanUp.png");
        pacDown = load("/pacmanDown.png");
        pacLeft = load("/pacmanLeft.png");
        pacRight = load("/pacmanRight.png");
    }

    private Image load(String path) {
        java.net.URL loc = getClass().getResource(path);
        if (loc == null) throw new RuntimeException("Image not found: " + path);
        return new ImageIcon(loc).getImage();
    }
}

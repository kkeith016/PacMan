package com.kyleekeith.pacman;

import java.awt.*;
import java.util.Random;

public class Ghost {
    public int x, y, width;
    public int velocityX = 0, velocityY = 0;
    public Image image;
    private final int startX, startY;
    private final Random random = new Random();

    public Ghost(Image img, int startX, int startY, int size) {
        this.image = img;
        this.x = startX;
        this.y = startY;
        this.startX = startX;
        this.startY = startY;
        this.width = size;
    }

    public void move() {
        x += velocityX;
        y += velocityY;
    }

    public void updateDirection(char dir, int speed) {
        switch (dir) {
            case 'U' -> { velocityX = 0; velocityY = -speed; }
            case 'D' -> { velocityX = 0; velocityY = speed; }
            case 'L' -> { velocityX = -speed; velocityY = 0; }
            case 'R' -> { velocityX = speed; velocityY = 0; }
        }
    }

    // ✅ Fixed: reset takes no arguments
    public void reset() {
        x = startX;
        y = startY;
        velocityX = 0;
        velocityY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, width);
    }
}

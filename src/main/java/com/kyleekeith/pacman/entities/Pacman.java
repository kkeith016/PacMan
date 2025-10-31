package com.kyleekeith.pacman.entities;

import java.awt.*;

public class Pacman {
    public int x, y, width;
    public int velocityX = 0, velocityY = 0;
    public Image upImage, downImage, leftImage, rightImage;
    public Image currentImage;
    private final int startX, startY;

    public Pacman(Image up, Image down, Image left, Image right, int startX, int startY, int size) {
        this.upImage = up;
        this.downImage = down;
        this.leftImage = left;
        this.rightImage = right;
        this.currentImage = right;
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

    public void setDirection(char dir, int speed) {
        switch (dir) {
            case 'U' -> { velocityX = 0; velocityY = -speed; currentImage = upImage; }
            case 'D' -> { velocityX = 0; velocityY = speed; currentImage = downImage; }
            case 'L' -> { velocityX = -speed; velocityY = 0; currentImage = leftImage; }
            case 'R' -> { velocityX = speed; velocityY = 0; currentImage = rightImage; }
        }
    }

    public void reset() {
        x = startX;
        y = startY;
        velocityX = 0;
        velocityY = 0;
        currentImage = rightImage;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, width);
    }
}

package com.kyleekeith.pacman.entities;

import java.awt.*;

public class Pacman extends GameObject {
    public Image up, down, left, right;
    public Image currentImage;
    public char direction = 'L'; // 'U', 'D', 'L', 'R'

    // Store starting position for reset
    private int startX, startY;

    public Pacman(Image up, Image down, Image left, Image right, int x, int y, int size) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.x = this.startX = x;
        this.y = this.startY = y;
        this.width = this.height = size;
        this.currentImage = left;
    }

    public void setDirection(char dir, int speed) {
        direction = dir;
        switch (dir) {
            case 'U' -> { velocityX = 0; velocityY = -speed; currentImage = up; }
            case 'D' -> { velocityX = 0; velocityY = speed; currentImage = down; }
            case 'L' -> { velocityX = -speed; velocityY = 0; currentImage = left; }
            case 'R' -> { velocityX = speed; velocityY = 0; currentImage = right; }
        }
    }

    @Override
    public void move() {
        x += velocityX;
        y += velocityY;
    }

    @Override
    public void undoMove() {
        x -= velocityX;
        y -= velocityY;
    }

    @Override
    public void reset() {
        x = startX;
        y = startY;
        velocityX = 0;
        velocityY = 0;
        direction = 'L';
        currentImage = left;
    }
}
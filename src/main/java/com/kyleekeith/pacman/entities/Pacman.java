package com.kyleekeith.pacman.entities;

import java.awt.Image;
import com.kyleekeith.pacman.util.Constants;

public class Pacman extends GameObject {
    public Image currentImage, up, down, left, right;
    private int startX, startY;

    public Pacman(Image up, Image down, Image left, Image right, int x, int y, int size) {
        this.up = up; this.down = down; this.left = left; this.right = right;
        this.currentImage = right;
        this.x = startX = x; this.y = startY = y;
        this.width = this.height = size;
    }

    public void setDirection(char dir, int speed) {
        switch (dir) {
            case 'U' -> { velocityX = 0; velocityY = -speed; currentImage = up; }
            case 'D' -> { velocityX = 0; velocityY = speed; currentImage = down; }
            case 'L' -> { velocityX = -speed; velocityY = 0; currentImage = left; }
            case 'R' -> { velocityX = speed; velocityY = 0; currentImage = right; }
        }
    }

    @Override
    public void move() { x += velocityX; y += velocityY; }

    @Override
    public void undoMove() { x -= velocityX; y -= velocityY; }

    @Override
    public void reset() { x = startX; y = startY; velocityX = velocityY = 0; }
}

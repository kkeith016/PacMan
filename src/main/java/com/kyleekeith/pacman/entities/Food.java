package com.kyleekeith.pacman.entities;

import java.awt.*;

public class Food extends GameObject {
    private boolean powerPellet;

    public Food(int x, int y, int width, int height, boolean powerPellet) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.powerPellet = powerPellet;
    }

    public boolean isPowerPellet() { return powerPellet; }

    @Override
    public void move() {}
    @Override
    public void undoMove() {}
    @Override
    public void reset() {}
}

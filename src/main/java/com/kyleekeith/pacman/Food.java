package com.kyleekeith.pacman;

import java.awt.*;

public class Food {
    public int x, y, width, height;

    public Food(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
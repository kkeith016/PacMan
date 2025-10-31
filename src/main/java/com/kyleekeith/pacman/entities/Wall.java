package com.kyleekeith.pacman.entities;

import java.awt.*;

public class Wall {
    public int x, y, width, height;
    public Image image;

    public Wall(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

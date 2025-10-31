package com.kyleekeith.pacman.entities;

import java.awt.Rectangle;

public abstract class GameObject {
    public int x, y, width, height;
    public int velocityX, velocityY;

    public abstract void move();
    public abstract void undoMove();
    public abstract void reset();
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
}

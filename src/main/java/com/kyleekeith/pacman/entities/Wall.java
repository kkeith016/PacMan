package com.kyleekeith.pacman.entities;

import java.awt.Image;

public class Wall extends GameObject {
    public Image image;
    public Wall(Image img, int x, int y, int width, int height){
        this.image = img;
        this.x=x; this.y=y;
        this.width=width; this.height=height;
    }

    @Override public void move() {}
    @Override public void undoMove() {}
    @Override public void reset() {}
}

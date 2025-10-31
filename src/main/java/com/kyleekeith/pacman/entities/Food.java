package com.kyleekeith.pacman.entities;

public class Food extends GameObject {
    public boolean isSuperPellet = false;
    public Food(int x, int y, int w, int h, boolean superPellet){
        this.x=x; this.y=y; this.width=w; this.height=h; this.isSuperPellet=superPellet;
    }

    @Override public void move() {}
    @Override public void undoMove() {}
    @Override public void reset() {}
}

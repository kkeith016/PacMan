package com.kyleekeith.pacman.entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Set;

public class Ghost extends GameObject {
    public Image image, scaredImage;
    public boolean scared = false;
    private int startX, startY;
    private Random random = new Random();

    public Ghost(Image normal, Image scared, int x, int y, int size) {
        this.image = normal;
        this.scaredImage = scared;
        this.x = startX = x;
        this.y = startY = y;
        this.width = this.height = size;
        this.velocityX = 0; this.velocityY = 0;
    }

    public void setScared(boolean val) { scared = val; image = val ? scaredImage : image; }

    @Override public void move() { x += velocityX; y += velocityY; }
    @Override public void undoMove() { x -= velocityX; y -= velocityY; }

    @Override
    public void reset() {
        x = startX; y = startY; velocityX = 0; velocityY = 0; scared = false;
    }

    public void chasePacman(Pacman pacman, Set<Wall> walls, int tileSize, int speed) {
        if (x % tileSize != 0 || y % tileSize != 0) return;

        int bestDist = Integer.MAX_VALUE;
        char bestDir = 'U';
        char[] dirs = {'U','D','L','R'};

        for (char dir : dirs) {
            int newX = x, newY = y;
            switch (dir) {
                case 'U' -> newY -= tileSize;
                case 'D' -> newY += tileSize;
                case 'L' -> newX -= tileSize;
                case 'R' -> newX += tileSize;
            }

            Rectangle future = new Rectangle(newX,newY,width,height);
            boolean collides = walls.stream().anyMatch(w -> w.getBounds().intersects(future));
            if (collides) continue;

            int dist = Math.abs(newX - pacman.x) + Math.abs(newY - pacman.y);
            if (dist < bestDist) { bestDist = dist; bestDir = dir; }
        }

        switch(bestDir){
            case 'U' -> { velocityX=0; velocityY=-speed; }
            case 'D' -> { velocityX=0; velocityY=speed; }
            case 'L' -> { velocityX=-speed; velocityY=0; }
            case 'R' -> { velocityX=speed; velocityY=0; }
        }
    }

    public void chooseRandomDirection(Set<Wall> walls, int tileSize, int speed) {
        if(x%tileSize!=0 || y%tileSize!=0) return;
        char[] dirs = {'U','D','L','R'};
        char dir;
        boolean valid;
        int attempts = 0;

        do {
            dir = dirs[random.nextInt(dirs.length)];
            int newX = x, newY = y;
            switch(dir){
                case 'U'-> newY -= tileSize;
                case 'D'-> newY += tileSize;
                case 'L'-> newX -= tileSize;
                case 'R'-> newX += tileSize;
            }
            Rectangle future = new Rectangle(newX,newY,width,height);
            valid = walls.stream().noneMatch(w->w.getBounds().intersects(future));
            attempts++;
        } while(!valid && attempts<10);

        switch(dir){
            case 'U' -> { velocityX=0; velocityY=-speed; }
            case 'D' -> { velocityX=0; velocityY=speed; }
            case 'L' -> { velocityX=-speed; velocityY=0; }
            case 'R' -> { velocityX=speed; velocityY=0; }
        }
    }
}

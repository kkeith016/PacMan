package com.kyleekeith.pacman.entities;

import java.awt.*;
import java.util.Random;
import java.util.Set;

public class Ghost extends GameObject {
    private int startX, startY;
    private Random random = new Random();
    private boolean inGhostHouse = true;

    public Image normalImage, scaredImage;
    public boolean scared = false;
    public GhostType type;

    public Ghost(GhostType type, Image normal, Image scared, int x, int y, int size) {
        this.type = type;
        this.normalImage = normal;
        this.scaredImage = scared;
        this.x = this.startX = x;
        this.y = this.startY = y;
        this.width = this.height = size;

        // Only Blinky starts outside the ghost house
        this.inGhostHouse = (type != GhostType.BLINKY);
    }

    public void setScared(boolean val) {
        scared = val;
    }

    public boolean isScared() {
        return scared;
    }

    public GhostType getType() {
        return type;
    }

    public boolean isInGhostHouse() {
        return inGhostHouse;
    }

    public Image getCurrentImage() {
        return scared ? scaredImage : normalImage;
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
        scared = false;
        inGhostHouse = (type != GhostType.BLINKY);
    }

    public void chasePacman(Pacman pacman, Set<Wall> walls, Set<Ghost> allGhosts, int tileSize, int speed, Ghost blinky) {
        // If still in ghost house, exit through the door
        if (inGhostHouse) {
            exitGhostHouse(tileSize, speed);
            return;
        }

        // Only change direction at tile boundaries
        if (x % tileSize != 0 || y % tileSize != 0) return;

        int targetX = pacman.x;
        int targetY = pacman.y;

        // Each ghost has unique targeting behavior
        switch (type) {
            case PINKY -> {
                // Pinky targets 4 tiles ahead of Pacman
                switch (pacman.direction) {
                    case 'U' -> targetY -= 4 * tileSize;
                    case 'D' -> targetY += 4 * tileSize;
                    case 'L' -> targetX -= 4 * tileSize;
                    case 'R' -> targetX += 4 * tileSize;
                }
            }
            case INKY -> {
                // Inky uses Blinky's position to calculate target
                if (blinky != null) {
                    int vx = pacman.x - blinky.x;
                    int vy = pacman.y - blinky.y;
                    targetX = pacman.x + vx;
                    targetY = pacman.y + vy;
                }
            }
            case CLYDE -> {
                // Clyde targets Pacman when far, but retreats when close
                int dist = Math.abs(pacman.x - x) + Math.abs(pacman.y - y);
                if (dist < 8 * tileSize) {
                    // Retreat to bottom-left corner
                    targetX = 2 * tileSize;
                    targetY = 26 * tileSize;
                }
            }
            case BLINKY -> {
                // Blinky directly chases Pacman (default behavior)
            }
        }

        moveTowardTarget(targetX, targetY, walls, allGhosts, tileSize, speed);
    }

    private void moveTowardTarget(int targetX, int targetY, Set<Wall> walls, Set<Ghost> allGhosts, int tileSize, int speed) {
        int bestDist = Integer.MAX_VALUE;
        char bestDir = 'U';
        char[] dirs = {'U', 'D', 'L', 'R'};

        // Test each direction and pick the one that gets closest to target
        for (char dir : dirs) {
            int newX = x, newY = y;
            switch (dir) {
                case 'U' -> newY -= tileSize;
                case 'D' -> newY += tileSize;
                case 'L' -> newX -= tileSize;
                case 'R' -> newX += tileSize;
            }

            // Check if this direction would hit a wall or another ghost
            Rectangle future = new Rectangle(newX, newY, width, height);
            boolean collidesWall = walls.stream().anyMatch(w -> w.getBounds().intersects(future));
            boolean collidesGhost = allGhosts.stream()
                    .filter(g -> g != this)
                    .anyMatch(g -> g.getBounds().intersects(future));

            if (collidesWall || collidesGhost) continue;

            // Calculate distance to target
            int dist = Math.abs(newX - targetX) + Math.abs(newY - targetY);
            if (dist < bestDist) {
                bestDist = dist;
                bestDir = dir;
            }
        }

        // Set velocity based on best direction
        switch (bestDir) {
            case 'U' -> { velocityX = 0; velocityY = -speed; }
            case 'D' -> { velocityX = 0; velocityY = speed; }
            case 'L' -> { velocityX = -speed; velocityY = 0; }
            case 'R' -> { velocityX = speed; velocityY = 0; }
        }
    }

    public void chooseRandomDirection(Set<Wall> walls, Set<Ghost> allGhosts, int tileSize, int speed) {
        // If still in ghost house, exit through the door
        if (inGhostHouse) {
            exitGhostHouse(tileSize, speed);
            return;
        }

        // Only change direction at tile boundaries
        if (x % tileSize != 0 || y % tileSize != 0) return;

        char[] dirs = {'U', 'D', 'L', 'R'};
        char dir;
        boolean valid;
        int attempts = 0;

        // Try random directions until we find one that doesn't hit a wall or ghost
        do {
            dir = dirs[random.nextInt(dirs.length)];
            int newX = x, newY = y;

            switch (dir) {
                case 'U' -> newY -= tileSize;
                case 'D' -> newY += tileSize;
                case 'L' -> newX -= tileSize;
                case 'R' -> newX += tileSize;
            }

            Rectangle future = new Rectangle(newX, newY, width, height);
            boolean collidesWall = walls.stream().noneMatch(w -> w.getBounds().intersects(future));
            boolean collidesGhost = allGhosts.stream()
                    .filter(g -> g != this)
                    .noneMatch(g -> g.getBounds().intersects(future));

            valid = collidesWall && collidesGhost;
            attempts++;
        } while (!valid && attempts < 10);

        // Set velocity for chosen direction
        switch (dir) {
            case 'U' -> { velocityX = 0; velocityY = -speed; }
            case 'D' -> { velocityX = 0; velocityY = speed; }
            case 'L' -> { velocityX = -speed; velocityY = 0; }
            case 'R' -> { velocityX = speed; velocityY = 0; }
        }
    }

    // Method to make ghosts exit the ghost house
    private void exitGhostHouse(int tileSize, int speed) {
        // Ghost house door is at approximately tile (13, 12)
        int doorX = 13 * tileSize;
        int doorY = 12 * tileSize;

        // Move toward center of ghost house horizontally first
        if (x < doorX) {
            velocityX = speed;
            velocityY = 0;
        } else if (x > doorX) {
            velocityX = -speed;
            velocityY = 0;
        }
        // Once centered, move up to exit
        else if (y > doorY) {
            velocityX = 0;
            velocityY = -speed;
        }
        // Once outside, mark as having left
        else {
            inGhostHouse = false;
            velocityY = -speed;
            velocityX = 0;
        }
    }

    // Method to return to ghost house (when eaten while scared)
    public void returnToGhostHouse(int tileSize, int speed) {
        int doorX = 13 * tileSize;
        int doorY = 12 * tileSize;
        int houseY = 14 * tileSize;

        // Move toward the door horizontally
        if (Math.abs(x - doorX) > speed) {
            if (x < doorX) {
                velocityX = speed;
                velocityY = 0;
            } else {
                velocityX = -speed;
                velocityY = 0;
            }
        }
        // Move down through the door
        else if (y < houseY) {
            velocityX = 0;
            velocityY = speed;
            x = doorX;
        }
        // Arrived back in house
        else {
            inGhostHouse = true;
            reset();
        }
    }
}
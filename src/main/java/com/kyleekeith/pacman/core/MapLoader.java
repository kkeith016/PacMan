package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;

import java.awt.*;
import java.util.Set;

public class MapLoader {

    private final int TILE_SIZE = 24;
    private final int MAP_WIDTH = 28 * TILE_SIZE;   // 672 pixels
    private final int MAP_HEIGHT = 31 * TILE_SIZE;  // 744 pixels

    private final Image blueGhost = loadImage("/blueGhost.png");
    private final Image redGhost = loadImage("/redGhost.png");
    private final Image pinkGhost = loadImage("/pinkGhost.png");
    private final Image orangeGhost = loadImage("/orangeGhost.png");
    private final Image scaredGhost = loadImage("/scaredGhost.png");
    private final Image pacUp = loadImage("/pacmanUp.png");
    private final Image pacDown = loadImage("/pacmanDown.png");
    private final Image pacLeft = loadImage("/pacmanLeft.png");
    private final Image pacRight = loadImage("/pacmanRight.png");

    private Pacman pacman;

    private Image loadImage(String path) {
        return new javax.swing.ImageIcon(getClass().getResource(path)).getImage();
    }

    public void loadLevel(Set<Wall> walls, Set<Food> foods, Set<Ghost> ghosts) {
        walls.clear();
        foods.clear();
        ghosts.clear();

        // Create walls using MapShape
        MapShape.createClassicMap();
        walls.addAll(MapShape.walls);

        // Place foods in a grid, avoiding walls
        // Start from tile 1 and go to tile 26 (leaving border)
        for (int tileY = 1; tileY < 30; tileY++) {
            for (int tileX = 1; tileX < 27; tileX++) {
                int x = tileX * TILE_SIZE;
                int y = tileY * TILE_SIZE;

                // Center the food pellet in the tile
                int foodX = x + TILE_SIZE / 2 - 4;  // 4 is half of food size (8)
                int foodY = y + TILE_SIZE / 2 - 4;

                final int fx = foodX;
                final int fy = foodY;

                // Check if this position collides with any wall
                boolean blocked = walls.stream().anyMatch(w ->
                        fx + 8 >= w.x && fx <= w.x + w.width &&
                                fy + 8 >= w.y && fy <= w.y + w.height
                );

                // Also skip the ghost house area (tiles 10-17 horizontally, 13-17 vertically)
                boolean inGhostHouse = (tileX >= 10 && tileX <= 17 && tileY >= 13 && tileY <= 17);

                // Skip the tunnel area (tiles 14-16 vertically on far left and right)
                boolean inTunnel = ((tileX == 0 || tileX == 27) && tileY >= 14 && tileY <= 16);

                if (!blocked && !inGhostHouse && !inTunnel) {
                    // Place power pellets in corners
                    boolean isPowerPellet = (tileX == 1 && tileY == 3) ||    // Top-left
                            (tileX == 26 && tileY == 3) ||   // Top-right
                            (tileX == 1 && tileY == 23) ||   // Bottom-left
                            (tileX == 26 && tileY == 23);    // Bottom-right

                    foods.add(new Food(foodX, foodY, 8, 8, isPowerPellet));
                }
            }
        }

        // Place Pacman at classic starting position (bottom center)
        // Classic position is around tile (13, 23)
        pacman = new Pacman(
                pacUp,
                pacDown,
                pacLeft,
                pacRight,
                13 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,  // Center in tile
                23 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                TILE_SIZE
        );

        // Place ghosts in the ghost house (classic starting positions)
        // Blinky (red) - starts outside the ghost house, above it
        ghosts.add(new Ghost(
                GhostType.BLINKY,
                redGhost,
                scaredGhost,
                13 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                11 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                TILE_SIZE
        ));

        // Pinky (pink) - center of ghost house
        ghosts.add(new Ghost(
                GhostType.PINKY,
                pinkGhost,
                scaredGhost,
                13 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                14 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                TILE_SIZE
        ));

        // Inky (blue) - left side of ghost house
        ghosts.add(new Ghost(
                GhostType.INKY,
                blueGhost,
                scaredGhost,
                11 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                14 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                TILE_SIZE
        ));

        // Clyde (orange) - right side of ghost house
        ghosts.add(new Ghost(
                GhostType.CLYDE,
                orangeGhost,
                scaredGhost,
                15 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                14 * TILE_SIZE + TILE_SIZE / 2 - TILE_SIZE / 2,
                TILE_SIZE
        ));
    }

    public Pacman getPacman() {
        return pacman;
    }

    // Getter methods for map dimensions (useful for game panel sizing)
    public int getMapWidth() {
        return MAP_WIDTH;
    }

    public int getMapHeight() {
        return MAP_HEIGHT;
    }
}
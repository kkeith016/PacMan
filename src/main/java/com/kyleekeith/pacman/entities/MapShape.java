package com.kyleekeith.pacman.entities;

import java.util.HashSet;
import java.util.Set;

public class MapShape {
    public static Set<Wall> walls = new HashSet<>();

    private static final int T = 24; // Tile size

    public static void createClassicMap() {
        walls.clear();

        // OUTER BOUNDARIES (with tunnel gaps on sides)
        // Top wall
        addHWall(0, 0, 28);

        // Bottom wall
        addHWall(0, 30, 28);

        // Left wall (with gap at 14-16 for tunnel)
        addVWall(0, 1, 13);   // Top portion (tiles 1-13)
        addVWall(0, 17, 13);  // Bottom portion (tiles 17-29)

        // Right wall (with gap at 14-16 for tunnel)
        addVWall(27, 1, 13);  // Top portion
        addVWall(27, 17, 13); // Bottom portion

        // TOP SECTION
        // Corner boxes (top-left and top-right)
        addBox(2, 2, 4, 4);   // Top-left corner box
        addBox(22, 2, 4, 4);  // Top-right corner box

        // Small vertical extensions below corners
        addVWall(2, 7, 2);
        addVWall(5, 7, 2);
        addVWall(22, 7, 2);
        addVWall(25, 7, 2);

        // T-shapes near corners
        addHWall(2, 10, 4);
        addVWall(3, 10, 2);

        addHWall(22, 10, 4);
        addVWall(24, 10, 2);

        // Top center section
        addHWall(7, 2, 4);
        addVWall(9, 2, 2);

        addHWall(17, 2, 4);
        addVWall(17, 2, 2);

        // Small boxes in upper-middle
        addBox(7, 5, 2, 2);
        addBox(19, 5, 2, 2);

        // Center top box
        addBox(12, 2, 4, 2);
        addVWall(13, 5, 2);

        addHWall(7, 8, 2);
        addHWall(19, 8, 2);

        // Upper T-sections
        addHWall(12, 8, 4);
        addVWall(13, 8, 2);

        // MIDDLE SECTION
        // Side boxes
        addBox(2, 13, 4, 2);
        addBox(22, 13, 4, 2);

        addBox(7, 11, 2, 4);
        addBox(19, 11, 2, 4);

        // GHOST HOUSE (center box with door)
        addVWall(10, 13, 5);  // Left wall
        addVWall(17, 13, 5);  // Right wall
        addHWall(10, 13, 3);  // Top-left
        addHWall(15, 13, 3);  // Top-right (gap in middle for door)
        addHWall(10, 17, 8);  // Bottom

        // Horizontal corridors beside ghost house
        addHWall(2, 16, 4);
        addHWall(22, 16, 4);

        addVWall(7, 16, 2);
        addVWall(19, 16, 2);

        // LOWER MIDDLE SECTION
        addBox(7, 19, 2, 2);
        addBox(19, 19, 2, 2);

        addHWall(12, 20, 4);
        addVWall(13, 20, 2);

        // BOTTOM SECTION
        // Bottom corner boxes
        addBox(2, 23, 4, 4);
        addBox(22, 23, 4, 4);

        // Extensions above bottom corners
        addVWall(2, 28, 1);
        addVWall(5, 28, 1);
        addVWall(22, 28, 1);
        addVWall(25, 28, 1);

        // Small pieces near bottom
        addHWall(7, 23, 2);
        addHWall(19, 23, 2);

        addBox(7, 26, 4, 2);
        addBox(17, 26, 4, 2);

        addVWall(9, 23, 2);
        addVWall(17, 23, 2);

        // Bottom center
        addHWall(12, 26, 4);
        addVWall(13, 26, 2);
    }

    // Helper: Add horizontal wall starting at tile (x, y) for 'length' tiles
    private static void addHWall(int tileX, int tileY, int lengthTiles) {
        walls.add(new Wall(tileX * T, tileY * T, lengthTiles * T, T));
    }

    // Helper: Add vertical wall starting at tile (x, y) for 'length' tiles
    private static void addVWall(int tileX, int tileY, int lengthTiles) {
        walls.add(new Wall(tileX * T, tileY * T, T, lengthTiles * T));
    }

    // Helper: Add a hollow box at tile (x, y) with given width and height in tiles
    private static void addBox(int tileX, int tileY, int widthTiles, int heightTiles) {
        addHWall(tileX, tileY, widthTiles);                    // Top
        addHWall(tileX, tileY + heightTiles - 1, widthTiles);  // Bottom
        addVWall(tileX, tileY, heightTiles);                   // Left
        addVWall(tileX + widthTiles - 1, tileY, heightTiles);  // Right
    }
}
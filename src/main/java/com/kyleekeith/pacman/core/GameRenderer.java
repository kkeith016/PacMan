package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;
import java.awt.*;
import java.util.Set;

public class GameRenderer {

    private Set<Wall> walls;
    private Set<Food> foods;
    private Set<Ghost> ghosts;
    private Pacman pacman;
    private int score;
    private int lives;
    private boolean gameOver;

    public GameRenderer(Set<Wall> walls, Set<Food> foods, Set<Ghost> ghosts,
                        Pacman pacman, int score, int lives, boolean gameOver) {
        this.walls = walls;
        this.foods = foods;
        this.ghosts = ghosts;
        this.pacman = pacman;
        this.score = score;
        this.lives = lives;
        this.gameOver = gameOver;
    }

    public void draw(Graphics g) {
        // Draw walls using their draw method
        for (Wall w : walls) {
            w.draw(g);
        }

        // Draw foods
        g.setColor(Color.WHITE);
        for (Food f : foods) {
            g.fillRect(f.x, f.y, f.width, f.height);
        }

        // Draw Pac-Man
        if (pacman != null) {
            g.drawImage(pacman.currentImage, pacman.x, pacman.y, pacman.width, pacman.height, null);
        }

        // Draw ghosts
        for (Ghost ghost : ghosts) {
            g.drawImage(ghost.getCurrentImage(), ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        // Draw HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + lives + "  Score: " + score, 10, 16);

        if (gameOver) {
            g.drawString("GAME OVER", 180, 250);
        }
    }
}

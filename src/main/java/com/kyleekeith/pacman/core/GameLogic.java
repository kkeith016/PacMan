package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;
import java.util.*;

public class GameLogic {

    private Pacman pacman;
    private Set<Wall> walls = new HashSet<>();
    private Set<Food> foods = new HashSet<>();
    private Set<Ghost> ghosts = new HashSet<>();

    private boolean gameOver = false;
    private int score = 0;
    private int lives = 3;
    private MapLoader mapLoader = new MapLoader();

    public GameLogic() {
        mapLoader.loadLevel(walls, foods, ghosts);
        pacman = mapLoader.getPacman();
    }

    public void update() {
        if (gameOver) return;

        pacman.move();
        checkCollisions();
        for (Ghost g : ghosts) g.move();

        if (foods.isEmpty()) resetLevel();
    }

    private void checkCollisions() {
        // Wall collisions
        for (Wall w : walls)
            if (pacman.getBounds().intersects(w.getBounds()))
                pacman.undoMove();

        // Food collisions
        Food eaten = null;
        for (Food f : foods)
            if (pacman.getBounds().intersects(f.getBounds())) {
                eaten = f;
                score += 10;
            }
        if (eaten != null) foods.remove(eaten);

        // Ghost collisions
        for (Ghost g : ghosts)
            if (pacman.getBounds().intersects(g.getBounds())) {
                lives--;
                if (lives <= 0) gameOver = true;
                resetPositions();
                break;
            }
    }

    private void resetPositions() {
        pacman.reset();
        for (Ghost g : ghosts) g.reset();
    }

    private void resetLevel() {
        mapLoader.loadLevel(walls, foods, ghosts);
        resetPositions();
    }

    // Getters for renderer
    public Set<Wall> getWalls() { return walls; }
    public Set<Food> getFoods() { return foods; }
    public Set<Ghost> getGhosts() { return ghosts; }
    public Pacman getPacman() { return pacman; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return gameOver; }
}

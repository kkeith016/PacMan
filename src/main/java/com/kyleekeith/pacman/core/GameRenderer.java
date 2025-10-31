package com.kyleekeith.pacman.core;

import java.awt.*;
import com.kyleekeith.pacman.entities.*;

public class GameRenderer {
    private GameLogic logic;

    public GameRenderer(GameLogic logic) {
        this.logic = logic;
    }

    public void draw(Graphics g) {
        // Draw walls
        for (Wall w : logic.getWalls())
            g.drawImage(w.image, w.x, w.y, w.width, w.height, null);

        // Draw food
        g.setColor(Color.WHITE);
        for (Food f : logic.getFoods())
            g.fillRect(f.x, f.y, f.width, f.height);

        // Draw Pacman
        Pacman p = logic.getPacman();
        g.drawImage(p.currentImage, p.x, p.y, p.width, p.height, null);

        // Draw ghosts
        for (Ghost ghost : logic.getGhosts())
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);

        // Draw HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + logic.getLives() + "  Score: " + logic.getScore(), 10, 16);

        if (logic.isGameOver())
            g.drawString("GAME OVER", 180, 250);
    }
}

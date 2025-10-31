package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;
import com.kyleekeith.pacman.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class PacmanGamePanel extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private MapLoader loader = new MapLoader();

    private Set<Wall> walls = new HashSet<>();
    private Set<Food> foods = new HashSet<>();
    private Set<Ghost> ghosts = new HashSet<>();
    private Pacman pacman;

    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;

    public PacmanGamePanel() {
        setPreferredSize(new Dimension(TileMap.getCols() * Constants.TILE_SIZE,
                TileMap.getRows() * Constants.TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        loadLevel();
        timer = new Timer(50, this);
        timer.start();
    }

    private void loadLevel() {
        loader.loadLevel(walls, foods, ghosts);
        pacman = loader.getPacman();
    }

    private boolean collision(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    private void move() {
        pacman.move();

        for (Wall w : walls)
            if (collision(pacman.getBounds(), w.getBounds()))
                pacman.undoMove();

        Food eaten = null;
        for (Food f : foods) {
            if (collision(pacman.getBounds(), f.getBounds())) {
                eaten = f;
                score += f.isSuperPellet ? 50 : 10;

                if (f.isSuperPellet) {
                    for (Ghost g : ghosts) g.setScared(true);
                    new javax.swing.Timer(10000, e -> {
                        for (Ghost g : ghosts) g.setScared(false);
                    }) {{
                        setRepeats(false);
                        start();
                    }};
                }
            }
        }
        foods.remove(eaten);

        for (Ghost g : ghosts) {
            if (g.scared) g.chooseRandomDirection(walls, Constants.TILE_SIZE, Constants.SPEED);
            else g.chasePacman(pacman, walls, Constants.TILE_SIZE, Constants.SPEED);

            g.move();
            for (Wall w : walls) if (collision(g.getBounds(), w.getBounds())) g.undoMove();

            if (collision(pacman.getBounds(), g.getBounds())) {
                if (g.scared) {
                    score += 100;
                    g.reset();
                } else {
                    lives--;
                    if (lives <= 0) gameOver = true;
                    resetPositions();
                }
            }
        }

        if (foods.isEmpty()) {
            loadLevel();
            resetPositions();
        }
    }

    private void resetPositions() {
        pacman.reset();
        for (Ghost g : ghosts) g.reset();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw walls safely
        for (Wall w : walls) {
            if (w.image != null && w.width > 0 && w.height > 0)
                g.drawImage(w.image, w.x, w.y, w.width, w.height, null);
        }

        // Draw foods
        g.setColor(Color.WHITE);
        for (Food f : foods)
            g.fillRect(f.x, f.y, f.width, f.height);

        // Draw Pac-Man
        if (pacman != null && pacman.currentImage != null && pacman.width > 0 && pacman.height > 0)
            g.drawImage(pacman.currentImage, pacman.x, pacman.y, pacman.width, pacman.height, null);

        // Draw ghosts safely
        for (Ghost ghost : ghosts) {
            if (ghost.image != null && ghost.width > 0 && ghost.height > 0)
                g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        // Draw score and lives
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + lives + "  Score: " + score, 10, 16);

        if (gameOver)
            g.drawString("GAME OVER", getWidth() / 2 - 50, getHeight() / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) move();
        repaint();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            loadLevel();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            timer.start();
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> pacman.setDirection('U', Constants.SPEED);
            case KeyEvent.VK_DOWN -> pacman.setDirection('D', Constants.SPEED);
            case KeyEvent.VK_LEFT -> pacman.setDirection('L', Constants.SPEED);
            case KeyEvent.VK_RIGHT -> pacman.setDirection('R', Constants.SPEED);
        }
    }
}

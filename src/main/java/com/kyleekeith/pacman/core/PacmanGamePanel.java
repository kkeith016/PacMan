package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class PacmanGamePanel extends JPanel implements ActionListener, KeyListener {

    private final Timer timer;
    private final MapLoader loader = new MapLoader();

    private final Set<Wall> walls = new HashSet<>();
    private final Set<Food> foods = new HashSet<>();
    private final Set<Ghost> ghosts = new HashSet<>();
    private Pacman pacman;

    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;
    private final int TILE_SIZE = 24;

    // Updated map dimensions for classic Pac-Man (28x31 tiles)
    private final int MAP_WIDTH = 28 * TILE_SIZE;   // 672 pixels
    private final int MAP_HEIGHT = 31 * TILE_SIZE;  // 744 pixels

    public PacmanGamePanel() {
        setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));
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

        // Handle screen wrapping in the tunnel (left-right sides)
        // Tunnel is around y=14-16 tiles (336-384 pixels)
        if (pacman.y >= 14 * TILE_SIZE && pacman.y <= 16 * TILE_SIZE) {
            // Wrap from right to left
            if (pacman.x >= MAP_WIDTH) {
                pacman.x = 0;
            }
            // Wrap from left to right
            else if (pacman.x + pacman.width <= 0) {
                pacman.x = MAP_WIDTH - pacman.width;
            }
        }

        // Collide with walls
        for (Wall w : walls) {
            if (collision(pacman.getBounds(), w.getBounds())) {
                pacman.undoMove();
            }
        }

        // Eat food
        Food eaten = null;
        for (Food f : foods) {
            if (collision(pacman.getBounds(), f.getBounds())) {
                eaten = f;
                score += f.isPowerPellet() ? 50 : 10;

                if (f.isPowerPellet()) {
                    for (Ghost g : ghosts) {
                        g.setScared(true);
                    }
                    Timer scareTimer = new Timer(10000, e -> {
                        for (Ghost g : ghosts) {
                            g.setScared(false);
                        }
                    });
                    scareTimer.setRepeats(false);
                    scareTimer.start();
                }
            }
        }
        foods.remove(eaten);

        // Move ghosts - NOW PASS THE GHOSTS SET for collision avoidance
        Ghost blinky = ghosts.stream()
                .filter(g -> g.getType() == GhostType.BLINKY)
                .findFirst()
                .orElse(null);

        for (Ghost g : ghosts) {
            if (g.isScared()) {
                // Pass ghosts set so they avoid each other
                g.chooseRandomDirection(walls, ghosts, TILE_SIZE, 4);
            } else {
                // Pass ghosts set so they avoid each other
                g.chasePacman(pacman, walls, ghosts, TILE_SIZE, 4, blinky);
            }

            g.move();

            // Handle ghost screen wrapping in tunnel
            if (g.y >= 14 * TILE_SIZE && g.y <= 16 * TILE_SIZE) {
                if (g.x >= MAP_WIDTH) {
                    g.x = 0;
                } else if (g.x + g.width <= 0) {
                    g.x = MAP_WIDTH - g.width;
                }
            }

            for (Wall w : walls) {
                if (collision(g.getBounds(), w.getBounds())) {
                    g.undoMove();
                }
            }

            // Collide with Pacman
            if (collision(pacman.getBounds(), g.getBounds())) {
                if (g.isScared()) {
                    score += 200;
                    g.setScared(false);
                    g.reset();
                } else {
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                    }
                    resetPositions();
                }
            }
        }

        // Level complete
        if (foods.isEmpty()) {
            loadLevel();
            resetPositions();
        }
    }

    private void resetPositions() {
        pacman.reset();
        for (Ghost g : ghosts) {
            g.reset();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw walls
        g.setColor(new Color(33, 33, 255));
        for (Wall w : walls) {
            g.fillRect(w.x, w.y, w.width, w.height);
            g.setColor(new Color(66, 66, 255));
            g.drawRect(w.x, w.y, w.width - 1, w.height - 1);
            g.setColor(new Color(33, 33, 255));
        }

        // Draw foods
        for (Food f : foods) {
            if (f.isPowerPellet()) {
                g.setColor(Color.WHITE);
                g.fillOval(f.x - 4, f.y - 4, 16, 16);
            } else {
                g.setColor(Color.WHITE);
                g.fillOval(f.x, f.y, f.width, f.height);
            }
        }

        // Draw Pac-Man
        if (pacman != null) {
            g.drawImage(pacman.currentImage, pacman.x, pacman.y,
                    pacman.width, pacman.height, null);
        }

        // Draw ghosts
        for (Ghost ghost : ghosts) {
            g.drawImage(ghost.getCurrentImage(), ghost.x, ghost.y,
                    ghost.width, ghost.height, null);
        }

        // HUD at bottom
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, MAP_HEIGHT - 10);
        g.drawString("Lives: " + lives, MAP_WIDTH - 120, MAP_HEIGHT - 10);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", MAP_WIDTH / 2 - 150, MAP_HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Press any key to restart",
                    MAP_WIDTH / 2 - 120, MAP_HEIGHT / 2 + 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            loadLevel();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            timer.start();
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                pacman.setDirection('U', 4);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                pacman.setDirection('D', 4);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                pacman.setDirection('L', 4);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                pacman.setDirection('R', 4);
                break;
        }
    }
}
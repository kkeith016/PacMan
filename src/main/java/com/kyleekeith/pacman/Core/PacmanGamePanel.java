package com.kyleekeith.pacman.Core;

import com.kyleekeith.pacman.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;

public class PacmanGamePanel extends JPanel implements ActionListener, KeyListener {

    private final int tileSize = 44;
    private final int speed = tileSize / 4;

    private TileMap tileMap;
    private Pacman pacman;
    private HashSet<Wall> walls = new HashSet<>();
    private HashSet<Food> foods = new HashSet<>();
    private HashSet<Ghost> ghosts = new HashSet<>();

    private Image wallImage, blueGhost, redGhost, pinkGhost, orangeGhost;
    private Image pacUp, pacDown, pacLeft, pacRight;

    private Timer timer;
    private Random random = new Random();
    private char[] directions = {'U', 'D', 'L', 'R'};
    private int score = 0, lives = 3;
    private boolean gameOver = false;

    public PacmanGamePanel() {
        setPreferredSize(new Dimension(19 * tileSize, 21 * tileSize));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        loadImages();
        tileMap = new TileMap();
        loadMap();

        timer = new Timer(50, this);
        timer.start();
    }

    private void loadImages() {
        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhost = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        redGhost = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();
        pinkGhost = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        orangeGhost = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();

        pacUp = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacDown = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        pacLeft = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();
        pacRight = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
    }

    private void loadMap() {
        walls.clear();
        foods.clear();
        ghosts.clear();

        String[] layout = tileMap.layout;
        for (int r = 0; r < tileMap.getRows(); r++) {
            String row = layout[r];
            for (int c = 0; c < tileMap.getCols(); c++) {
                char ch = row.charAt(c);
                int x = c * tileSize;
                int y = r * tileSize;
                switch (ch) {
                    case 'X' -> walls.add(new Wall(wallImage, x, y, tileSize, tileSize));
                    case ' ' -> foods.add(new Food(x + tileSize / 2 - 4, y + tileSize / 2 - 4, 8, 8));
                    case 'P' -> pacman = new Pacman(pacUp, pacDown, pacLeft, pacRight, x, y, tileSize);
                    case 'b' -> ghosts.add(new Ghost(blueGhost, x, y, tileSize));
                    case 'r' -> ghosts.add(new Ghost(redGhost, x, y, tileSize));
                    case 'p' -> ghosts.add(new Ghost(pinkGhost, x, y, tileSize));
                    case 'o' -> ghosts.add(new Ghost(orangeGhost, x, y, tileSize));
                }
            }
        }

        for (Ghost g : ghosts) g.updateDirection(directions[random.nextInt(4)], speed);
    }

    private boolean collision(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw walls
        for (Wall w : walls) g.drawImage(w.image, w.x, w.y, w.width, w.height, null);

        // Draw foods
        g.setColor(Color.WHITE);
        for (Food f : foods) g.fillRect(f.x, f.y, f.width, f.height);

        // Draw Pac-Man
        g.drawImage(pacman.currentImage, pacman.x, pacman.y, pacman.width, pacman.width, null);

        // Draw ghosts
        for (Ghost ghost : ghosts) g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.width, null);

        // Draw score and lives
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + lives + " Score: " + score, 10, 16);
        if (gameOver) g.drawString("GAME OVER", getWidth() / 2 - 50, getHeight() / 2);
    }

    private void move() {
        // Move Pac-Man
        pacman.move();
        for (Wall w : walls)
            if (collision(pacman.getBounds(), w.getBounds())) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
            }

        // Eat food
        Food eaten = null;
        for (Food f : foods)
            if (collision(pacman.getBounds(), f.getBounds())) {
                eaten = f;
                score += 10;
            }
        foods.remove(eaten);

        // Move ghosts
        for (Ghost g : ghosts) {
            if (collision(pacman.getBounds(), g.getBounds())) {
                lives--;
                if (lives <= 0) gameOver = true;
                resetPositions();
            }

            // Update ghost direction at tile boundaries
            if (g.x % tileSize == 0 && g.y % tileSize == 0)
                g.updateDirection(directions[random.nextInt(4)], speed);

            g.move();
            for (Wall w : walls)
                if (collision(g.getBounds(), w.getBounds())) {
                    g.x -= g.velocityX;
                    g.y -= g.velocityY;
                    g.updateDirection(directions[random.nextInt(4)], speed);
                }
        }

        // Reset map if all food eaten
        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }

    private void resetPositions() {
        pacman.reset();
        for (Ghost g : ghosts) g.reset();
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
            loadMap();
            resetPositions();
            lives = 3;
            score = 0;
            gameOver = false;
            timer.start();
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> pacman.setDirection('U', speed);
            case KeyEvent.VK_DOWN -> pacman.setDirection('D', speed);
            case KeyEvent.VK_LEFT -> pacman.setDirection('L', speed);
            case KeyEvent.VK_RIGHT -> pacman.setDirection('R', speed);
        }
    }
}

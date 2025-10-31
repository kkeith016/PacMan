package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;
import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class MapLoader {
    private Pacman pacman;

    private Image wallImg = new ImageIcon(getClass().getResource("/wall.png")).getImage();
    private Image blueGhost = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
    private Image redGhost = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();
    private Image pinkGhost = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
    private Image orangeGhost = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
    private Image scaredGhost = new ImageIcon(getClass().getResource("/scaredGhost.png")).getImage();
    private Image pacUp = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
    private Image pacDown = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
    private Image pacLeft = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();
    private Image pacRight = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();

    public void loadLevel(Set<Wall> walls, Set<Food> foods, Set<Ghost> ghosts){
        walls.clear(); foods.clear(); ghosts.clear();

        String[] layout = TileMap.layout;
        for(int r=0;r<TileMap.getRows();r++){
            String row = layout[r];
            for(int c=0;c<TileMap.getCols();c++){
                char ch = row.charAt(c);
                int x=c*24, y=r*24;
                switch(ch){
                    case 'X' -> walls.add(new Wall(wallImg,x,y,24,24));
                    case '.' -> foods.add(new Food(x+10,y+10,4,4,false));
                    case '*' -> foods.add(new Food(x+10,y+10,4,4,true));
                    case 'P' -> pacman = new Pacman(pacUp,pacDown,pacLeft,pacRight,x,y,24);
                    case 'b' -> ghosts.add(new Ghost(blueGhost,scaredGhost,x,y,24));
                    case 'r' -> ghosts.add(new Ghost(redGhost,scaredGhost,x,y,24));
                    case 'p' -> ghosts.add(new Ghost(pinkGhost,scaredGhost,x,y,24));
                    case 'o' -> ghosts.add(new Ghost(orangeGhost,scaredGhost,x,y,24));
                }
            }
        }
    }

    public Pacman getPacman(){ return pacman; }
}

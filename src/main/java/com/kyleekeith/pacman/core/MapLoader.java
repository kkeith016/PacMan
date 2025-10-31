package com.kyleekeith.pacman.core;

import com.kyleekeith.pacman.entities.*;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Set;

public class MapLoader {
    private Pacman pacman;

    private final Image wallImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wall.png"))).getImage();
    private final Image blueGhost = new ImageIcon(Objects.requireNonNull(getClass().getResource("/blueGhost.png"))).getImage();
    private final Image redGhost = new ImageIcon(Objects.requireNonNull(getClass().getResource("/redGhost.png"))).getImage();
    private final Image pinkGhost = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pinkGhost.png"))).getImage();
    private final Image orangeGhost = new ImageIcon(Objects.requireNonNull(getClass().getResource("/orangeGhost.png"))).getImage();
    private final Image scaredGhost = new ImageIcon(Objects.requireNonNull(getClass().getResource("/scaredGhost.png"))).getImage();
    private final Image pacUp = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacmanUp.png"))).getImage();
    private final Image pacDown = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacmanDown.png"))).getImage();
    private final Image pacLeft = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacmanLeft.png"))).getImage();
    private final Image pacRight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/pacmanRight.png"))).getImage();

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

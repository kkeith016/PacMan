package com.kyleekeith.pacman.core;

import java.awt.event.KeyEvent;
import java.util.Map;
import com.kyleekeith.pacman.entities.Pacman;
import com.kyleekeith.pacman.util.Constants;

public class GameInput {
    private final GameLogic logic;

    // Map key codes to directions
    private static final Map<Integer, Character> keyDirectionMap = Map.of(
            KeyEvent.VK_UP, 'U',
            KeyEvent.VK_DOWN, 'D',
            KeyEvent.VK_LEFT, 'L',
            KeyEvent.VK_RIGHT, 'R'
    );

    public GameInput(GameLogic logic) {
        this.logic = logic;
    }

    public void handleInput(KeyEvent e) {
        Pacman pac = logic.getPacman();
        Character direction = keyDirectionMap.get(e.getKeyCode());

        if (direction != null) {
            pac.setDirection(direction, Constants.SPEED);
        }
    }
}
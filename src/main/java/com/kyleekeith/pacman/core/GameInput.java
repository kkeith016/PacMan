package com.kyleekeith.pacman.core;

import java.awt.event.KeyEvent;
import com.kyleekeith.pacman.entities.Pacman;
import com.kyleekeith.pacman.util.Constants;

public class GameInput {
    private com.kyleekeith.pacman.core.GameLogic logic;

    public GameInput(GameLogic logic) {
        this.logic = logic;
    }

    public void handleInput(KeyEvent e) {
        Pacman pac = logic.getPacman();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> pac.setDirection('U', Constants.SPEED);
            case KeyEvent.VK_DOWN -> pac.setDirection('D', Constants.SPEED);
            case KeyEvent.VK_LEFT -> pac.setDirection('L', Constants.SPEED);
            case KeyEvent.VK_RIGHT -> pac.setDirection('R', Constants.SPEED);
        }
    }
}

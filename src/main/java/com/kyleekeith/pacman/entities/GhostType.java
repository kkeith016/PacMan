package com.kyleekeith.pacman.entities;

/**
 * Enum representing the different types of ghosts in Pac-Man.
 * Each ghost has a unique behavior:
 * - BLINKY: Direct chase
 * - PINKY: Ambushes 4 tiles ahead of Pac-Man
 * - INKY: Vector chase using Blinky and Pac-Man
 * - CLYDE: Alternates between chasing Pac-Man and retreating
 */
public enum GhostType {
    BLINKY,
    PINKY,
    INKY,
    CLYDE
}

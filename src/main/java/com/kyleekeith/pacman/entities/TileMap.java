package com.kyleekeith.pacman.entities;

public class TileMap {

    public static final String[] layout = {
            "XXXXXXXXXXXXXXXXXXXXXXXXXXXX",
            "X............XX............X",
            "X.X XX.XX XX.XX.XX XX.XX X.X",
            "X*X  X.XX XX.  .XX XX.X  X*X",
            "X.XX X.XX XX.XX.XX XX.X XX.X",
            "X.............P............X",
            "X.XXXX. X.X X X XX.XX.X XX.X",
            "X.XXXX. X.X X X XX.XX.X XX.X",
            "X...... X....XX....XX......X",
            "XXXXXX.XXXXX XX XXXXX.XXXXXX",
            "     X.XXXXX XX XXXXX.X     ",
            "     X.XX          XX.X     ",
            "     X.XX XXXXXXXX XX.X     ",
            "XXXXXX.XX X b o    X XX.XXXXXX",
            "          X o      X          ",
            "XXXXXX.XX X p r   X XX.XXXXXX",
            "     X.XX XXXXXXXX XX.X     ",
            "     X.XX          XX.X     ",
            "     X.XX XXXXXXXX XX.X     ",
            "XXXXXX.XX XXXXXXXX XX.XXXXXX",
            "X............XX............X",
            "X.XXXX.XXXXX.XX.XXXXX.XXXX.X",
            "X*XXXX.XXXXX.XX.XXXXX.XXXX*X",
            "X...XX................XX...X",
            "XXX.XX.XX.XXXXXXXX.XX.XX.XXX",
            "X......XX....XX....XX......X",
            "X.XXXXXXXXXX.XX.XXXXXXXXXX.X",
            "X..........................X",
            "XXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    };

    public static int getRows() { return layout.length; }

    public static int getCols() { return layout[0].length(); }
}

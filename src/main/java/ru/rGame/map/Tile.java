package ru.rGame.map;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.enums.TileType;

public class Tile {
    public static final Color WALL_COLOR = Color.GRAY;
    public static final Color FLOOR_COLOR = Color.DARKGRAY;
    private int x;
    private int y;
    private TileType type;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw () {
        switch (type) {
            case WALL: {
                RGame.getInstance().setCellValueEx(x+ RGame.SHIFT_X, y+RGame.SHIFT_Y, WALL_COLOR, "#",Color.BLACK);
                break;
            }
            case FLOOR: {
                RGame.getInstance().setCellValueEx(x+ RGame.SHIFT_X, y+ RGame.SHIFT_Y, FLOOR_COLOR, ".",Color.BLACK);
                break;
            }
            default: {
                RGame.getInstance().setCellValueEx(x+ RGame.SHIFT_X, y+ RGame.SHIFT_Y, WALL_COLOR, "",Color.BLACK);
            }
        }
    }

    public TileType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setType(TileType type) {
        this.type = type;
    }

}

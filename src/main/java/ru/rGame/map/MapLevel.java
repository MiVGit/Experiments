package ru.rGame.map;

import ru.rGame.RGame;
import ru.rGame.enums.TileType;

public class MapLevel {
    Tile[][] map = new Tile[RGame.FIELD_WIDTH][RGame.FIELD_HEIGHT];
    private int level;

    public void generate(){
        //стартовая генерация лабиринта
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                TileType type = TileType.WALL;
                map[i][j] = new Tile(i, j, type);
            }
        }
        iterator();
    }

    private void iterator () {
        //итерации генерации карты
        int x = map[0].length/2+RGame.random(8)-4;
        int y = map.length/2+RGame.random(8)-4;
        map[x][y].setType(TileType.FLOOR);
        int step = RGame.random(4)+1;
        int choice = RGame.random(3);
        for (int iterations = 0; iterations < 550; iterations++) {
            if (iterations%step==0) {
                step = RGame.random(4)+1;
                choice = RGame.random(3);
            }
            if (choice == 0 && y <2) {choice = 1;}
            if (choice == 1 && x >=map.length-2) {choice = 2;}
            if (choice == 2 && y >=map[0].length-2) {choice = 3;}
            if (choice == 3 && x <2) {choice = 0;}
            if (choice == 0 && y <2) {choice = 1;}
            switch (choice) {
                case 0: {
                    y=y-1;
                    break;
                }
                case 1: {
                    x=x+1;
                    break;
                }
                case 2: {
                    y++;
                    break;
                }
                case 3: {
                    x--;
                    break;
                }
            }
            map[x][y].setType(TileType.FLOOR);
        }
    }

    public void draw() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != null) {map[i][j].draw();}
            }
        }
    }

    public int getLevel() {
        return level;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}

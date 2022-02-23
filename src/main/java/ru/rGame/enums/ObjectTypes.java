package ru.rGame.enums;

import com.javarush.engine.cell.Color;

public enum ObjectTypes {
    PLAYER("@", Color.DARKGRAY),
    LADDER_DOWN("v", Color.ORANGE),
    LADDER_UP("^", Color.LIGHTGOLDENRODYELLOW),
    GATE("O", Color.CYAN),
    ARMOR("a",Color.YELLOW),
    WEAPON("/", Color.YELLOW),
    FOOD("o",Color.LIGHTGREEN),
    POTION("8",Color.LIGHTGREEN),
    LEFTOVER("b",Color.LIGHTGREEN),
    ANIMAL("W", Color.GREEN),
    HUMANOID("H", Color.GREEN),
    MONSTER("M", Color.GREEN),
    ARTIFACT("*", Color.LIGHTGOLDENRODYELLOW),
    BOSS("!",Color.BLUE);

    String shape;
    Color bg;

    ObjectTypes(String shape, Color bg) {
        this.shape = shape;
        this.bg = bg;
    }

    public String getShape () {
        return shape;
    }

    public Color getBg () {
        return bg;
    }


    @Override
    public String toString() {
        switch (this) {
            case PLAYER: {
                return "Игрок";
            }
            case HUMANOID: {
                return "Гуманоид";
            }
            case MONSTER: {
                return "Монстр";
            }
            case ANIMAL: {
                return "Животное";
            }
            case BOSS: {
                return "Босс";
            }
            default: {
                return "Прочее";
            }
        }
    }
}

package ru.rGame.menus;

import com.javarush.engine.cell.Color;

public class BattleLog extends Menu{
    private final int lines = 11;

    public BattleLog() {
        super(1, 14);
        setLinesCount(lines);
        menuTextColorInactive = Color.BLACK;
    }

    @Override
    public void use() {
    }

    @Override
    public void add(String name, String caption) {
        if (menuItems.size()>=lines) {
            menuItems.remove(0);
        }
        super.add("", caption);
    }
}

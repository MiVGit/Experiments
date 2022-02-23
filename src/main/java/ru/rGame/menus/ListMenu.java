package ru.rGame.menus;

import com.javarush.engine.cell.Color;
import ru.rGame.Escapeable;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.Screen;

public class ListMenu extends Menu implements Escapeable {
    private String title;

    public ListMenu(String title) {
        super(RGame.SCREEN_WIDTH-11, 12);
        this.title = title;
        menuTextColorInactive = Color.BLACK;
    }

    @Override
    public void draw() {
        super.draw();
        Screen.getInstance().label(getX(), getY()-1, Color.DARKGRAY,title, Color.BLACK);
    }

    @Override
    public void use() {
            LevelManager.getInstance().useListObject(this);
    }

    @Override
    public void deactivate() {
        setActive(false);
    }
}

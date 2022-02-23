package ru.rGame.menus;

import com.javarush.engine.cell.Color;
import ru.rGame.Escapeable;
import ru.rGame.RGame;

public class OkMenu extends Menu implements Escapeable {
    public OkMenu(int x, int y, String caption) {
        super(x, y);
        add("HM_OK", caption);
        menuColorChecked = Color.AQUA;
        setActive(true);
    }

    @Override
    public void use() {
        deactivate();
    }

    @Override
    public void deactivate() {
        RGame.getInstance().startScreen();
    }
}

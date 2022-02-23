package ru.rGame.objects;

import ru.rGame.Interactive;
import ru.rGame.LevelManager;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.menus.ConfirmMenu;

public class Gate extends GameObject implements Interactive {
    public Gate(ObjectTypes type, int x, int y, String caption, String info) {
        super(type, x, y);
        this.caption = caption;
        this.info = info;
    }

    @Override
    public void use() {
        ConfirmMenu confirmMenu;
        if (type == ObjectTypes.LADDER_DOWN) {confirmMenu = new ConfirmMenu(this, "Идти ниже?");}
        else if (type == ObjectTypes.LADDER_UP) {confirmMenu = new ConfirmMenu(this, "Идти выше?");}
        else if (type == ObjectTypes.GATE) {confirmMenu = new ConfirmMenu(this, "Выйти вон?");}
        else {confirmMenu = new ConfirmMenu(this, "Сделать хоть что-то?");}
    }

    @Override
    public void useC() {
            LevelManager.getInstance().enterGateC();
    }

    @Override
    public void select() {
        use();
    }

}

package ru.rGame.menus;

import com.javarush.engine.cell.Color;
import ru.rGame.Interactive;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.objects.GameObject;

public class ConfirmMenu extends  Menu{
    private String title;

    public ConfirmMenu(GameObject object, String title) {
        super(RGame.SCREEN_WIDTH-11, 7);
        add("CM_YES", "Да ");
        add("CM_NO", "Нет");
        this.title = title;
        RGame.getInstance().deactivateMenus();
        setActive(true);
        RGame.getInstance().addMenu(this);
        LevelManager.getInstance().setCurrentInteractionObject(object);
    }

    @Override
    public void draw() {
        super.draw();
        Screen.getInstance().label(getX(), getY()-1, Color.DARKGRAY,title, Color.BLACK);
    }

    @Override
    public void use() {
            switch (getActiveName()) {
                case "CM_YES": {
                    Interactive interactionObject = (Interactive) LevelManager.getInstance().getCurrentInteractionObject();
                    this.setActive(false);
                    RGame.getInstance().removeMenu(this);
                    interactionObject.useC();
                    break;
                }
                case "CM_NO": {
                    setActive(false);
                    RGame.getInstance().getMenus().remove(this);
                    break;
                }
            }
    }
}

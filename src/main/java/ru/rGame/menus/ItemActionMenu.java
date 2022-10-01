package ru.rGame.menus;

import ru.rGame.Escapeable;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.objects.ItemObject;
import ru.rGame.objects.Player;

public class ItemActionMenu extends Menu implements Escapeable {
    private final ItemObject itemObject;

    public ItemActionMenu(ItemObject itemObject) {
        super(RGame.SCREEN_WIDTH-11, 6);
        add("IM_USE", "Применить");
        add("IM_Grab", "Подобрать");
        add("IM_Back", "Назад");
        this.itemObject = itemObject;
        this.setActive(true);
    }

    @Override
    public void use() {
        if (getActiveName().equals("IM_Back")) {
            deactivate();
        }
        if (getActiveName().equals("IM_Grab")) {
            itemObject.grab(Player.getInstance());
            deactivate();
        }
        if (getActiveName().equals("IM_USE")) {
            itemObject.use();
            LevelManager.getInstance().makeTurn(null);
            deactivate();
        }
    }

    @Override
    public void deactivate() {
        this.setActive(false);
        RGame.getInstance().removeMenu(this);
    }

}

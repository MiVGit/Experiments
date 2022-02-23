package ru.rGame.menus;

import com.javarush.engine.cell.Key;
import ru.rGame.Escapeable;
import ru.rGame.InventoryManager;
import ru.rGame.RGame;

public class InventoryActionMenu extends Menu implements Escapeable {
    int inventoryMenuIndex;

    public InventoryActionMenu() {
        super(RGame.SCREEN_WIDTH/2+2, RGame.SCREEN_HEIGHT-7);
        add("IM_USE", "Применить");
        add("IM_Drop", "Выбросить");
        add("IM_Back", "Назад");
        InventoryManager.getInstance().showInfo(getMenuIndex());
    }

    public void setUseCaption(String useCaption){
        this.menuItems.set(0, new String[] {"IM_USE", useCaption});
    }

    @Override
    public void keyPressed(Key key) {
        super.keyPressed(key);
        InventoryManager.getInstance().showInfo(inventoryMenuIndex);
    }

    @Override
    public void use() {
        if (getActiveName().equals("IM_Back")) {
            deactivate();
        }
        if (getActiveName().equals("IM_Drop")) {
            InventoryManager.getInstance().dropItem(inventoryMenuIndex);
        }
        if (getActiveName().equals("IM_USE")) {
            InventoryManager.getInstance().useItem(inventoryMenuIndex);
        }
    }

    @Override
    public void deactivate() {
        for (Menu menu:RGame.getInstance().getMenus()) {
            if (menu instanceof InventoryMenu) {
                menu.setActive(true);
            }
        }
        this.setActive(false);
    }
}

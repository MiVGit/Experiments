package ru.rGame.menus;

import com.javarush.engine.cell.Key;
import ru.rGame.*;
import ru.rGame.objects.ItemObject;

public class InventoryMenu extends Menu implements Escapeable {
    private final Inventory inventory;

    public InventoryMenu(Inventory inventory) {
        super(3, 5);
        super.setLinesCount(8);
        this.inventory = inventory;
    }

    public void refresh() {
        this.clear();
        if (inventory.armor == null) {add("0", "Нет брони");}
        else {add("0", inventory.armor.getInfo()+",надето");}
        if (inventory.weapon == null) {add("1", "Нет оружия");}
        else {add("1", inventory.weapon.getInfo()+",в руках");}
        int i = 2;
            for (ItemObject inventoryItem : inventory.inventory) {
                add(String.valueOf(i), inventoryItem.getInfo());
                i++;
            }
        add(String.valueOf(i),"Закрыть инвентарь (esc)");
    }

    @Override
    public void use() {
        if (getMenuIndex() == menuItems.size()-1) {
            RGame.getInstance().closeInfoScreen();
        }
        else {
            String useCaption = "Применить";
            if (getMenuIndex() == 0 && inventory.armor != null) {useCaption = inventory.armor.getUseCaption();}
            else if (getMenuIndex() == 1 && inventory.weapon != null) {useCaption = inventory.weapon.getUseCaption();}
            else if (getMenuIndex() > 1) {useCaption = inventory.inventory.get(getMenuIndex()-2).getUseCaption();}
            if ((getMenuIndex() == 0 && inventory.armor != null) || (getMenuIndex() == 1 && inventory.weapon != null) || getMenuIndex() > 1) {
                for (Menu menu : RGame.getInstance().getMenus()) {
                    if (menu instanceof InventoryActionMenu) {
                        menu.setActive(true);
                        ((InventoryActionMenu) menu).inventoryMenuIndex = getMenuIndex();
                        ((InventoryActionMenu) menu).setUseCaption(useCaption);
                    }
                }
            setActive(false);
            }
        }
    }

    @Override
    public void keyPressed(Key key) {
        super.keyPressed(key);
        Screen.getInstance().clearBottom();
        InventoryManager.getInstance().showInfo(getMenuIndex());
    }

    @Override
    public void deactivate() {
        RGame.getInstance().closeInfoScreen();
    }
}

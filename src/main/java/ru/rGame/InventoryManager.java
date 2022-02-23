package ru.rGame;

import ru.rGame.enums.GameStates;
import ru.rGame.menus.InventoryActionMenu;
import ru.rGame.menus.InventoryMenu;
import ru.rGame.menus.Menu;
import ru.rGame.objects.Player;

public class InventoryManager {
    private static InventoryManager instance;
    private Inventory currentInventory;

    private InventoryManager() {}

    public void showInventory(Inventory inventory) {
        currentInventory = inventory;
        Screen.getInstance().draw(GameStates.INVENTORY);
        InventoryMenu menu = new InventoryMenu(inventory);
        menu.refresh();
        menu.setActive(true);
        RGame.getInstance().addMenu(menu);
        RGame.getInstance().addMenu(new InventoryActionMenu());
        showInfo(0);
    }

    public void showInfo(int itemIndex) {
        Screen.getInstance().draw(GameStates.INVENTORY);
        if (itemIndex == 0 && currentInventory.armor != null) {
            currentInventory.armor.showInfo();
        }
        else if (itemIndex == 1 && currentInventory.weapon != null) {
            currentInventory.weapon.showInfo();
        }
        else if (itemIndex>1&&itemIndex<currentInventory.inventory.size()+2) {
            currentInventory.inventory.get(itemIndex-2).showInfo();
        }
    }

    public void dropItem(int itemIndex) {
        currentInventory.drop(Player.getInstance(), itemIndex);
        refreshInventory();
    }

    public void useItem(int itemIndex) {
        if (itemIndex == 0) {currentInventory.armor.useC();}
        else if (itemIndex == 1) {currentInventory.weapon.useC();}
        else {currentInventory.inventory.get(itemIndex-2).use();}
    }

    public void refreshInventory () {
        if (RGame.getInstance().state == GameStates.INVENTORY) {
            Screen.getInstance().clear();
            Menu menu;
            for (int i = 0; i < RGame.getInstance().getMenus().size(); i++) {
                menu = RGame.getInstance().getMenus().get(i);
                if (menu instanceof InventoryActionMenu) {
                    menu.setActive(false);
                }
                if (menu instanceof InventoryMenu) {
                    RGame.getInstance().removeMenu(menu);
                    i--;
                }
            }
            currentInventory = Player.getInstance().getInventory();
            menu = new InventoryMenu(currentInventory);
            ((InventoryMenu) menu).refresh();
            menu.setActive(true);
            RGame.getInstance().addMenu(menu);
            showInfo(0);
        }
        else if (RGame.getInstance().state == GameStates.MAIN) {
        LevelManager.getInstance().makeTurn(null);
        }
    }

    public static InventoryManager getInstance() {
        if (instance == null) {instance = new InventoryManager();}
        return instance;
    }

}

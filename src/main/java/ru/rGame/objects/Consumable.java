package ru.rGame.objects;

import ru.rGame.InventoryManager;
import ru.rGame.LevelManager;
import ru.rGame.enums.ObjectTypes;

public abstract class Consumable extends ItemObject{

    public Consumable(ObjectTypes type, int level, int x, int y) {
        super(type, level, x, y);
    }

    @Override
    public void useC() {
        LevelManager.getInstance().removeGameObject(this);
        Player.getInstance().inventory.inventory.remove(this);
        castEffect();
        InventoryManager.getInstance().refreshInventory();
    }

    protected abstract void castEffect();

}

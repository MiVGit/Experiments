package ru.rGame.objects;

import ru.rGame.Interactive;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.menus.ItemActionMenu;

public abstract class ItemObject extends GameObject implements Interactive {
    protected int level;
    String useCaption = "Применить";

    public ItemObject(ObjectTypes type, int level, int x, int y) {
        super(type, x, y);
        this.level = level;
    }

    public abstract void showInfo();

    public void grab(Person person) {
        //if (LevelManager.getInstance().getItemObjects().contains(this)) { //Подбираем, если среди игровых объектов есть такой предмет
            if (person.getInventory().inventory.size() >= person.getInventory().getSize()) {
                if (person.getInventory().inventory.get(person.getInventory().getSize() - 1) != null) {
                    person.getInventory().drop(person, person.getInventory().getSize() + 1);//в индекс метода drop включены дополнительно две позиции по сравнению с индексом инвентаря: 0 - броня, 1 - оружие
                    RGame.getInstance().setMessage(RGame.SCREEN_WIDTH - 26, RGame.SCREEN_HEIGHT - 1, "места нет. выбросил лишнее");
                }
            }
            person.getInventory().inventory.add(0, this);//подбираем в сумку инвентаря
            LevelManager.getInstance().removeGameObject(this);
            LevelManager.getInstance().makeTurn(null);
        //}
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void use() {
        useC();
    }

    public void select() {
        RGame.getInstance().deactivateMenus();
        RGame.getInstance().addMenu(new ItemActionMenu(this));
    }

    public String getUseCaption() {
        return useCaption;
    }
}

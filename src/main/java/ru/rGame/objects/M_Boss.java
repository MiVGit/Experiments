package ru.rGame.objects;

import ru.rGame.Balanser;
import ru.rGame.enums.ObjectTypes;
import java.util.ArrayList;
import java.util.Collections;

public class M_Boss extends WildLife{
    public M_Boss(int x, int y) {
        super(ObjectTypes.BOSS, Balanser.mapLevels, x, y);
    }

    @Override
    protected void generateInventory(int level) {
        inventory.armor = new Armor(ObjectTypes.BOSS, level+2, 0, 0);
        inventory.weapon = new Weapon(ObjectTypes.BOSS, level+2, 0, 0);
        inventory.inventory.add(new Potion(level+5, 0, 0));
        inventory.inventory.add(new Food(level+5, 0, 0));
        inventory.inventory.add(Artifact.getInstance());
    }

    @Override
    protected void getChances() {
    }

    @Override
    ArrayList<Monster> getMonsterList() {
        ArrayList<Monster> boss = new ArrayList<>();
        Collections.addAll(boss,
                new Monster("Босс","Хозяин подземелья",2, 0, 2, "Останки", "Поверженный хозяин подземелья", -100));
        return boss;
    }

}

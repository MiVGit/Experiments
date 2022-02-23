package ru.rGame.objects;

import ru.rGame.enums.ObjectTypes;
import java.util.ArrayList;
import java.util.Collections;

public class M_Animal extends WildLife{

    public M_Animal(int level, int x, int y) {
        super(ObjectTypes.ANIMAL, level, x, y);
    }

    @Override
    protected void getChances() {
        armorChance = 20;
        weaponChance = 10;
        foodChance = 0;
        potionChance = 0;
    }

    @Override
    ArrayList<Monster> getMonsterList() {
        ArrayList<Monster> animals = new ArrayList<>();
        Collections.addAll(animals,
                new Monster("Барашек","Небольшой баран, белый",0, 0, 0, "Тушка", "Тушка барашка", 2),
                new Monster("Змейка","Маленькая ядовитая змейка",0, 0, 1, "Остатки", "Остатки змейки", 1),
                new Monster("Лютоволк","Волк размером с молодого бычка",2, 0, 2, "Туша", "Туша лютоволка", 1));
        return animals;
    }

}

package ru.rGame.objects;

import ru.rGame.enums.ObjectTypes;
import java.util.ArrayList;
import java.util.Collections;

public class M_Monster extends WildLife {
    public M_Monster(int level, int x, int y) {
        super(ObjectTypes.MONSTER, level, x, y);
    }

    @Override
    protected void getChances() {
        armorChance = 90;
        weaponChance = 65;
        foodChance = 50;
        potionChance = 5;
    }

    @Override
    ArrayList<WildLife.Monster> getMonsterList() {
        ArrayList<WildLife.Monster> animals = new ArrayList<>();
        Collections.addAll(animals,
                new Monster("Полудракон","Полудракон злобно сверкает глазами",2, 1, 1, "Останки", "Дракон, хоть и полу", -5),
                new Monster("Пони хаоса","Маленькая чёрная милая пони",1, 0, 2, "Тушка", "Убиенный пони", 2),
                new Monster("Слизь","Живая желеобразная слизь",2, 1, 0, "Лужица", "Лужица остывшей слизи", -4),
                new Monster("Сарпончо","Кто бы знал, что это такое",2, 0, 2, "Остатки", "Остатки Сарпончо", -3));
        return animals;
    }
}

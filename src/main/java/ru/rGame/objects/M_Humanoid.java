package ru.rGame.objects;

import ru.rGame.enums.ObjectTypes;
import java.util.ArrayList;
import java.util.Collections;

public class M_Humanoid  extends  WildLife {
    public M_Humanoid(int level, int x, int y) {
        super(ObjectTypes.HUMANOID, level, x, y);
    }

    @Override
    protected void getChances() {
        armorChance = 90;
        weaponChance = 65;
        foodChance = 50;
        potionChance = 5;
    }

    @Override
    ArrayList<Monster> getMonsterList() {
        ArrayList<Monster> animals = new ArrayList<>();
        Collections.addAll(animals,
                new Monster("Орк","Орк большой сердитый зелёный",2, 0, 1, "Тело", "Бывший орк", -2),
                new Monster("Огр","Огр злой, но тупой",2, 0, 0, "Тело", "Здесь был огр", -3),
                new Monster("Дексян","Жадный, большой, жёлтый дексян",2, 1, 1, "Остатки", "Остатки дексяна", -1),
                new Monster("Гоблин","Трусливый гоблин",0, 1, 2, "Тело", "Тело гоблина", -2),
                new Monster("Мертвец","Ожившее тело",2, 0, 2, "Тело", "Труп мертвеца", -3),
                new Monster("Гоблин","Маленький коварный жадный гоблин",2, 1, 1, "Тело", "Тело гоблина", -2));
        return animals;
    }
}

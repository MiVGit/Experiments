package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.enums.Stats;
import ru.rGame.menus.ConfirmMenu;

public class Potion extends Consumable{
    private int type;
    private final String[][] TYPES = {
            {"Зелье (S)","Зелье силы"},
            {"Зелье (A)","Зелье ловкости"},
            {"Зелье (V)","Зелье выносливости"},
            {"Зелье (HP-)","Слабое зелье лечения"},
            {"Зелье (HP)","Среднее зелье лечения"},
            {"Зелье (HP+)","Крепкое зелье лечения"},
            {"Зелье (s)","Зелье слабины"},
    };

    public Potion(int level, int x, int y) {
        super(ObjectTypes.POTION, level, x, y);
        generate();
    }

    @Override
    protected void castEffect() {
        switch (type) {
            case 0: {
                Player.getInstance().addStat(Stats.STRENGTH, 1);
                break;
            }
            case 1: {
                Player.getInstance().addStat(Stats.DEXTERITY, 1);
                break;
            }
            case 2: {
                Player.getInstance().addStat(Stats.VITALITY, 1);
                break;
            }
            case 3: {
                Player.getInstance().addHP((int)Math.floor(Player.getInstance().health[0]*0.2));
                break;
            }
            case 4: {
                Player.getInstance().addHP((int)Math.floor(Player.getInstance().health[0]*0.5));
                break;
            }
            case 5: {
                Player.getInstance().addHP(Player.getInstance().health[0]);
                break;
            }
            case 6: {
                Player.getInstance().addStat(Stats.STRENGTH, -1);
                break;
            }
        }
    }

    @Override
    public void use() {
        ConfirmMenu confirmMenu = new ConfirmMenu(this, "Выпить?");
    }

    @Override
    public void showInfo() {
        int p = RGame.SCREEN_HEIGHT-6;
        Screen.getInstance().label(3, p, Color.DARKGRAY,"Выпей меня",Color.BLACK);
    }

    private void generate() {
        type = RGame.random(TYPES.length-1);
        caption = TYPES[type][0];
        info = TYPES[type][1];
    }

}

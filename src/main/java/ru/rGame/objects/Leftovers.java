package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.menus.ConfirmMenu;

public class Leftovers extends Consumable{
    int nutrition;

    public Leftovers(int level, int x, int y, String caption, String info, int nutrition) {
        super(ObjectTypes.LEFTOVER, level, x, y);
        this.caption = caption;
        this.info = info;
        this.nutrition = nutrition;
    }

    @Override
    protected void castEffect() {
        Player.getInstance().addHP(nutrition*level);
        if (nutrition>0) {RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-15,RGame.SCREEN_HEIGHT-1,"Ел я и похуже");}
        else {RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-12,RGame.SCREEN_HEIGHT-1,"Ихтиандр!!!");}
    }

    @Override
    public void use() {
        ConfirmMenu confirmMenu = new ConfirmMenu(this, "Съесть?");
    }

    @Override
    public void showInfo() {
        int p = RGame.SCREEN_HEIGHT-10;
        Screen.getInstance().label(3, p, Color.DARKGRAY,caption,Color.BLACK);
        if (nutrition>0) {
            Screen.getInstance().label(3, p+=2, Color.DARKGRAY,"Кажется,",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"это можно",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"съесть...",Color.BLACK);
        }
        else if (nutrition > -80) {
            Screen.getInstance().label(3, p+=2, Color.DARKGRAY,"Выглядит",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"совершенно",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"несъедобно",Color.BLACK);
        }
        else {
            Screen.getInstance().label(3, p+=2, Color.DARKGRAY,"Выглядит",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"опасно.",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"не стоит",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"это есть.",Color.BLACK);
        }
    }

}

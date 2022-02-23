package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.enums.ObjectTypes;

public class Artifact extends ItemObject{
    private static Artifact instance;

    private Artifact() {
        super(ObjectTypes.ARTIFACT, 1, 0, 0);
        caption = "Артефакт";
        info = "Могущественный артефакт";
    }

    @Override
    public void showInfo() {
        int p = RGame.SCREEN_HEIGHT-10;
        Screen.getInstance().label(3, p, Color.DARKGRAY,"Загадочный",Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"артефакт.",Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"По слухам",Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"увеличивает",Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"могущество",Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"владельца.",Color.BLACK);
    }

    @Override
    public void useC() {
        RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-23, RGame.SCREEN_HEIGHT-1,"Мощь переполняет меня!");
    }

    public static Artifact getInstance() {
        if (instance == null) {instance = new Artifact();}
        return instance;
    }
}

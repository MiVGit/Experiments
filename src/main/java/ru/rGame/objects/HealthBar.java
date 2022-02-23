package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.Screen;

public class HealthBar {
    String caption="";
    int x=RGame.SCREEN_WIDTH, y= 0;
    Color color;

    public void draw() {
        caption=getCaption();
        x = RGame.FIELD_WIDTH-caption.length()+3;
        Screen.getInstance().label(x, y, Color.ALICEBLUE, caption, color);
    }

    public void draw(int x, int y) {
        caption=getCaption();
        Screen.getInstance().label(x, y, Color.ALICEBLUE, caption, color);
    }

    private String getCaption() {
        int healthLeft = (int) Math.floor(((float) Player.getInstance().getHealth()[1])/((float) Player.getInstance().getHealth()[0])*100);
        if (healthLeft==100) {
            color = Color.DARKGREEN;
            caption = "Я только начал!";
        }
        else if (healthLeft>=90) {
            color = Color.GREEN;
            caption = "Здоров как бык!";
        }
        else if (healthLeft>=75) {
            color = Color.DARKGOLDENROD;
            caption = "Бодрячком!";
        }
        else if (healthLeft>=50) {
            color = Color.DARKGOLDENROD;
            caption = "Держусь!";
        }
        else if (healthLeft>=30) {
            color = Color.DARKMAGENTA;
            caption = "Так, царапина...";
        }
        else if (healthLeft>=15) {
            color = Color.RED;
            caption = "Что-то мне нехорошо...";
        }
        else {
            color = Color.RED;
            caption = "Пойду, полежу";
        }
        return caption;
    }

}

package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.enums.ObjectTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Food extends Consumable{
    private int nutrition;
    private final List<Foods> foods = new ArrayList<>();

    public Food(int level, int x, int y) {
        super(ObjectTypes.FOOD, level, x, y);
        generateFood();
    }

    private void generateFood (){
        Collections.addAll(foods,
                new Foods("Лапша","Лапша быстрого приготовления",10),
                new Foods("Сухарик","Засохший кусок хлеба",10),
                new Foods("Яблоко","Сочное спелое садовое яблоко",20),
                new Foods("Окорочок","Куриный окорочок",20),
                new Foods("Обед","Первое, второе и компот",30),
                new Foods("Борщ","Борщ с мясом и капустой",30)
                );
        int r = RGame.random(foods.size()-1);
        this.caption=foods.get(r).caption;
        this.info=foods.get(r).info;
        this.nutrition = foods.get(r).nutrition;
    }

    @Override
    protected void castEffect() {
        Player.getInstance().addHP(nutrition*level);
    }

    @Override
    public void showInfo() {
        int p = RGame.SCREEN_HEIGHT-11;
        Screen.getInstance().label(3, p, Color.DARKGRAY,caption,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Уровень "+level,Color.BLACK);
        if (nutrition<10) {
            Screen.getInstance().label(3, p+=2, Color.DARKGRAY,"Можно",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"перехватить,",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"если желудок",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"не жалко",Color.BLACK);
        }
        else if (nutrition<20) {
            Screen.getInstance().label(3, p+=2, Color.DARKGRAY,"Увеличивает",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"здоровье",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"и живот",Color.BLACK);
        }
        else {
            Screen.getInstance().label(3, p+=2, Color.DARKGRAY,"Вкусно,",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"сытно",Color.BLACK);
            Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"и полезно",Color.BLACK);
        }
    }

    private class Foods {
        String caption;
        String info;
        int nutrition;
        public Foods(String caption, String info, int nutrition) {
            this.caption = caption;
            this.info = info;
            this.nutrition = nutrition;
        }
    }

}

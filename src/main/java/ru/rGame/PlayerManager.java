package ru.rGame;

import com.javarush.engine.cell.Color;
import ru.rGame.enums.GameStates;
import ru.rGame.menus.StatsMenu;
import ru.rGame.objects.Player;

public class PlayerManager {
    private static PlayerManager instance;

    private PlayerManager() {}

    public static PlayerManager getInstance() {
        if (instance == null) {instance = new PlayerManager();}
        return instance;
    }

    public void showPlayerScreen() {
        Screen.getInstance().draw(GameStates.LEVELUP);
        StatsMenu menu = new StatsMenu();
        menu.setActive(true);
        RGame.getInstance().addMenu(menu);
        showInfo();
    }

    public void showInfo() {
        String weapon = "пусто";
        String armor = "собственная кожа";
        if (Player.getInstance().getInventory().weapon != null) {weapon = Player.getInstance().getInventory().weapon.getInfo();}
        if (Player.getInstance().getInventory().armor != null) {armor = Player.getInstance().getInventory().armor.getInfo();}
        Screen.getInstance().label(3, 4, Color.ALICEBLUE, "Вот игрок.",Color.BLACK);
        Screen.getInstance().label(3, 5, Color.ALICEBLUE, "Уровень игрока "+ Player.getInstance().getLevel(),Color.BLACK);
        Screen.getInstance().label(3, 7, Color.ALICEBLUE, "В руках", Color.BLACK);
        Screen.getInstance().label(3, 8, Color.ALICEBLUE, weapon, Color.BLACK);
        Screen.getInstance().label(3, 10, Color.ALICEBLUE, "Для защиты используется", Color.BLACK);
        Screen.getInstance().label(3, 11, Color.ALICEBLUE, armor, Color.BLACK);
        Screen.getInstance().label(3, 13, Color.ALICEBLUE, "Раны:", Color.BLACK);
        LevelManager.getInstance().getHealthBar().draw(3, 14);

    }


}

package ru.rGame.menus;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.enums.MainMenuItems;

public class MainMenu extends Menu {

    public MainMenu() {
        super(RGame.SCREEN_WIDTH/2- MainMenuItems.values()[0].getMenuItem()[0].length(), RGame.SCREEN_HEIGHT/2-2);
        menuColor = Color.ALICEBLUE;
        for (int i = 0; i < MainMenuItems.values().length; i++) {
            add(MainMenuItems.values()[i].getMenuItem()[0],MainMenuItems.values()[i].getMenuItem()[1]);
        }
    }

    @Override
    public void use() {
        switch (getActiveName()) {
            case "MM_Exit": {javafx.application.Platform.exit(); break;}
            case "MM_Start": {
                RGame.getInstance().startNewGame();
                break;
            }
            case "MM_Info": {
                RGame.getInstance().showAbout();
                break;
            }
        }
    }
}

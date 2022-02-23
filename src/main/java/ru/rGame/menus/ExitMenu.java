package ru.rGame.menus;

import ru.rGame.Escapeable;
import ru.rGame.RGame;

public class ExitMenu extends Menu implements Escapeable {

    public ExitMenu() {
        super(RGame.SCREEN_WIDTH-11, 1);
        add("EM_Inventory", "Инвентарь");
        add("EM_Persona", "Персонаж ");
        add("EM_Exit", "Выход    ");
        //add("EM_Exit", "Тест     ");
        add("EM_Back", "Назад    ");
    }

    @Override
    public void use() {
            switch (getActiveName()) {
                case "EM_Exit": {
                    RGame.getInstance().startScreen();
                    break;
                }
                case "EM_Back": {
                    setActive(false);
                    break;
                }
                case "EM_Inventory": {
                    RGame.getInstance().inventoryScreen();
                    break;
                }
                case "EM_Persona": {
                    RGame.getInstance().playerScreen();
                    break;
                }
            }
    }

    @Override
    public void deactivate() {
        setActive(false);
    }
}

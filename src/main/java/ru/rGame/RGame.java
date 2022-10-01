package ru.rGame;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import ru.rGame.enums.GameStates;
import ru.rGame.menus.Menu;
import ru.rGame.menus.OkMenu;
import ru.rGame.objects.GameObject;
import ru.rGame.objects.Message;
import ru.rGame.objects.Person;
import ru.rGame.objects.Player;

import java.util.ArrayList;
import java.util.List;


public class RGame extends Game {
    public static final int SCREEN_WIDTH=40;
    public static final int SCREEN_HEIGHT=26;
    public static final int LEFT_FIELD = 1;
    public static final int RIGHT_FIELD = 12;
    public static final int UD_FIELD = 1;
    public static final int FIELD_WIDTH=SCREEN_WIDTH-LEFT_FIELD-RIGHT_FIELD-2;
    public static final int FIELD_HEIGHT=SCREEN_HEIGHT-UD_FIELD-UD_FIELD-2;
    public static final int SHIFT_X = LEFT_FIELD+1;
    public static final int SHIFT_Y = UD_FIELD +1;
    public static final int LAST_LEVEL = 10;
    private int turn;
    private boolean bossDefeated = false;
    private boolean keyPressed = false;
    private static RGame instance;
    private final Message message = new Message();

    GameStates state;
    List<Menu> menus = new ArrayList<>();
    Screen screen = Screen.getInstance();
    LevelManager levelManager = LevelManager.getInstance();
    InventoryManager inventoryManager = InventoryManager.getInstance();
    PlayerManager playerManager = PlayerManager.getInstance();
    BattleManager battleManager = BattleManager.getInstance();

    public void startScreen () {
        state = GameStates.START;
        clearMenus();
        menus.add(screen.mainMenu);
        screen.mainMenu.setActive(true);
        refreshGameScreen();
    }

    public void startNewGame() {
        state = GameStates.MAIN;
        clearMenus();
        menus.add(screen.exitMenu);
        refreshGameScreen();
        levelManager.newGame();
        levelManager.draw();
    }

    public void showAbout() {
        state = GameStates.ABOUT;
        clearMenus();
        menus.add(new OkMenu(RGame.SCREEN_WIDTH/2-6, RGame.SCREEN_HEIGHT-5, "Ясно, понятно"));
        refreshGameScreen();
    }

    public void inventoryScreen() {
        state = GameStates.INVENTORY;
        clearMenus();
        screen.clear();
        inventoryManager.showInventory(levelManager.player.getInventory());
    }

    public void playerScreen() {
        state = GameStates.LEVELUP;
        clearMenus();
        screen.clear();
        playerManager.showPlayerScreen();
    }

    public void battleScreen(Person opponent) {
        state = GameStates.BATTLE;
        clearMenus();
        screen.clear();
        battleManager.showBattleScreen(opponent);
    }

    public void gameOver() {
        state = GameStates.GAMEOVER;
        clearMenus();
        menus.add(new OkMenu(RGame.SCREEN_WIDTH/2-9, RGame.SCREEN_HEIGHT-3,"Это ещё  не конец!"));
        refreshGameScreen();
    }

    public void finishGame () {
        state = GameStates.VICTORY;
        clearMenus();
        menus.add(new OkMenu(2, RGame.SCREEN_HEIGHT-3,"Завершить приключение"));
        refreshGameScreen();
    }

    public void closeInfoScreen() {
        state = GameStates.MAIN;
        clearMenus();
        menus.add(screen.exitMenu);
        refreshGameScreen();
        levelManager.makeTurn(null);
    }

    private void refreshGameScreen() {
        screen.clear();
        screen.draw(state);
        drawMenus();
    }

    public void fight(Person opponent) {
        //Переход в состояние битвы
        state = GameStates.BATTLE;
        battleScreen(opponent);
    }

    @Override
    public void initialize() {
        instance = this;
        showGrid(false);
        setScreenSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTurnTimer(100);
        startScreen();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x>=0&&x<SCREEN_WIDTH&&y>=0&&y<SCREEN_HEIGHT) {
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void setCellValue(int x, int y, String value) {
        if (x>=0&&x<SCREEN_WIDTH&&y>=0&&y<SCREEN_HEIGHT) {
            super.setCellValue(x, y, value);
        }
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value, Color textColor, int textSize) {
        if (x>=0&&x<SCREEN_WIDTH&&y>=0&&y<SCREEN_HEIGHT) {
            super.setCellValueEx(x, y, cellColor, value, textColor, textSize);
        }
    }

    public void setFieldCellColor(int x, int y, Color color) {
        if (x>=LEFT_FIELD&&x<SCREEN_WIDTH-RIGHT_FIELD&&y>=UD_FIELD&&y<SCREEN_HEIGHT-UD_FIELD) {
            super.setCellColor(x, y, color);
        }
    }

    @Override
    public void onKeyPress(Key key) {
        //Ищем активное меню
        Menu activeMenu = null;
        for (Menu menu:menus) {
            if (menu.isActive()) {activeMenu = menu;}
        }

        //Обработка нажатия клавиш
        if (!keyPressed) {
            screen.clear();
            screen.draw(state);
        //Если есть активные меню, обрабатываем нажатие клавиш для них
            if (activeMenu != null) {
                activeMenu.keyPressed(key);
                if (key == Key.SPACE||key==Key.ENTER) {
                    activeMenu.use();
                }
                if (key == Key.ESCAPE && (activeMenu instanceof Escapeable)) {((Escapeable)activeMenu).deactivate();}
            }
        //Иначе обрабатываем нажатие клавиш для основной игры
            else {
                switch (key) {
                    case ESCAPE: {
                        if (state == GameStates.MAIN) {
                            screen.exitMenu.setActive(true);
                            break;
                        }
                    }
                    case SPACE: {
                        levelManager.makeTurn(key);
                        break;
                    }
                    default: {
                        levelManager.makeTurn(key);
                    }
                }
            }
        //Завершение обработки нажатия клавиш
            if (state == GameStates.MAIN) {levelManager.draw();}
            drawMenus();
            showMessage();
            if (Player.getInstance().getLvlUpPoints()>0) {screen.label(RGame.SCREEN_WIDTH-11,0,Color.ALICEBLUE,"Уровень!",Color.BLACK);}
            message.setMessage("");
            keyPressed = true;
        }
    }

    public void showMessage() {
        if (!message.getMessage().equals("")) {screen.clearBottom();}
        Screen.getInstance().label(message.getX(), message.getY(), Color.ALICEBLUE, message.getMessage(), Color.BLACK);
    }

    @Override
    public void onKeyReleased(Key key) {
        keyPressed = false;
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        List<GameObject> objectsInTile = LevelManager.getInstance().objectsInTile(x-LEFT_FIELD-1, y-UD_FIELD-1);
        if (objectsInTile.size()>0) {
            LevelManager.getInstance().showAbout(objectsInTile.get(objectsInTile.size()-1));
        }
    }

    @Override
    public void onTurn(int step) {
        keyPressed = false;
    }

    private void drawMenus() {
        for (Menu menu:menus) {menu.draw();}
    }

    public static int random(int max) {
        return (int) Math.floor(Math.random()*++max);
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
    }
    public void removeMenu(Menu menu) {
        menus.remove(menu);
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isBossDefeated() {
        return bossDefeated;
    }

    public void setBossDefeated() {
        bossDefeated = true;
    }

    private void clearMenus() {
        deactivateMenus();
        menus.clear();
    }

    public void deactivateMenus() {
        for (Menu menu:menus) {
            menu.setActive(false);
        }
    }

    public static RGame getInstance() {
        return instance;
    }

    public void setMessage(int x, int y, String message) {
        this.message.setX(x);
        this.message.setY(y);
        this.message.setMessage(message);
    }

    public void nextTurn() {
        turn++;
    }

}

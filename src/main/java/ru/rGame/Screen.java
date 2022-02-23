package ru.rGame;

import com.javarush.engine.cell.Color;
import ru.rGame.enums.GameStates;
import ru.rGame.menus.ExitMenu;
import ru.rGame.menus.MainMenu;
import ru.rGame.objects.Artifact;
import ru.rGame.objects.Player;

public class Screen {
    public final Color LABEL_BG = Color.ALICEBLUE;
    public final Color LABEL_TEXT_COLOR = Color.BLACK;
    public final String START_TEXT = "RGame!";
    public final String CELLAR_TEXT = "2021";
    private static Screen instance;

    MainMenu mainMenu = new MainMenu();
    ExitMenu exitMenu = new ExitMenu();

    private Screen() {}

    public void draw (GameStates state) {
        switch (state) {
            case START: {
                drawStartScreen();
                break;
            }
            case MAIN: {
                drawGameScreen();
                break;
            }
            case INVENTORY: {
                drawInventoryScreen();
                break;
            }
            case LEVELUP: {
                drawPlayerScreen();
                break;
            }
            case ABOUT: {
                drawHelpScreen();
                break;
            }
            case BATTLE: {
                drawBattleScreen();
                break;
            }
            case GAMEOVER: {
                drawGameOverScreen();
                break;
            }
            case VICTORY: {
                if (Player.getInstance().getInventory().inventory.contains(Artifact.getInstance())) {showVictoryScreen();}
                else {showNonVictoryScreen();}
                break;
            }
            default: {
                break;
            }
        }
    }

    private void drawStartScreen () {
        for (int i = 0; i < RGame.SCREEN_HEIGHT; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GREY);
            }
        }
        label(RGame.SCREEN_WIDTH/2-START_TEXT.length()/2,RGame.UD_FIELD*2, LABEL_BG, START_TEXT, LABEL_TEXT_COLOR);
        label(RGame.SCREEN_WIDTH/2-CELLAR_TEXT.length()/2,RGame.SCREEN_HEIGHT-RGame.UD_FIELD*2, Color.GRAY, CELLAR_TEXT, LABEL_TEXT_COLOR);
    }

    private void drawGameScreen() {
        for (int i = 0; i < RGame.SCREEN_HEIGHT; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GREY);
            }
        }
        for (int i = RGame.LEFT_FIELD; i < RGame.SCREEN_WIDTH-RGame.RIGHT_FIELD; i++) {
            RGame.getInstance().setCellColor(i, RGame.UD_FIELD, Color.BLUE);
            RGame.getInstance().setCellColor(i, RGame.SCREEN_HEIGHT-RGame.UD_FIELD-1, Color.BLUE);
        }
        for (int i = RGame.UD_FIELD; i < RGame.SCREEN_HEIGHT-RGame.UD_FIELD; i++) {
            RGame.getInstance().setCellColor(RGame.LEFT_FIELD, i, Color.BLUE);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-RGame.RIGHT_FIELD-1, i, Color.BLUE);
        }
    }

    private void drawInventoryScreen() {
        for (int i = 1; i < RGame.SCREEN_HEIGHT-1; i++) {
            for (int j = 1; j < RGame.SCREEN_WIDTH-1; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GREY);
            }
        }
        for (int i = 2; i < RGame.SCREEN_WIDTH-2; i=i+2) {
            RGame.getInstance().setCellColor(i, 1, Color.ALICEBLUE);
            RGame.getInstance().setCellColor(i+1, 2, Color.ALICEBLUE);
            RGame.getInstance().setCellColor(i, RGame.SCREEN_HEIGHT-2, Color.ALICEBLUE);
            RGame.getInstance().setCellColor(i+1, RGame.SCREEN_HEIGHT-3, Color.ALICEBLUE);
        }
        for (int j = 2; j < RGame.SCREEN_HEIGHT-2; j=j+2) {
            RGame.getInstance().setCellColor(1, j, Color.ALICEBLUE);
            RGame.getInstance().setCellColor(2, j+1, Color.ALICEBLUE);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-2, j, Color.ALICEBLUE);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-3, j+1, Color.ALICEBLUE);
        }
        label(RGame.SCREEN_WIDTH/2-5, 2, Color.ALICEBLUE, "Инвентарь", Color.BLACK);
    }

    private void drawPlayerScreen() {
        for (int i = 0; i < RGame.SCREEN_HEIGHT; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GREY);
            }
        }
        for (int i = 1; i < RGame.SCREEN_WIDTH-2; i++) {
            RGame.getInstance().setCellColor(i, 1, Color.LIGHTGOLDENRODYELLOW);
            RGame.getInstance().setCellColor(i, RGame.SCREEN_HEIGHT-2, Color.LIGHTGOLDENRODYELLOW);
        }
        for (int j = 1; j < RGame.SCREEN_HEIGHT-1; j++) {
            RGame.getInstance().setCellColor(1, j, Color.LIGHTGOLDENRODYELLOW);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-2, j, Color.LIGHTGOLDENRODYELLOW);
        }
        label(RGame.SCREEN_WIDTH/2-4, 2, Color.LIGHTGOLDENRODYELLOW, "Персонаж", Color.BLACK);
    }

    private void drawHelpScreen() {
        for (int i = 2; i < RGame.SCREEN_HEIGHT-2; i++) {
            for (int j = 2; j < RGame.SCREEN_WIDTH-2; j++) {
                RGame.getInstance().setCellColor(j, i, Color.DARKGRAY);
            }
        }
        for (int i = 1; i < 6; i++) {
            RGame.getInstance().setCellColor(i, 1, Color.AQUA);
            RGame.getInstance().setCellColor(i, RGame.SCREEN_HEIGHT-2, Color.AQUA);
        }
        for (int i = RGame.SCREEN_WIDTH-6; i < RGame.SCREEN_WIDTH-1; i++) {
            RGame.getInstance().setCellColor(i, 1, Color.AQUA);
            RGame.getInstance().setCellColor(i, RGame.SCREEN_HEIGHT-2, Color.AQUA);
        }
        for (int j = 2; j < 5; j++) {
            RGame.getInstance().setCellColor(1, j, Color.AQUA);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-2, j, Color.AQUA);
        }
        for (int j = RGame.SCREEN_HEIGHT-5; j < RGame.SCREEN_HEIGHT-2; j++) {
            RGame.getInstance().setCellColor(1, j, Color.AQUA);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-2, j, Color.AQUA);
        }
        int p = 3;
        label(3, p, Color.ALICEBLUE, "Мой первый рогалик.", Color.BLACK);
        label(3, p+=2, Color.ALICEBLUE, "Управление:", Color.BLACK);
        label(3, p+=2, Color.ALICEBLUE, "Стрелки - перемещение", Color.BLACK);
        label(3, p+=1, Color.ALICEBLUE, "(персонаж, меню)", Color.BLACK);
        label(3, p+=2, Color.ALICEBLUE, "Space - использовать", Color.BLACK);
        label(3, p+=1, Color.ALICEBLUE, "(предмет, пункт меню)", Color.BLACK);
        label(3, p+=2, Color.ALICEBLUE, "Esc - выход/игровое меню", Color.BLACK);
        label(3, p+=2, Color.ALICEBLUE, "ЛКМ - информация о предмете", Color.BLACK);
        label(3, p+=2, Color.ALICEBLUE, "Выход из игры реализуется", Color.BLACK);
        label(3, p+=1, Color.ALICEBLUE, "только в оффлайн версии.", Color.BLACK);
        label(RGame.SCREEN_WIDTH/2-3, RGame.SCREEN_HEIGHT-3, Color.DARKGRAY, "v.1.0", Color.BLACK);
    }

    public void drawBattleScreen() {
        for (int i = 0; i < RGame.SCREEN_HEIGHT-1; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH-1; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GRAY);
            }
        }
        for (int i = 1; i < 5; i++) {
            RGame.getInstance().setCellColor(1, i, Color.RED);
            RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-2, i, Color.RED);
        }
        RGame.getInstance().setCellColor(1, 7, Color.RED);
        RGame.getInstance().setCellColor(RGame.SCREEN_WIDTH-2, 7, Color.RED);
    }

    public void drawGameOverScreen() {
        for (int i = 0; i < RGame.SCREEN_HEIGHT; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GREY);
            }
        }
        int[][] print ={{2,2,2,0,0,0,0,0,29,29,29,29,0,0,0,0,0,2,2,2},
        {0,0,0,2,2,0,0,29,14,14,14,14,2,0,0,2,2,0,0,0},
        {0,0,0,0,0,0,29,14,14,14,14,14,14,2,0,0,0,0,0,0},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {2,2,2,2,0,29,14,14,14,14,14,14,14,14,2,0,2,2,2,2},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {2,2,2,2,0,29,14,14,14,14,14,14,14,14,2,0,2,2,2,2},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {0,0,0,0,0,29,14,14,14,14,14,14,14,14,2,0,0,0,0,0},
        {0,0,2,2,0,29,2,2,2,2,2,2,2,2,2,0,2,2,0,0},
        {2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2},
        {0,0,0,0,0,13,13,13,13,13,13,13,13,13,13,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,13,13,13,13,13,13,13,13,13,13,13,13,13,13,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},};
    printMatrix(print, RGame.SCREEN_WIDTH/2-print.length/2, 3);
        int p = 17;
        label(RGame.SCREEN_WIDTH/2-3,p,Color.NONE,"Здесь",Color.BLACK);
        label(RGame.SCREEN_WIDTH/2-7,p+=2,Color.NONE,"завершён  путь",Color.BLACK);
        label(RGame.SCREEN_WIDTH/2-8,p+=2,Color.NONE,"великого  героя.",Color.BLACK);
    }

    private void showVictoryScreen () {
        for (int i = 0; i < RGame.SCREEN_HEIGHT/2; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.ALICEBLUE);
            }
        }
        for (int i = RGame.SCREEN_HEIGHT/2; i < RGame.SCREEN_HEIGHT; i++) {
             for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.GREEN);
             }
        }
        int[][] sun = {{0,3,3,3,3},
                         {0,3,3,3,3},
                         {0,0,3,3,3},
                         {0,0,0,3,3}};
        printMatrix(sun,RGame.SCREEN_WIDTH-5,0);
        int[][] cloud = {{0,2,2,0,0,2,2,2,2,0,0,0,0},
                         {2,2,2,2,2,2,2,2,2,2,2,2,0},
                         {2,2,2,2,2,2,2,2,2,2,2,2,2},
                         {0,0,0,0,2,2,2,2,2,0,0,0,0}};
        printMatrix(cloud,2,3);
        printMatrix(cloud,RGame.SCREEN_WIDTH-20,5);
        int[][] hills = {{0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                         {0,0,0,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2},
                         {0,0,0,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2},
                         {0,0,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,6,0,0,0,0,0,0,0,0,0,0},
                         {0,6,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0,0,0,0,0,6,6,6,6,6,0,0,0,0,0,0,0,0,0},
                         {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,0,6,6,6,6,6,6,6,6,6,0,0,0,0,0,0,0}};
        printMatrix(hills,2,RGame.SCREEN_HEIGHT/2-hills.length);
        int p =RGame.SCREEN_HEIGHT-10;
        label(2,p,Color.ALICEBLUE,"Ты выходишь из подземелья,",Color.BLACK);
        label(2,p+=1,Color.ALICEBLUE,"держа в руках артефакт Хозяина.",Color.BLACK);
        label(2,p+=1,Color.ALICEBLUE,"Слава, богатство, могущество",Color.BLACK);
        label(2,p+=1,Color.ALICEBLUE,"уже плывут перед твоими глазами.",Color.BLACK);
        label(2,p+=1,Color.ALICEBLUE,"Теперь всё будет по другому.",Color.BLACK);
    }

    private void showNonVictoryScreen() {
        for (int i = 0; i < RGame.SCREEN_HEIGHT/2; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.DARKGRAY);
            }
        }
        for (int i = RGame.SCREEN_HEIGHT/2; i < RGame.SCREEN_HEIGHT; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellColor(j, i, Color.DARKGREEN);
            }
        }
        int[][] cloud =
                {{0,0,0,9,9,9,9,0,0,0,0,0,0},
                {0,9,9,9,9,9,9,9,9,0,0,0,0},
                {9,9,9,9,9,9,9,9,9,9,9,9,0},
                {9,9,9,9,9,9,9,9,9,9,9,9,9},
                {0,0,0,0,9,9,9,9,9,0,0,0,0}};
        printMatrix(cloud,2,2);
        printMatrix(cloud,RGame.SCREEN_WIDTH-18,4);
        int[][] hills =
                {{0,0,0,0,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,33,33,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,33,33,33,33,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,33,33,33,33,33,33,33,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,33,33,0,0,0,0,0,0,0,0,0,0},
                {0,33,33,33,33,33,33,33,33,33,33,33,33,0,0,0,0,0,0,0,0,0,0,0,33,33,33,33,33,0,0,0,0,0,0,0,0,33},
                {33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,33,0,0,0,0,0,33,33,33,33,33,33,33,33,33,0,0,0,0,33,33,33}};
        printMatrix(hills,2,RGame.SCREEN_HEIGHT/2-hills.length);
        int p =RGame.SCREEN_HEIGHT-10;
        label(2,p,Color.DARKGRAY,"Ты выходишь из подземелья.",Color.BLACK);
        label(2,p+=1,Color.DARKGRAY,"Тебе не удалось добыть артефакт,",Color.BLACK);
        label(2,p+=1,Color.DARKGRAY,"Мир вокруг выглядит прежним,",Color.BLACK);
        label(2,p+=1,Color.DARKGRAY,"но кажется, что-то",Color.BLACK);
        label(2,p+=1,Color.DARKGRAY,"неуловимо изменилось.",Color.BLACK);
        label(2,p+=1,Color.DARKGRAY,"Теперь всё будет по другому.",Color.BLACK);
    }

    public void label (int x, int y, Color cellColor, String text, Color textColor) {
        for (int i = 0; i < text.length(); i++) {
            RGame.getInstance().setCellValueEx(x+i, y, cellColor, String.valueOf(text.toCharArray()[i]), textColor, 54);
        }
    }

    public void clear() {
        for (int i = 0; i < RGame.SCREEN_HEIGHT; i++) {
            for (int j = 0; j < RGame.SCREEN_WIDTH; j++) {
                RGame.getInstance().setCellTextSize(j, i, 54);
                RGame.getInstance().setCellTextColor(j, i, Color.BLACK);
                RGame.getInstance().setCellValueEx(j, i, Color.NONE, "");
            }

        }
    }

    public static Screen getInstance() {
        if (instance == null) {instance = new Screen();}
        return instance;
    }

    public void clearBottom() {
        for (int i = 0; i < RGame.SCREEN_WIDTH; i++) {
           label(i, RGame.SCREEN_HEIGHT-1, Color.GRAY," ", Color.BLACK);
        }
    }

    private void printMatrix(int[][] matrix, int x, int y) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                RGame.getInstance().setCellColor(x+j, y+i, Color.values()[matrix[i][j]]);
            }
        }
    }

}

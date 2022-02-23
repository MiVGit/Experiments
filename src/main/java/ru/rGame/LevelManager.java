package ru.rGame;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Key;
import ru.rGame.enums.Directions;
import ru.rGame.enums.GameStates;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.enums.TileType;
import ru.rGame.map.MapLevel;
import ru.rGame.map.Tile;
import ru.rGame.menus.ListMenu;
import ru.rGame.menus.Menu;
import ru.rGame.objects.*;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public MapLevel mapLevel = new MapLevel();
    private ListMenu listMenu = new ListMenu("Здесь:");
    public int currentLevel=1;
    private int lastLevel;
    private Gate upLadder;
    private Gate dowLadder;
    private GameObject currentInteractionObject;
    private List<GameObject> objectsInTile;
    Player player = Player.getInstance();
    private List<GameObject> gameObjects = new ArrayList<>();
    private static LevelManager instance;
    private HealthBar healthBar = new HealthBar();

    private LevelManager() {}

    public void newGame() {
        Player.refreshPlayer();
        player = Player.getInstance();
        lastLevel = 0;
        currentLevel = 1;
        createNewLevel(1);
    }

    public void createNewLevel(int level) {
        gameObjects.clear();
        mapLevel.setLevel(level);
        mapLevel.generate();
        addGates();
        addLoot();
        if (level<Balanser.mapLevels) {
            addMonsters();
        }
        else {addBoss();}
        player.x = lastLevel<=currentLevel? upLadder.x:dowLadder.x;
        player.y = lastLevel<=currentLevel? upLadder.y:dowLadder.y;
        listObjectsInTile();
    }

    public void draw() {
        mapLevel.draw();
        Screen.getInstance().label(1, 0, Color.ALICEBLUE,"Уровень"+currentLevel,Color.BLACK);
        player.setBgColor(player.getType().getBg());
        for (GameObject gameObject:gameObjects) {
            gameObject.draw();
            if (gameObject.x == player.x && gameObject.y == player.y) {
                player.setBgColor(gameObject.getBgColor());
                showAbout(gameObject);
            }
        }
        player.draw();
        healthBar.draw();
    }

    public void showAbout(GameObject gameObject) {
        Screen.getInstance().clearBottom();
        if (gameObject instanceof WildLife) {
            Screen.getInstance().label(1, RGame.SCREEN_HEIGHT - 1, Color.ALICEBLUE, gameObject.caption+"(ур."+((WildLife) gameObject).getLevel()+")"+"."+((WildLife)gameObject).getAction(), Color.BLACK);
        }
        else {Screen.getInstance().label(1, RGame.SCREEN_HEIGHT-1, Color.ALICEBLUE,gameObject.info, Color.BLACK);}
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < mapLevel.getMap().length; i++) {
            for (int j = 0; j < mapLevel.getMap()[0].length; j++) {
                if (mapLevel.getMap()[i][j].getType() == TileType.FLOOR) {
                    //boolean isEmpty = true;
                    //for (GameObject gameObject:gameObjects) {
                    //    if (gameObject.x == i && gameObject.y == j) {isempty = false;}
                    //}
                    //if (isEmpty) {
                    emptyTiles.add(mapLevel.getMap()[i][j]);
                    //}
                }
            }
        }
        if (emptyTiles.size()<=2) {
            createNewLevel(mapLevel.getLevel());
        }
        return emptyTiles;
    }

    private void addGates() {
        if (mapLevel.getLevel() == 1) {
            upLadder = generateGate(ObjectTypes.GATE, "Врата", "Выход из подземелья");
            dowLadder = generateGate(ObjectTypes.LADDER_DOWN, "Спуск ниже", "Лестница вниз");
            gameObjects.add(dowLadder);
        }
        else if (mapLevel.getLevel() == RGame.LAST_LEVEL) {
            upLadder = generateGate(ObjectTypes.LADDER_UP, "Подъём", "Подъём наверх");
            dowLadder = null;
        }
        else {
            upLadder = generateGate(ObjectTypes.LADDER_UP, "Подъём", "Подъём наверх");
            dowLadder = generateGate(ObjectTypes.LADDER_DOWN, "Спуск ниже", "Лестница вниз");
            gameObjects.add(dowLadder);
        }
        gameObjects.add(upLadder);
    }

    private void addLoot(){
        int rand = RGame.random(100);
        if (rand<=1) {gameObjects.add(generateLoot(ObjectTypes.LEFTOVER));}
        rand = RGame.random(100);
        if (rand<=2) {gameObjects.add(generateLoot(ObjectTypes.WEAPON));}
        rand = RGame.random(100);
        if (rand<=6) {gameObjects.add(generateLoot(ObjectTypes.ARMOR));}
        rand = RGame.random(100);
        if (rand<=10) {gameObjects.add(generateLoot(ObjectTypes.POTION));}
        rand = RGame.random(100);
        if (rand<=50) {
            int foodCount = 1;
            rand = RGame.random(100);
            if (rand <= 20) {foodCount = 2;}
            for (int i = 0; i < foodCount; i++) {
                gameObjects.add(generateLoot(ObjectTypes.FOOD));
            }
        }
    }
    private ItemObject generateLoot(ObjectTypes type){
        List<Tile> emptyTiles = getEmptyTiles();
        int i = RGame.random(emptyTiles.size()-1);
        switch (type) {
            case ARMOR: {
                return new Armor(ObjectTypes.HUMANOID, currentLevel,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
            case WEAPON: {
                return new Weapon(ObjectTypes.HUMANOID, currentLevel,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
            case FOOD: {
                return new Food(currentLevel,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
            case POTION: {
                return new Potion(currentLevel,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
            default: {
                return new Leftovers(currentLevel,emptyTiles.get(i).getX(), emptyTiles.get(i).getY(),"Тело","Тело забытого героя", -1000);
            }
        }
    }

    private void addMonsters() {
        int rand = RGame.random(100);
        if (rand<=30) {gameObjects.add(generateMonster(ObjectTypes.MONSTER));}
        rand = RGame.random(100);
        if (rand<=5) {gameObjects.add(generateMonster(ObjectTypes.MONSTER));}
        rand = RGame.random(100);
        if (rand<=70) {gameObjects.add(generateMonster(ObjectTypes.ANIMAL));}
        rand = RGame.random(100);
        if (rand<=10) {gameObjects.add(generateMonster(ObjectTypes.ANIMAL));}
        gameObjects.add(generateMonster(ObjectTypes.HUMANOID));
        rand = RGame.random(100);
        if (rand<=80) {gameObjects.add(generateMonster(ObjectTypes.HUMANOID));}
        rand = RGame.random(100);
        if (rand<=50) {gameObjects.add(generateMonster(ObjectTypes.HUMANOID));}
        rand = RGame.random(100);
        if (rand<=10) {gameObjects.add(generateMonster(ObjectTypes.HUMANOID));}
    }
    private WildLife generateMonster(ObjectTypes type) {
        List<Tile> emptyTiles = getEmptyTiles();
        int i = RGame.random(emptyTiles.size()-1);
        int level;
        switch (type) {
            case MONSTER: {
                level = RGame.getInstance().isBossDefeated()? Balanser.mapLevels*2-currentLevel+RGame.random(2)+2:currentLevel+RGame.random(2)+2;
                return new M_Monster(level,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
            case ANIMAL: {
                level = RGame.getInstance().isBossDefeated()? Balanser.mapLevels*2-currentLevel+RGame.random(1)-2:currentLevel>2?currentLevel+RGame.random(1)-2:currentLevel;
                return new M_Animal(level,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
            default: {
                level = RGame.getInstance().isBossDefeated()? Balanser.mapLevels*2-currentLevel+RGame.random(2):currentLevel+RGame.random(2);
                return new M_Humanoid(level,emptyTiles.get(i).getX(), emptyTiles.get(i).getY());
            }
        }
    }

    private void addBoss() {
        if (!RGame.getInstance().isBossDefeated()) {
            List<Tile> emptyTiles = getEmptyTiles();
            int i = RGame.random(emptyTiles.size() - 1);
            gameObjects.add(new M_Boss(emptyTiles.get(i).getX(), emptyTiles.get(i).getY()));
        }
    }

    private Gate generateGate (ObjectTypes type, String about, String info) {
        List<Tile> emptyTiles = getEmptyTiles();
        int i = RGame.random(emptyTiles.size()-1);
        return new Gate(type, emptyTiles.get(i).getX(), emptyTiles.get(i).getY(), about, info);
    }

    public void makeTurn(Key key) {
        movePlayer(key);
        if (key!=null&&key!=Key.SPACE) {
            //Выполняем ход, если нажата какая-то кнопка
            RGame.getInstance().nextTurn();
            List<WildLife> wildLife = new ArrayList<>();
            for (GameObject gameObject:gameObjects) {
                if (gameObject instanceof WildLife) {wildLife.add((WildLife) gameObject);}
            }
            for (WildLife monster:wildLife) {
                monster.makeTurn();
            }
        }
        if (RGame.getInstance().state == GameStates.MAIN) {listObjectsInTile();}
        Player.getInstance().calculateCurrentStats();
    }

    private void movePlayer (Key key) {
        Directions direction;
        if (key != null) {
            switch (key) {
                case UP: {
                    direction = Directions.UP;
                    break;
                }
                case DOWN: {
                    direction = Directions.DOWN;
                    break;
                }
                case LEFT: {
                    direction = Directions.LEFT;
                    break;
                }
                case RIGHT: {
                    direction = Directions.RIGHT;
                    break;
                }
                case SPACE: {
                    direction = Directions.NONE;
                    useObject();
                    break;
                }
                default: {
                    direction = Directions.NONE;
                }
            }
            player.move(mapLevel, direction);
        }
    }

    private void useObject () {
        List<GameObject> objects = objectsInTile(player.x, player.y);
        if (objects.size()==1) {
            if (objects.get(0) instanceof Interactive) {
                ((Interactive) objects.get(0)).select();
            }
        }
        else if (objects.size()>1) {
            for (Menu menu:RGame.getInstance().menus) {
                if (menu instanceof ListMenu) {menu.setActive(true);}
            }
        }
    }

    public List<GameObject> objectsInTile(int x, int y) {
        List<GameObject> objects = new ArrayList<>();
        for (GameObject gameObject:gameObjects) {
            if (gameObject.x == x && gameObject.y == y) {
                objects.add(gameObject);
            }
        }
        return objects;
    }

    private void listObjectsInTile () {
        objectsInTile = objectsInTile(player.x, player.y);
        for (int i = 0; i < RGame.getInstance().menus.size(); i++) {
            if (RGame.getInstance().menus.get(i) instanceof ListMenu) {
                RGame.getInstance().menus.remove(i);
            }
        }
        if (objectsInTile.size()>1) {
            int i = 0;
            listMenu.clear();
            for (GameObject objectInTile : objectsInTile) {
                listMenu.add(String.valueOf(i), objectInTile.caption);
                i++;
            }
            RGame.getInstance().addMenu(listMenu);
        }
}

    public void enterGateC() {
        if (currentInteractionObject.getType() == ObjectTypes.GATE) {
            RGame.getInstance().finishGame();
        }
        else {
            if (currentInteractionObject.getType() == ObjectTypes.LADDER_DOWN) {lastLevel=currentLevel++;}
            if (currentInteractionObject.getType() == ObjectTypes.LADDER_UP) {lastLevel=currentLevel--;}
            createNewLevel(currentLevel);
        }
    }

    public GameObject getCurrentInteractionObject() {
        return currentInteractionObject;
    }

    public void useListObject(Menu menu){
        if (objectsInTile.get(menu.getMenuIndex()) instanceof Interactive) {
            ((Interactive) objectsInTile.get(menu.getMenuIndex())).select();
            menu.setActive(false);
        }
    }

    public void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public void removeGameObject(GameObject object) {
            gameObjects.remove(object);
    }

    public static LevelManager getInstance() {
        if (instance == null) {instance = new LevelManager();}
        return instance;
    }

    public void setCurrentInteractionObject(GameObject currentInteractionObject) {
        this.currentInteractionObject = currentInteractionObject;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public ArrayList<GameObject> getItemObjects() {
        ArrayList<GameObject> itemObjects = new ArrayList<>();
        for (GameObject gameObject:gameObjects) {
            if (gameObject instanceof ItemObject && !(gameObject instanceof Person)) {
                itemObjects.add(gameObject);
            }
        }
        return itemObjects;
    }

}

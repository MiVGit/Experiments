package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.Interactive;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.enums.Desires;
import ru.rGame.enums.Directions;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.enums.TileType;
import ru.rGame.menus.ConfirmMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class WildLife extends Person implements Interactive {
    int agression; //0 - трусливый, 1 - пассивный, 2 - агрессивный
    int greediness; //0 - не жадный, 1 - жадный
    int activity; //0 - ленивый, 1 - активный, 2 - гиперактивный
    String remains;
    String remainsInfo;
    int remainsNutrition;
    int armorChance;
    int weaponChance;
    int foodChance;
    int potionChance;
    Desires desire;
    int mutability;
    int lastTurn;
    GameObject target;

    public WildLife(ObjectTypes type, int level, int x, int y) {
        super(type, level, x, y);
        setColor();
        mutability = RGame.random(30)+6;
        generate(getMonsterList());
        desire = getDesire();
        colorDesire();
    }

    private void setColor() {
        if (Player.getInstance().getLevel()>level+2) {color = Color.DARKGREEN;}
        else if (Player.getInstance().getLevel()>level) {color = Color.GREEN;}
        else if (Player.getInstance().getLevel()==level) {color = Color.BLACK;}
        else if (Player.getInstance().getLevel()<level-2) {color = Color.RED;}
        else if (Player.getInstance().getLevel()<level) {color = Color.DARKORANGE;}
    }

    void generate(ArrayList<Monster> monsters) {
        Monster monster = monsters.get(RGame.random(monsters.size()-1));
        this.caption = monster.caption;
        this.info = monster.info;
        this.agression = monster.agression;
        this.activity = monster.activity;
        this.greediness = monster.greediness;
        this.remains = monster.remains;
        this.remainsInfo = monster.remainsInfo;
        this.remainsNutrition = monster.remainsNutrition;
    }

    abstract ArrayList<Monster> getMonsterList();

    @Override
    protected void generateInventory(int level) {
        getChances();
        int chance = RGame.random(100);
        if (chance < armorChance) {
            inventory.armor = new Armor(type, level, 0, 0);
        }
        chance = RGame.random(100);
        if (chance < weaponChance) {
            inventory.weapon = new Weapon(type, level, 0, 0);
        }
        chance = RGame.random(100);
        if (chance < potionChance) {
            inventory.inventory.add(new Potion(level, 0, 0));
        }
        chance = RGame.random(100);
        if (chance < foodChance) {
            inventory.inventory.add(new Food(level, 0, 0));
        }
    }

    protected abstract void getChances();

    private Desires getDesire() {
        ArrayList<Desires> desires = new ArrayList<>();
        desires.clear();
        if (agression == 2) {desires.add(Desires.ATTACK);}
        if (agression < 1) {desires.add(Desires.RUN);}
        if (activity == 0) {desires.add(Desires.SLEEP);}
        if (greediness == 1) {desires.add(Desires.GRAB);}
        desires.add(Desires.WANDER);
        Desires desire = desires.get(RGame.random(desires.size()-1));
        if (desire == Desires.ATTACK||desire == Desires.RUN) {target = Player.getInstance();}
        if (desire == Desires.GRAB) {selectGrabTarget();}
        return desire;
    }

    private void changeDesire() {
        if (RGame.getInstance().getTurn()>lastTurn+mutability) {
            lastTurn = RGame.getInstance().getTurn();
            desire = getDesire();
            colorDesire();
        }
    }

    private void colorDesire() {
        switch (desire) {
            case ATTACK: {
                bgColor=Color.ORANGE;
                break;
            }
            case RUN: {
                bgColor=Color.ALICEBLUE;
                break;
            }
            case WANDER: {
                bgColor=Color.GREEN;
                break;
            }
            case SLEEP: {
                bgColor=Color.GRAY;
                break;
            }
            case GRAB: {
                bgColor=Color.YELLOW;
                break;
            }
        }
    }

    private void selectGrabTarget() {
        List<GameObject> interactiveObjects;
        if ((interactiveObjects = LevelManager.getInstance().getItemObjects()).size()>0) {
            target = interactiveObjects.get(RGame.random(interactiveObjects.size()-1));
        }
        else {target = Player.getInstance();}
    }

    private Directions targetDirection() {
        if (target == null) {return Directions.NONE;}
        else {
            Directions horizontalDirection;
            Directions verticalDirection;
            if (x>target.x) {horizontalDirection = Directions.LEFT;}
            else if (x< target.x) {horizontalDirection =  Directions.RIGHT;}
            else {horizontalDirection = Directions.NONE;}
            if (y>target.y) {verticalDirection = Directions.UP;}
            else if (y< target.y) {verticalDirection = Directions.DOWN;}
            else {verticalDirection = Directions.NONE;}

            if (Math.abs(x-target.x)>Math.abs(y-target.y)) {
                int displacement = x==target.x?0:(x-target.x)/Math.abs(x-target.x);
                if(LevelManager.getInstance().mapLevel.getMap()[x-displacement][y].getType() != TileType.WALL) {return horizontalDirection;}
                else {return verticalDirection==Directions.NONE?RGame.random(1)==1?Directions.UP:Directions.DOWN:verticalDirection;}
            }
            else {
                int displacement = y==target.y?0:(y-target.y)/Math.abs(y-target.y);
                if(LevelManager.getInstance().mapLevel.getMap()[x][y-displacement].getType() != TileType.WALL) {return verticalDirection;}
                else {return horizontalDirection==Directions.NONE?RGame.random(1)==1?Directions.LEFT:Directions.RIGHT:horizontalDirection;}
            }
        }
    }
    private Directions reverceTargetDirection () {
        switch (targetDirection()) {
            case LEFT: {return Directions.RIGHT;}
            case RIGHT: {return Directions.LEFT;}
            case UP: {return Directions.DOWN;}
            case DOWN: {return Directions.UP;}
            default: {return Directions.NONE;}
        }
    }

    private Directions selectDirection() {
        switch (desire) {
            case RUN: {return reverceTargetDirection();}
            case ATTACK: {return targetDirection();}
            case GRAB: {return targetDirection();}
            case WANDER: {return Directions.values()[RGame.random(Directions.values().length-1)];}
            default: {return Directions.NONE;}
        }
    }

    private void doAction() {
        if (target != null) {
            if (x == target.x && y == target.y) {
                if (desire == Desires.GRAB && target instanceof ItemObject) {
                    ((ItemObject) target).grab(this);
                    desire = Desires.WANDER;
                }
            }
        }
        if (x == Player.getInstance().x && y == Player.getInstance().y && agression == 2) {
            RGame.getInstance().fight(this);
        }
    }

    public void makeTurn() {
        changeDesire();
        move(LevelManager.getInstance().mapLevel, selectDirection());
        doAction();
    }

    public void dropBody() {
        LevelManager.getInstance().addGameObject(new Leftovers(this.level,this.x, this.y, this.remains, this.remainsInfo, this.remainsNutrition));
    }

    @Override
    public void use() {
        ConfirmMenu confirmMenu = new ConfirmMenu(this, "Напасть?");
    }

    @Override
    public void useC() {
        RGame.getInstance().fight(this);
    }

    @Override
    public void select() {
        use();
    }

    public String getAction() {
        switch (desire) {
            case RUN: return "Убегает";
            case WANDER: return "Гуляет";
            case ATTACK: return "Нападает";
            case GRAB: return target==Player.getInstance()?"Хочет ограбить игрока":"Хочет взять"+target.caption;
            case SLEEP: return "Спит";
            default: return "";
        }
    }

    class Monster {
        String caption;
        String info;
        int agression;
        int greediness;
        int activity;
        String remains;
        String remainsInfo;
        int remainsNutrition;
        Monster(String caption, String info, int agression, int greediness, int activity, String remains, String remainsInfo, int remainsNutrition) {
            this.caption = caption;
            this.info = info;
            this.agression = agression;
            this.greediness = greediness;
            this.activity = activity;
            this.remains = remains;
            this.remainsInfo = remainsInfo;
            this.remainsNutrition = remainsNutrition;
        }
    }

}

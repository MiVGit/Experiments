package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.InventoryManager;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.enums.ObjectTypes;

public class Armor extends ItemObject{
    private final ObjectTypes ownerType;
    private int strength=0;
    private int dextery=0;
    private int vitality=0;
    private final int defence;
    private final String[] NAMES_H = {
            "Деревянный нагрудник",
            "Кожаная накидка",
            "Стальной доспех",
            "Желейное покрытие",
            "Расстегай",
            "Застегай",
            "Рёбра скелета",
            "Полный доспех",
            "Кушак",
            "Шишак"
    };
    private final String[] NAMES_A = {
            "Ошейник",
            "Носочки",
            "Чехол",
            "Намордник",
            "Попона",
            "Курточка"
    };
    private final String[] NAMES_M = {
            "Крыло",
            "Щит",
            "Шипы",
            "Копыта",
            "Пластина"
    };
    private final String[][] TYPES = {
            {""},
            {"титана","халка","ярости","стража"},
            {"прыти","с дырой", "XXS", "опа-опа","ужа"},
            {"бизона","на вате", "без дыр", "XXL", "ласки", "с мехом"}
    };

    public Armor(ObjectTypes type, int level, int x, int y) {
        super(ObjectTypes.ARMOR, level, x, y);
        String[] names;
        if (type == ObjectTypes.PLAYER||type==ObjectTypes.HUMANOID||type==ObjectTypes.BOSS) {names = NAMES_H;}
        else if (type == ObjectTypes.ANIMAL) {names = NAMES_A;}
        else if (type == ObjectTypes.MONSTER) {names = NAMES_M;}
        else {names = NAMES_H;}
        String info = names[RGame.random(names.length-1)];
        int roll = RGame.random(100);
        if (50<roll&&roll<=66) {
            this.strength = RGame.random(level)+1;
            info = info+" "+TYPES[1][RGame.random(TYPES[1].length-1)];
        }
        else if (roll<=82) {
            this.dextery = RGame.random(level)+1;
            info = info+" "+TYPES[2][RGame.random(TYPES[2].length-1)];
        }
        else {
            this.vitality = RGame.random(level)+1;
            info = info+" "+TYPES[3][RGame.random(TYPES[3].length-1)];
        }
        defence = level;
        this.info = info;
        this.caption = "Броня";
        this.ownerType = type;
        this.useCaption = "Переодеться";
    }

    @Override
    public void showInfo() {
        int p = RGame.SCREEN_HEIGHT-10;
        Screen.getInstance().label(3, p, Color.DARKGRAY,"Уровень "+level,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Защита "+defence,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Сила + "+strength,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Ловкость + "+dextery,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Выносливость + "+vitality,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Носит "+ownerType.toString(),Color.BLACK);
    }

    @Override
    public void useC() {
        if (ownerType == ObjectTypes.HUMANOID ||ownerType == ObjectTypes.PLAYER || ownerType == ObjectTypes.BOSS) {
            if (Player.getInstance().getInventory().armor == this) {
                grab(Player.getInstance());
                Player.getInstance().getInventory().armor = null;
            } else if (Player.getInstance().getInventory().inventory.contains(this)) {
                Player.getInstance().getInventory().inventory.remove(this);
                if (Player.getInstance().getInventory().armor != null) {
                    Player.getInstance().getInventory().armor.grab(Player.getInstance());
                }
                Player.getInstance().getInventory().armor = this;
            } else {
                if (Player.getInstance().getInventory().armor != null) {
                    Player.getInstance().getInventory().armor.grab(Player.getInstance());
                }
                Player.getInstance().getInventory().armor = this;
                LevelManager.getInstance().removeGameObject(this);
            }
            InventoryManager.getInstance().refreshInventory();
        }
        else {
            RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-11, RGame.SCREEN_HEIGHT-1, "Не надеть");
            RGame.getInstance().showMessage();
        }
    }

    public int getStrength() {
        return strength;
    }

    public int getDextery() {
        return dextery;
    }

    public int getVitality() {
        return vitality;
    }

    public int getDefence() {
        return defence;
    }

    public int getLevel() {
        return level;
    }
}

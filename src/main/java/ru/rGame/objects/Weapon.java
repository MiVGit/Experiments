package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.InventoryManager;
import ru.rGame.LevelManager;
import ru.rGame.RGame;
import ru.rGame.Screen;
import ru.rGame.enums.ObjectTypes;

public class Weapon extends ItemObject{
    private ObjectTypes ownerType;
    private int strength=0;
    private int dextery=0;
    private int vitality=0;
    private int damage;
    private final String[] NAMES_H = {
            "Колобашка",
            "Половник",
            "Меч",
            "Мифрильный молот",
            "Кнут",
            "Пряник",
            "Пистолет",
            "Крюк",
            "Булыжник"
    };
    private final String[] NAMES_A = {
            "Золотой клык",
            "Коготь",
            "Пятая лапа",
            "Меч в зубы",
            "Шипохвост"
    };
    private final String[] NAMES_M = {
            "Аяоль",
            "Надкрылье",
            "Шар",
            "Клыки",
            "Рог"
    };
    private final String[][] TYPES = {
            {""},
            {"огромной мощи","могущества","тяжести", "с шипами"},
            {"без веса","огня","танцора"},
            {"красоты","огра","черепахи"}
    };

    public Weapon(ObjectTypes type, int level, int x, int y) {
        super(ObjectTypes.WEAPON, level, x, y);
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
        damage = level;
        this.info = info;
        this.caption = "Оружие";
        this.ownerType = type;
        this.useCaption = "Заменить оружие";
    }

    @Override
    public void showInfo() {
        int p = RGame.SCREEN_HEIGHT-10;
        Screen.getInstance().label(3, p, Color.DARKGRAY,"Уровень "+level,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Урон "+damage,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Сила + "+strength,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Ловкость + "+dextery,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Выносливость + "+vitality,Color.BLACK);
        Screen.getInstance().label(3, p+=1, Color.DARKGRAY,"Носит "+ownerType.toString(),Color.BLACK);
    }

    @Override
    public void useC() {
        if (ownerType == ObjectTypes.HUMANOID ||ownerType == ObjectTypes.PLAYER || ownerType == ObjectTypes.BOSS) {
            if (Player.getInstance().getInventory().weapon == this) {
                grab(Player.getInstance());
                Player.getInstance().getInventory().weapon = null;
            } else if (Player.getInstance().getInventory().inventory.contains(this)) {
                Player.getInstance().getInventory().inventory.remove(this);
                if (Player.getInstance().getInventory().weapon != null) {
                    Player.getInstance().getInventory().weapon.grab(Player.getInstance());
                }
                Player.getInstance().getInventory().weapon = this;
            } else {
                if (Player.getInstance().getInventory().weapon != null) {
                    Player.getInstance().getInventory().weapon.grab(Player.getInstance());
                }
                Player.getInstance().getInventory().weapon = this;
                LevelManager.getInstance().removeGameObject(this);
            }
            InventoryManager.getInstance().refreshInventory();
        }
        else {
            RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-12, RGame.SCREEN_HEIGHT-1,"Это не моё");
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

    public int getDamage() {
        return damage;
    }

    public int getLevel() {
        return level;
    }
}

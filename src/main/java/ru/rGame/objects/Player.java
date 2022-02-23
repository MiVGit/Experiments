package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.Balanser;
import ru.rGame.RGame;
import ru.rGame.enums.ObjectTypes;
import ru.rGame.enums.Stats;

public class Player extends  Person{
    private static Player instance;
    private int lvlUpPoints;
    private int runCooldown;
    private int expPoints;
    private Player() {
        super(ObjectTypes.PLAYER, 1, 3, 3);
        this.setColor(Color.RED);
        this.caption = "Игрок";
        this.info = "Игрок обыкновенный";
    }

    @Override
    protected void generateInventory(int level) {
        int chance = RGame.random(100);
        if (chance < 60) {
            inventory.armor = new Armor(ObjectTypes.PLAYER, level, 0, 0);
        }
        chance = RGame.random(100);
        if (chance < 40) {
            inventory.weapon = new Weapon(ObjectTypes.PLAYER, level, 0, 0);
        }
        inventory.inventory.add(new Food(1,0,0));
        inventory.inventory.add(new Food(1,0,0));
    }

    public void upStat(Stats stat) {
        addStat(stat, 1);
        if (stat == Stats.VITALITY) {addHP(Balanser.healthPerVitality);}
        lvlUpPoints--;
    }

    public void addExpPoints(int points) {
        if ((level<Balanser.levelCapNA)||
            (inventory.inventory.contains(Artifact.getInstance())&&level<Balanser.levelCapArtifacted)) {
            expPoints +=points;
            RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-16, RGame.SCREEN_HEIGHT-1, "Я стал сильнее!");
        }
        else RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-16, RGame.SCREEN_HEIGHT-1, "Это мой предел.");
        checkForLvlUp();
    }

    private void checkForLvlUp () {
        if (expPoints>level*level/2) {
            if ((level<Balanser.levelCapNA)||
                (inventory.inventory.contains(Artifact.getInstance())&&level<Balanser.levelCapArtifacted)) {
                lvlUp();
            }
        }
    }

    private void lvlUp () {
        level++;
        lvlUpPoints += Balanser.pointsPerLevel;
        health[1] = health[1]+health[0]*(Balanser.healthPerLvlUp)/100;
        health[1] = Math.min(health[1], health[0]);
    }

    public static Player getInstance() {
        if (instance == null) {instance = new Player();}
        return instance;
    }

    public int getLvlUpPoints() {
        return lvlUpPoints;
    }

    public int getRunCooldown() {
        return runCooldown;
    }

    public void setRunCooldown(int runCooldown) {
        this.runCooldown = runCooldown;
    }

    public static void refreshPlayer() {
        instance = new Player();
    }

    @Override
    public void cooldowns() {
        super.cooldowns();
        runCooldown = runCooldown<0? 0:runCooldown-1;
    }
}

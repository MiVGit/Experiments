package ru.rGame.objects;

import ru.rGame.*;
import ru.rGame.enums.*;
import ru.rGame.map.MapLevel;

public abstract class Person extends GameObject implements Fighter, Movable {
    protected Inventory inventory;
    protected int level;
    int[] strength= {1,1};
    int[] dexterity= {1,1};
    int[] vitality= {1,1};
    int[] health= {100, 100};
    int defence;
    int damage;
    private BattleStances stance;
    private int powerStrikeCooldown;
    private int aimedStrikeCooldown;

    public Person(ObjectTypes type, int level, int x, int y) {
        super(type, x, y);
        this.level = level;
        generateStats(level);
        inventory = new Inventory();
        generateInventory(level);
        calculateCurrentStats();
        health[1] = health[0];
    }

    private void generateStats(int level) {
        int left = level* Balanser.pointsPerLevel;
        int points = RGame.random(left);
        strength[0] = strength[0]+points;
        left = left-points;
        points = RGame.random(left);
        dexterity[0] = dexterity[0]+points;
        left = left-points;
        vitality[0] = vitality[0]+left;
    }

    protected abstract void generateInventory(int level);

    public void calculateCurrentStats () {
        int[] armorStats = {0,0,0};
        int[] weaponStats = {0,0,0};
        defence = Balanser.basicDefence;
        damage = Balanser.basicDamage;
        if (inventory.armor != null) {
            armorStats[0] = inventory.armor.getStrength();
            armorStats[1] = inventory.armor.getDextery();
            armorStats[2] = inventory.armor.getVitality();
            defence += inventory.armor.getDefence();
        }
        if (inventory.weapon != null) {
            weaponStats[0] = inventory.weapon.getStrength();
            weaponStats[1] = inventory.weapon.getDextery();
            weaponStats[2] = inventory.weapon.getVitality();
            damage += inventory.weapon.getDamage();
        }
        strength[1] = strength[0]+armorStats[0]+weaponStats[0];
        dexterity[1] = dexterity[0]+armorStats[1]+weaponStats[1];
        vitality[1] = vitality[0]+armorStats[2]+weaponStats[2];
        health[0] = Balanser.basicHealth+vitality[1]* Balanser.healthPerVitality;
        if (health[1]>health[0]) {health[1]=health[0];}
    }

    public void addHP (int hp) {
        health[1] = health[1]+hp;
        if (health[1]>health[0]) {health[1]=health[0];}
    }

    public void addStat(Stats stat, int amount) {
        switch (stat) {
            case STRENGTH: {
                strength[0] = strength[0]+amount;
                break;
            }
            case DEXTERITY: {
                dexterity[0] = dexterity[0]+amount;
                break;
            }
            case VITALITY: {
                vitality[0] = vitality[0]+amount;
                break;
            }
        }
        calculateCurrentStats();
    }

    public void cooldowns() {
        powerStrikeCooldown = powerStrikeCooldown<=0? 0:powerStrikeCooldown-1;
        aimedStrikeCooldown = aimedStrikeCooldown<=0? 0:aimedStrikeCooldown-1;
    }

    @Override
    public void move(MapLevel map, Directions direction) {
        switch (direction) {
            case UP: {
                if (y>1 && map.getMap()[x][y-1].getType() != TileType.WALL) {y--;}
                break;
            }
            case DOWN: {
                if (y< RGame.FIELD_HEIGHT-1 && map.getMap()[x][y+1].getType() != TileType.WALL) {y++;}
                break;
            }
            case LEFT: {
                if (x > 1 && map.getMap()[x-1][y].getType() != TileType.WALL) {x--;}
                break;
            }
            case RIGHT: {
                if (x < RGame.SCREEN_WIDTH-1 && map.getMap()[x+1][y].getType() != TileType.WALL) {x++;}
                break;
            }
        }
    }

    @Override
    public void attack() {
        stance = BattleStances.ATTACK;
    }

    @Override
    public void defence() {
        stance = BattleStances.DEFENCE;
    }

    @Override
    public void powerAttack() {
        if (powerStrikeCooldown<=0) {
            powerStrikeCooldown = Balanser.powerStrikeCooldown-vitality[1]/Balanser.vitalityToLowerpscd;
            stance = BattleStances.POWER_ATTACK;
        }
        else stance = BattleStances.ATTACK;
    }

    @Override
    public void aimedAttack() {
        if (aimedStrikeCooldown<=0) {
            aimedStrikeCooldown = Balanser.aimedStrikeCooldown-dexterity[1]/Balanser.dexterityToLowerascd;
            stance = BattleStances.AIMED_ATTACK;
        }
        else stance = BattleStances.ATTACK;
    }

    @Override
    public void dodge() {
        stance = BattleStances.DODGE;
    }

    public void dropAll() {
        for (int i = 0; i < inventory.inventory.size()+2; i++) {
            inventory.drop(this, i);
        }
    }

    public int[] getStrength() {
        return strength;
    }

    public int[] getDexterity() {
        return dexterity;
    }

    public int[] getVitality() {
        return vitality;
    }

    public int getLevel() {
        return level;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int[] getHealth() {
        return health;
    }

    public BattleStances getStance() {
        return stance;
    }

    public int getPowerStrikeCooldown() {
        return powerStrikeCooldown;
    }

    public int getAimedStrikeCooldown() {
        return aimedStrikeCooldown;
    }

    public int getDefence() {
        return defence;
    }

    public int getDamage() {
        return damage;
    }

    public void setPowerStrikeCooldown(int powerStrikeCooldown) {
        this.powerStrikeCooldown = powerStrikeCooldown;
    }

    public void setStance(BattleStances stance) {
        this.stance = stance;
    }

    public void setAimedStrikeCooldown(int aimedStrikeCooldown) {
        this.aimedStrikeCooldown = aimedStrikeCooldown;
    }
}

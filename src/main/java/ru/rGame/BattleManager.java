package ru.rGame;

import com.javarush.engine.cell.Color;
import ru.rGame.enums.BattleStances;
import ru.rGame.enums.GameStates;
import ru.rGame.enums.Stats;
import ru.rGame.menus.BattleLog;
import ru.rGame.menus.BattleMenu;
import ru.rGame.menus.Menu;
import ru.rGame.objects.M_Boss;
import ru.rGame.objects.Person;
import ru.rGame.objects.Player;
import ru.rGame.objects.WildLife;

import java.util.ArrayList;
import java.util.List;

public class BattleManager {
    private static BattleManager instance;
    private final String DIVIDER = ".......................";
    private Person opponent;
    private int battleTurn;
    private final Menu battleLog = new BattleLog();
    private int order;
    private int damage;

    private BattleManager() {}

    public void showBattleScreen(Person opponent) {
        Screen.getInstance().draw(GameStates.BATTLE);
        this.opponent = opponent;
        order = Player.getInstance().getDexterity()[1]>opponent.getDexterity()[1]? 0:1;
        showInfo();
        BattleMenu menu = new BattleMenu();
        menu.setActive(true);
        battleLog.clear();
        RGame.getInstance().addMenu(menu);
        RGame.getInstance().addMenu(battleLog);
        if (order == 1) {opponentFirstStrike();}
    }

    private void opponentFirstStrike() {
        //Выбор действия при незнании хода игрока и вывод информации о выбранном действии
        if (RGame.random(100)<85) {selectAttack();}
        else {selectDefenceU();}
        switch (opponent.getStance()) {
            case ATTACK: {
                battleLog.add("", opponent.caption+" идёт в атаку.");
                break;
            }
            case POWER_ATTACK: {
                battleLog.add("", opponent.caption+" размахивается на рубль.");
                break;
            }
            case AIMED_ATTACK: {
                battleLog.add("", opponent.caption+" щурит глаза.");
                break;
            }
            case DEFENCE: {
                battleLog.add("", opponent.caption+" уходит в оборону.");
                break;
            }
            case DODGE: {
                battleLog.add("", opponent.caption+" прижимается к земле.");
                break;
            }
        }
    }

    private void opponentSecondStrike() {
        //Выбор действия при знании хода игрока
        selectDefenceK();
    }

    private void selectAttack () {
        //выбор оппонентом любимой или другой доступной атаки
        BattleStances preferredStance=null;
        if (opponent.getStrength()[1]>opponent.getDexterity()[1]&&opponent.getPowerStrikeCooldown()<=0) {preferredStance = BattleStances.POWER_ATTACK;}
        else if (opponent.getAimedStrikeCooldown()<=0) {preferredStance = BattleStances.AIMED_ATTACK;}
        if (preferredStance == null ||RGame.random(100)<=20) {
            List<BattleStances> possibleStances = new ArrayList<>();
            possibleStances.add(BattleStances.ATTACK);
            if (opponent.getAimedStrikeCooldown()<=0) {possibleStances.add(BattleStances.AIMED_ATTACK);}
            if (opponent.getPowerStrikeCooldown()<=0) {possibleStances.add(BattleStances.POWER_ATTACK);}
            switch (possibleStances.get(RGame.random(possibleStances.size()-1))) {
                case ATTACK: {
                    opponent.attack();
                    break;
                }
                case POWER_ATTACK: {
                    opponent.powerAttack();
                    break;
                }
                case AIMED_ATTACK: {
                    opponent.aimedAttack();
                    break;
                }
            }
        }
        else {
            if (preferredStance == BattleStances.AIMED_ATTACK) {opponent.aimedAttack();}
            else {opponent.powerAttack();}
        }
    }

    private void selectDefenceU() {
        //выбор оппонентом подходящей защиты без знания стойки игрока
        if (RGame.random(100)<75||maxStat(opponent) != Stats.DEXTERITY) {opponent.defence();}
        else {opponent.dodge();}
    }

    private void selectDefenceK() {
        //выбор оппонентом подходящей защиты со знанием стойки игрока
        if (Player.getInstance().getStance() == BattleStances.AIMED_ATTACK) {
            opponent.defence();
        }
        else if (Player.getInstance().getStance() == BattleStances.POWER_ATTACK) {
            if (opponent.getDexterity()[1]>Player.getInstance().getDexterity()[1]) {opponent.dodge();}
            else if (RGame.random(100)<85) {opponent.defence();}
            else {selectAttack();}
        }
        else if (Player.getInstance().getStance() == BattleStances.ATTACK && RGame.random(100)<75) {
            if (opponent.getDexterity()[1]>Player.getInstance().getDexterity()[1]&&RGame.random(100)<40) {opponent.dodge();}
            else {opponent.defence();}
        }
        else {selectAttack();}
    }

    public void makeTurn() {
        if (order == 1) {
            doAttack();
        }
        else {
            opponentSecondStrike();
            doAttack();
            opponentFirstStrike();
        }
        if (checkOpponents()) {
            battleLog.add("", DIVIDER);
            order = 1 - order;
            cooldowns();
            battleTurn++;
            Screen.getInstance().clear();
            showInfo();
        }
    }

    private void cooldowns() {
        Player.getInstance().cooldowns();
        opponent.cooldowns();
    }

    private void doAttack() {
        //Расчёт результата атак обоих оппонентов в зависимости от выбранных стоек
        if (Player.getInstance().getStance() == BattleStances.ATTACK) {
            calculateAttack(Player.getInstance(), opponent);
        }
        else if (Player.getInstance().getStance() == BattleStances.POWER_ATTACK) {
            calculatePowerAttack(Player.getInstance(), opponent);
        }
        else if (Player.getInstance().getStance() == BattleStances.AIMED_ATTACK) {
            calculateAimedAttack(Player.getInstance(), opponent);
        }
        else if (opponent.getStance() == BattleStances.DEFENCE||opponent.getStance() == BattleStances.DODGE) {
            battleLog.add("","Вы нерешительно смотрите друг на друга");
        }
        if (opponent.getStance() == BattleStances.ATTACK) {
            calculateAttack(opponent, Player.getInstance());
        }
        else if (opponent.getStance() == BattleStances.POWER_ATTACK) {
            calculatePowerAttack(opponent, Player.getInstance());
        }
        else if (opponent.getStance() == BattleStances.AIMED_ATTACK) {
            calculateAimedAttack(opponent, Player.getInstance());
        }
    }

    private void calculateAttack(Person attacker, Person defender) {
        battleLog.add("",attacker.caption+" атакует противника");
        if (defender.getStance() == BattleStances.DODGE &&                                                                      //шанс увернуться от обычной атаки зависит от разницы ловкости сражающихся
            (RGame.random(100)<Balanser.baseDodgeChance+(defender.getDexterity()[1]-attacker.getDexterity()[1])*Balanser.dodgePerDexterity)) {
            battleLog.add("","но "+defender.caption+" уворачивается.");
        }
        else if (defender.getStance() == BattleStances.DEFENCE) {                                                               //защита от обычной атаки увеличивается в защитной стойке
            damage = attacker.getDamage()*(100+Balanser.damagePerStrMultiplyer)/100;
            damage = damage-defender.getDefence()*(100+Balanser.defenceStanceMultiplier/100)*(Balanser.defenceEffectiveness/100);
            damage = damage<0? 0:damage;
            defender.addHP(-damage);
            battleLog.add("", defender.caption+" отражает часть урона.");
        }
        else {                                                                                                                 //если нет активной защиты, учитывается пассивная
            damage = attacker.getDamage()*(100+Balanser.damagePerStrMultiplyer)/100;
            damage = damage-defender.getDefence()*Balanser.defenceEffectiveness/100;
            damage = damage<0? 0:damage;
            defender.addHP(-damage);
            battleLog.add("", "и наносит уверенный удар.");
        }
    }

    private void calculatePowerAttack(Person attacker, Person defender) {
        battleLog.add("",attacker.caption+" наносит мощный удар");
        if (defender.getStance() == BattleStances.DODGE &&                                                                      //шанс уворота от силовой атаки повышен
                (RGame.random(100)<(Balanser.baseDodgeChance+(defender.getDexterity()[1]-attacker.getDexterity()[1])*Balanser.dodgePerDexterity)*((100+Balanser.psDodgeChance)/100))) {
            battleLog.add("","но "+defender.caption+" уворачивается.");
        }
        else if (defender.getStance() == BattleStances.DEFENCE) {                                                               //активная защита работает так же, как и при обычной атаке
            damage = attacker.getDamage()*(100+Balanser.damagePerStrMultiplyer)/100;
            damage = damage-defender.getDefence()*(100+Balanser.defenceStanceMultiplier/100)*(Balanser.defenceEffectiveness/100);
            damage = damage*(100+Balanser.powerStrikeDamage)/100;
            damage = damage<0? 0:damage;
            defender.addHP(-damage);
            battleLog.add("", defender.caption+" стойко принимает удар.");
        }
        else {                                                                                                                  //пассивная защита работает так же, как и при обычной атаке
            damage = attacker.getDamage()*(100+Balanser.damagePerStrMultiplyer)/100;
            damage = damage-defender.getDefence()*Balanser.defenceEffectiveness/100;
            damage = damage*(100+Balanser.powerStrikeDamage)/100;
            damage = damage<0? 0:damage;
            defender.addHP(-damage);
            battleLog.add("", "и обрушивает всю мощь на противника.");
        }
    }

    private void calculateAimedAttack(Person attacker, Person defender) {
        battleLog.add("",attacker.caption+" метит в уязвимое место");
        if (defender.getStance() == BattleStances.DODGE) {                                                                  //от меткой атаки не увернуться
            battleLog.add("","и "+defender.caption+" тщетно пытается увернуться.");
        }
        if (defender.getStance() == BattleStances.DEFENCE) {                                                               //защита эффективна против меткой атаки
            damage = attacker.getDamage()*(100+Balanser.damagePerStrMultiplyer)/100;
            damage = damage-defender.getDefence()*(100+Balanser.defenceStanceMultiplier/100)*((100+Balanser.asDefenceMultiplier)/100)*(Balanser.defenceEffectiveness/100);
            damage = damage<0? 0:damage;
            defender.addHP(-damage);
            battleLog.add("", defender.caption+" отражает атаку.");
        }
        else {                                                                                                             //если нет активной защиты, пассивная защита игнорируется
            damage = attacker.getDamage()*(100+Balanser.damagePerStrMultiplyer)/100;
            damage = damage*(100+Balanser.aimedStrikeDamage)/100;
            damage = damage<0? 0:damage;
            defender.addHP(-damage);
            battleLog.add("", "удар настигает цель.");
        }
    }

    private boolean checkOpponents() {
        //Проверка, все ли живы
        if (Player.getInstance().getHealth()[1]<=0) {
            loseBattle();
            return false;
        }
        else if (opponent.getHealth()[1]<=0) {
            winBattle();
            return false;
        }
        return true;
    }

    private void loseBattle() {
        RGame.getInstance().closeInfoScreen();
        RGame.getInstance().gameOver();
    }

    private void winBattle() {
        Player.getInstance().addExpPoints(opponent.getLevel());
        opponent.dropAll();
        if (opponent instanceof WildLife) {((WildLife)opponent).dropBody();}
        if (opponent instanceof M_Boss) {RGame.getInstance().setBossDefeated();}
        LevelManager.getInstance().removeGameObject(opponent);
        RGame.getInstance().closeInfoScreen();
    }

    public void showInfo() {
        showPlayerInfo();
        showOpponentInfo();
    }

    private void showPlayerInfo() {
        int p = 4;
        Screen.getInstance().label(3,p, Color.DARKGRAY, "Игрок:",Color.BLACK);
        Screen.getInstance().label(3,p+=1, Color.DARKGRAY, "Уровень "+Player.getInstance().getLevel(),Color.BLACK);
        Screen.getInstance().label(3,p+=1, Color.DARKGRAY, "Сила "+Player.getInstance().getStrength()[0]+"("+Player.getInstance().getStrength()[1]+")",Color.BLACK);
        Screen.getInstance().label(3,p+=1, Color.DARKGRAY, "Ловкость "+Player.getInstance().getDexterity()[0]+"("+Player.getInstance().getDexterity()[1]+")",Color.BLACK);
        Screen.getInstance().label(3,p+=1, Color.DARKGRAY, "Выносливость "+Player.getInstance().getVitality()[0]+"("+Player.getInstance().getVitality()[1]+")",Color.BLACK);
        Screen.getInstance().label(3,p+=2, Color.DARKGRAY, "Раны:",Color.BLACK);
        LevelManager.getInstance().getHealthBar().draw(3,p+=1);
    }

    private void showOpponentInfo() {
        int p = 1;
        Screen.getInstance().label(RGame.SCREEN_WIDTH-opponent.caption.length()-3,p, Color.DARKGRAY, opponent.caption,Color.BLACK);
        Screen.getInstance().label(RGame.SCREEN_WIDTH-opponent.info.length()-3,p+=1, Color.DARKGRAY, opponent.info,Color.BLACK);
        String statInfo;
        String levelInfo;
        switch (maxStat(opponent)) {
            case STRENGTH: {
                statInfo = "крепкий,";
                break;
            }
            case DEXTERITY: {
                statInfo = "жилистый,";
                break;
            }
            default: {
                statInfo = "упитанный,";
                break;
            }
        }
        if (opponent.getLevel()-Player.getInstance().getLevel()<-2) {levelInfo = "совершенно безобидный,";}
        else if (opponent.getLevel()-Player.getInstance().getLevel()<-0) {levelInfo = "выглядит неопасным,";}
        else if (opponent.getLevel()-Player.getInstance().getLevel()<1) {levelInfo = "серьёзный,";}
        else if (opponent.getLevel()-Player.getInstance().getLevel()<3) {levelInfo = "опасный,";}
        else {levelInfo = "В глазах его смерть";}
        Screen.getInstance().label(RGame.SCREEN_WIDTH-statInfo.length()-13,p+=1, Color.DARKGRAY, "Противник "+statInfo,Color.BLACK);
        Screen.getInstance().label(RGame.SCREEN_WIDTH-levelInfo.length()-3,p+=1, Color.DARKGRAY, levelInfo,Color.BLACK);
        Screen.getInstance().label(RGame.SCREEN_WIDTH-hpInfo(opponent).length()-3,p+=1, Color.DARKGRAY, hpInfo(opponent),Color.BLACK);
    }

    private String hpInfo(Person person) {
        if (person.getHealth()[1]*100/person.getHealth()[0]==100) {return "здоров как бык";}
        else if (person.getHealth()[1]*100/person.getHealth()[0]>75) {return "слегка помят";}
        else if (person.getHealth()[1]*100/person.getHealth()[0]>50) {return "с раной";}
        else if (person.getHealth()[1]*100/person.getHealth()[0]>30) {return "потрёпан в бою";}
        else if (person.getHealth()[1]*100/person.getHealth()[0]>20) {return "шатается";}
        else {return "еле живой";}
    }

    private Stats maxStat(Person person) {
        if (person.getStrength()[1]>person.getDexterity()[1]&&person.getStrength()[1]>person.getVitality()[1]) {return Stats.STRENGTH;}
        if (person.getDexterity()[1]>person.getStrength()[1]&&person.getDexterity()[1]>person.getVitality()[1]) {return Stats.DEXTERITY;}
        return Stats.VITALITY;
    }

    public void runAway() {
        if (Player.getInstance().getRunCooldown()<=0) {
            Player.getInstance().setRunCooldown(Balanser.runCooldown);
            if (LevelManager.getInstance().currentLevel<=1) {RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-15,RGame.SCREEN_HEIGHT-1,"Некуда бежать");}
            else if (RGame.random(100)<Balanser.runChance) {
                RGame.getInstance().closeInfoScreen();
                int runDirection = RGame.getInstance().isBossDefeated()? 1:-1;
                LevelManager.getInstance().currentLevel+=runDirection;
                LevelManager.getInstance().createNewLevel(LevelManager.getInstance().currentLevel);
            }
            else {RGame.getInstance().setMessage(RGame.SCREEN_WIDTH-19,RGame.SCREEN_HEIGHT-1,"Не удалось сбежать");}
        }
    }

    public static BattleManager getInstance() {
        if (instance == null) {instance = new BattleManager();}
        return instance;
    }

}

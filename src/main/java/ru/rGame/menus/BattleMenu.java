package ru.rGame.menus;

import com.javarush.engine.cell.Key;
import ru.rGame.BattleManager;
import ru.rGame.RGame;
import ru.rGame.objects.Player;

public class BattleMenu extends Menu {

    public BattleMenu() {
        super(RGame.SCREEN_WIDTH-14, 7);
        setLinesCount(6);
        refresh();
    }

    public void refresh() {
        this.clear();
        add("BM_Attack", " Атаковать ");
        if (Player.getInstance().getPowerStrikeCooldown()<=0) {add("BM_PStrike", "Мощный удар");}
        if (Player.getInstance().getAimedStrikeCooldown()<=0) {add("BM_AStrike", "Меткий удар");}
        add("BM_Defence", "Защищаться ");
        add("BM_Dodge", "Увернуться ");
        if (Player.getInstance().getRunCooldown()<=0) {add("BM_Run", "  Сбежать  ");}
        setMenuIndex(0);
    }

    @Override
    public void use() {
        if (getActiveName().equals("BM_Run")) {
            BattleManager.getInstance().runAway();
        }
        else {
            switch (getActiveName()) {
                case "BM_Attack": {
                    Player.getInstance().attack();
                    break;
                }
                case "BM_PStrike": {
                    Player.getInstance().powerAttack();
                    break;
                }
                case "BM_AStrike": {
                    Player.getInstance().aimedAttack();
                    break;
                }
                case "BM_Defence": {
                    Player.getInstance().defence();
                    break;
                }
                case "BM_Dodge": {
                    Player.getInstance().dodge();
                    break;
                }
            }
            BattleManager.getInstance().makeTurn();
        }
        refresh();
    }

    @Override
    public void keyPressed(Key key) {
        super.keyPressed(key);
        BattleManager.getInstance().showInfo();
    }
}

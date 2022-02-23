package ru.rGame.menus;

import com.javarush.engine.cell.Key;
import ru.rGame.PlayerManager;
import ru.rGame.RGame;
import ru.rGame.enums.Stats;
import ru.rGame.objects.Player;

public class StatsMenu extends Menu{
    int lvlUpPoints;

    public StatsMenu() {
        super(3, RGame.SCREEN_HEIGHT-8);
        super.setLinesCount(8);
        this.lvlUpPoints = Player.getInstance().getLvlUpPoints();
        String up = "";
        if (lvlUpPoints>0) {up = "(Повысить)";}
        add("SM_Strength", "Сила: "+ Player.getInstance().getStrength()[0]+"("+Player.getInstance().getStrength()[1]+")"+up);
        add("SM_Dexterity", "Ловкость: "+ Player.getInstance().getDexterity()[0]+"("+Player.getInstance().getDexterity()[1]+")"+up);
        add("SM_Vitality", "Выносливость: "+ Player.getInstance().getVitality()[0]+"("+Player.getInstance().getVitality()[1]+")"+up);
        add("SM_Exit", "Закрыть");
    }

    @Override
    public void use() {
        Stats stat=Stats.STRENGTH;
        if (getActiveName().equals("SM_Exit")) {
            RGame.getInstance().closeInfoScreen();
        }
        else {
        if (lvlUpPoints>0) {
            switch (getActiveName()) {
                case "SM_Strength": {
                    stat = Stats.STRENGTH;
                    break;
                }
                case "SM_Dexterity": {
                    stat = Stats.DEXTERITY;
                    break;
                }
                case "SM_Vitality": {
                    stat = Stats.VITALITY;
                    break;
                }
            }
            Player.getInstance().upStat(stat);
            lvlUpPoints = Player.getInstance().getLvlUpPoints();
            refresh();
        }
        }
    }

    @Override
    public void keyPressed(Key key) {
        super.keyPressed(key);
        PlayerManager.getInstance().showInfo();
    }

    private void refresh() {
        this.lvlUpPoints = Player.getInstance().getLvlUpPoints();
        String up = "";
        if (lvlUpPoints>0) {up = "(Повысить)";}
        menuItems.set(0, new String[] {"SM_Strength", "Сила: "+ Player.getInstance().getStrength()[0]+"("+Player.getInstance().getStrength()[1]+")"+up});
        menuItems.set(1, new String[] {"SM_Dexterity", "Ловкость: "+ Player.getInstance().getDexterity()[0]+"("+Player.getInstance().getDexterity()[1]+")"+up});
        menuItems.set(2, new String[] {"SM_Vitality", "Выносливость: "+ Player.getInstance().getVitality()[0]+"("+Player.getInstance().getVitality()[1]+")"+up});
    }

    public void setLvlUpPoints(int lvlUpPoints) {
        this.lvlUpPoints = lvlUpPoints;
    }
}

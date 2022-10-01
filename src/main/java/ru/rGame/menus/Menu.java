package ru.rGame.menus;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Key;
import ru.rGame.Screen;

import java.util.ArrayList;


public abstract class Menu {
    protected Color menuColor = Color.DARKGRAY;
    protected Color menuColorChecked = Color.GREEN;
    protected Color menuTextColor = Color.BLACK;
    protected Color menuTextColorInactive = Color.GRAY;

    protected ArrayList<String[]> menuItems = new ArrayList<>();
    private int menuIndex = 0;
    private int offset;
    private int linesCount = 4;
    private final int x;
    private final int y;
    private boolean active = false;

    public Menu (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void removeItem (String item) {
        menuItems.remove(item);
    }

    public void add (String name, String caption) {
        menuItems.add(new String[] {name, caption});
    }

    public void draw () {
        String uText = genMenuItem("^");
        String dText = genMenuItem("v");
        Color textColor = active?menuTextColor:menuTextColorInactive;
        int count = Math.min(menuItems.size(), linesCount);
        for (int i=offset; i<count+offset; i++) {
            if (i==menuIndex&&active) {
                Screen.getInstance().label(x, y+i-offset, menuColorChecked, menuItems.get(i)[1], textColor);
            }
            else {
                Screen.getInstance().label(x, y+i-offset, menuColor, menuItems.get(i)[1], textColor);
            }
        }
        if (offset>0) {Screen.getInstance().label(x, y, menuColor, uText, textColor);}
        if ((offset+linesCount)<menuItems.size()) {Screen.getInstance().label(x, y+linesCount-1, menuColor, dText, textColor);}
    }

    public void keyPressed (Key key) {
        switch (key) {
            case UP: {
                if (menuIndex==0) {
                    menuIndex = menuItems.size()-1;
                    int count = Math.min(menuItems.size(), linesCount);
                    offset = menuItems.size()-count;
                }
                else menuIndex--;
                if (menuIndex-offset<=1&&menuIndex>0) {offset = menuIndex-1;}
                break;
            }
            case DOWN: {
                if (menuIndex==menuItems.size()-1) {
                    menuIndex = 0;
                    offset = 0;
                }
                else menuIndex++;
                if (menuIndex-offset>=linesCount-1&&menuIndex<menuItems.size()-1) {offset = menuIndex-linesCount+2;}
                break;
            }
        }
    }

    private String genMenuItem(String ch) {
        StringBuilder str= new StringBuilder();
        String maxItem="";
        for (String[] item:menuItems) {
            if (item[1].length()>maxItem.length()) {maxItem = item[1];}
        }
        if (ch.length()>maxItem.length()) {return ch;}
        else {
            for (int i = 0; i < (maxItem.length()-ch.length())/2; i++) {
                str.append(" ");
            }
            str.append(ch);
            for (int i = str.length(); i < maxItem.length(); i++) {
                str.append(" ");
            }
        }
        return str.toString();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    public void clear() {
        menuItems.clear();
        menuIndex = 0;
        offset = 0;
    }

    public abstract void use();

    public boolean isActive() {
        return active;
    }

    public String getActiveName () {
        return menuItems.get(menuIndex)[0];
    }

    public String getActiveTitle () {
        return menuItems.get(menuIndex)[1];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }
}

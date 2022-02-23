package ru.rGame.objects;

import com.javarush.engine.cell.Color;
import ru.rGame.RGame;
import ru.rGame.enums.ObjectTypes;

public abstract class GameObject {
    public int x;
    public int y;
    protected ObjectTypes type;
    protected Color color;
    protected Color bgColor;
    public String caption="";
    public String info;

    public GameObject(ObjectTypes type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.bgColor = type.getBg();
    }

    public void draw() {
        RGame.getInstance().setCellValueEx(x+ RGame.SHIFT_X, y+ RGame.SHIFT_Y, bgColor, type.getShape(), color);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public ObjectTypes getType() {
        return type;
    }

}

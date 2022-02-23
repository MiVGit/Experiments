package ru.rGame.objects;

public class Message {
    private int x;
    private int y;
    private String message="";

    public Message() {
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMessage() {
        return message;
    }
}

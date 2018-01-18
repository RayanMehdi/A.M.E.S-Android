package com.example.iem.ames.model.element;

/**
 * Created by Jo' on 16/01/2018.
 */

public class Button {
    private String filename;
    private int nextEventIndex;
    private double x;
    private double y;

    public String getFilename() {
        return filename;
    }

    public int getNextEventIndex() {
        return nextEventIndex;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setNextEventIndex(int nextEventIndex) {
        this.nextEventIndex = nextEventIndex;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Button(String filename, int nextEventIndex, double x, double y) {
        this.filename = filename;
        this.nextEventIndex = nextEventIndex;
        this.x = x;
        this.y = y;
    }
}

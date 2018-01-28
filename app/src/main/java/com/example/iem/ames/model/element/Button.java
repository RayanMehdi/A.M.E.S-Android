package com.example.iem.ames.model.element;

import android.content.Context;

import com.example.iem.ames.AMESApplication;

/**
 * Created by Jo' on 16/01/2018.
 */

public class Button {
    private String filename;
    private int nextEventIndex;
    private double x;
    private double y;
    private double scaleX, scaleY;

    public int getID() {
        Context context = AMESApplication.application().getAMESManager().getContextView();
        return context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
    }

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

    public Button(String filename, int nextEventIndex, double x, double y, double scaleX, double scaleY) {
        this.filename=filename.substring(0, filename.length()-4).toLowerCase();
        this.nextEventIndex = nextEventIndex;
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }
}

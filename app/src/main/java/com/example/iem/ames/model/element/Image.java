package com.example.iem.ames.model.element;

import android.content.Context;

import com.example.iem.ames.AMESApplication;

/**
 * Created by Jo' on 16/01/2018.
 */

public class Image {
    private String filename;
    private double x;
    private double y;
    private boolean isGIF;
    private int duration;

    public Image(String filename, double x, double y, boolean isGIF, int duration) {
        this.filename = (isGIF) ? filename : filename.substring(0, filename.length()-4);
        this.x = x;
        this.y = y;
        this.isGIF = isGIF;
        this.duration = duration;
    }

    public int getID() {
        Context context = AMESApplication.application().getAMESManager().getContextView();
        return context.getResources().getIdentifier(filename, "raw", context.getPackageName());
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getX() {
        return x;
    }


    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isGIF() {
        return isGIF;
    }

    public void setGIF(boolean GIF) {
        isGIF = GIF;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

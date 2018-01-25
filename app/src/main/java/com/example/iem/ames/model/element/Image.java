package com.example.iem.ames.model.element;

import android.content.Context;

import com.example.iem.ames.AMESApplication;

/**
 * Created by Jo' on 16/01/2018.
 */

public class Image {
    protected String filename;
    protected double x;
    protected double y;
    protected boolean isGIF;

    public Image(String filename, double x, double y, boolean isGIF) {
        this.filename = (isGIF) ? filename : filename.substring(0, filename.length()-4);
        this.filename=this.filename.toLowerCase();
        this.x = x;
        this.y = y;
        this.isGIF = isGIF;
    }

    public int getID() {
        Context context = AMESApplication.application().getAMESManager().getContextView();
        return context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
    }

    public String getFilename() {
        return filename;
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
}

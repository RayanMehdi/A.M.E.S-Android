package com.example.iem.ames.model.element;

import android.content.Context;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 23/01/2018.
 */

public class ImageAnimation extends Image{

    private double duration;
    private int numberOfFile;
    private int numberOfRepeat;
    private double translationX,translationY, translationZ, movementDuration;

    public ImageAnimation(String filename, double x, double y, boolean isGIF, double duration, int numberOfFile, int numberOfRepeat, double translationX, double translationY, double translationZ, double movementDuration, double scaleX, double scaleY) {
        super(filename, x, y, isGIF, scaleX, scaleY);
        this.duration = duration;
        this.numberOfFile = numberOfFile;
        this.numberOfRepeat = numberOfRepeat;
        this.translationX = translationX;
        this.translationY = translationY;
        this.translationZ = translationZ;
        this.movementDuration = movementDuration;
    }

    @Override
    public int getID() {
        Context context = AMESApplication.application().getAMESManager().getContextView();
        return context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
    }

    public double getDuration() {
        return duration;
    }

    public int getNumberOfFile() {
        return numberOfFile;
    }

    public int getNumberOfRepeat() {
        return numberOfRepeat;
    }

    public double getTranslationX() {
        return translationX;
    }

    public double getTranslationY() {
        return translationY;
    }

    public double getTranslationZ() {
        return translationZ;
    }

    public double getMovementDuration() {
        return movementDuration;
    }
}

package com.example.iem.ames.model.element;

import android.content.Context;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 23/01/2018.
 */

public class ImageAnimation extends Image{

    private int duration;
    private int numberOfFile;
    private int numberOfRepeat;

    public ImageAnimation(String filename, double x, double y, boolean isGIF, int duration, int numberOfFile, int numberOfRepeat) {
        super(filename, x, y, isGIF);
        this.duration = duration;
        this.numberOfFile = numberOfFile;
        this.numberOfRepeat = numberOfRepeat;

    }

    @Override
    public int getID() {
        Context context = AMESApplication.application().getAMESManager().getContextView();
        return context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
    }

    public int getDuration() {
        return duration;
    }

    public int getNumberOfFile() {
        return numberOfFile;
    }

    public int getNumberOfRepeat() {
        return numberOfRepeat;
    }
}

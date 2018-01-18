package com.example.iem.ames.model.element;

import android.widget.RelativeLayout;

/**
 * Created by iem on 18/01/2018.
 */

public class Screen {
    private RelativeLayout relativeLayout;
    private int height, width;

    public Screen(RelativeLayout relativeLayout, int height, int width) {
        this.relativeLayout = relativeLayout;
        this.height = height;
        this.width = width;
    }


    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    public int getHeight() {
        return height;
    }

    public void setHeigth(int heigth) {
        this.height = heigth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

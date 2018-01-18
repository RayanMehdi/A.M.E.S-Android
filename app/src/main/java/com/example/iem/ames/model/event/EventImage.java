package com.example.iem.ames.model.event;

import com.example.iem.ames.model.element.Image;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class EventImage extends AMESEvent {
    private ArrayList<Image> images;

    public EventImage(String name, String type, double delay, ArrayList<Image> images) {
        super(name, type, delay);
        this.images = images;
    }

    @Override
    public void run() {

    }
}

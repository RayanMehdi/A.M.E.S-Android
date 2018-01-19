package com.example.iem.ames.model.event;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Image;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class EventImage extends AMESEvent {
    private Image image;

    public EventImage(String name, String type, double delay, Image image) {
        super(name, type, delay);
        this.image = image;
    }

    @Override
    public void run() {
        AMESApplication.application().getAMESManager().getImageManager().displayNewImage(image);
    }
}

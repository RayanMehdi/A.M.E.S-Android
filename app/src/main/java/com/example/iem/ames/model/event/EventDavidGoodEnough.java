package com.example.iem.ames.model.event;

import android.widget.ImageView;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.ImageAnimation;

/**
 * Created by Thomas on 29/01/2018.
 */

public class EventDavidGoodEnough extends AMESEvent{
    private ImageAnimation image;
    private ImageView imageView;

    public EventDavidGoodEnough(String name, String type, double delay, ImageAnimation image) {
        super(name, type, delay);
        this.image = image;
        imageView=null;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void run() {
        AMESApplication.application().getAMESManager().getImageManager().displayFadeInImageAnimation(image);
    }

    @Override
    public void stop() {
        AMESApplication.application().getAMESManager().getImageManager().destroyImageView(this.getName(), this.getType());
    }

}

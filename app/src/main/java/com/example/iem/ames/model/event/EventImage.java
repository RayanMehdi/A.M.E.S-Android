package com.example.iem.ames.model.event;

import android.widget.ImageView;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.ImageAnimation;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class EventImage extends AMESEvent {
    private Image image;
    private ImageView imageView;

    public EventImage(String name, String type, double delay, Image image) {
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
        if(image.isGIF()){
            AMESApplication.application().getAMESManager().getImageManager().displayAnimation(((ImageAnimation) image));
        }
        else{
            AMESApplication.application().getAMESManager().getImageManager().displayNewImage(image);
        }
    }

    @Override
    public void stop() {
        AMESApplication.application().getAMESManager().getImageManager().destroyImageView(this.getName());
    }
}

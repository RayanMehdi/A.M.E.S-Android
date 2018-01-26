package com.example.iem.ames.model.event;

import android.hardware.Camera;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.element.Image;

import java.util.ArrayList;

/**
 * Created by iem on 17/01/2018.
 */

public class EventCamera extends AMESEvent {
    private boolean rear_front;
    private Image overlayImage;
    private Camera camera;

    public EventCamera(String name, String type, double delay, boolean rear_front, Image overlayImage) {
        super(name, type, delay);

        this.rear_front = rear_front;
        this.overlayImage = overlayImage;
    }


    public boolean isRear_front() {
        return rear_front;
    }

    public void setRear_front(boolean rear_front) {
        this.rear_front = rear_front;
    }

    public Image getOverlayImage() {
        return overlayImage;
    }

    public void setOverlayImage(Image overlayImage) {
        this.overlayImage = overlayImage;
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void run() {
        // Display buttons
        AMESApplication.application().getAMESManager().getCameraManager().displayCamera(this, getDelayInMillisecond());
    }

    @Override
    public void stop() {
        AMESApplication.application().getAMESManager().getCameraManager().destroy();
    }


}
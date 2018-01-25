package com.example.iem.ames.model.event;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 25/01/2018.
 */

public class EventFlash extends AMESEvent {
    public EventFlash(String name, String type, double delay) {
        super(name, type, delay);
    }

    @Override
    public void run() {
        AMESApplication.application().getAMESManager().getFlashManager().turnLightOn(this);
    }

    @Override
    public void stop() {
        AMESApplication.application().getAMESManager().getFlashManager().turnLightOff();
    }
}

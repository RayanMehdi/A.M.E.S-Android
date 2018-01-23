package com.example.iem.ames.model.event;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 22/01/2018.
 */

public class EventCheckHeadphones extends AMESEvent {

    public EventCheckHeadphones(String name, String type, double delay) {
        super(name, type, delay);
    }

    @Override
    public void run() {
        AMESApplication.application().getAMESManager().getCheckHeadphonesManager().checkHeadphones();
    }

    @Override
    public void stop() {

    }
}

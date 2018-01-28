package com.example.iem.ames.model.event;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 25/01/2018.
 */

public class EventMicro extends AMESEvent{


    public EventMicro(String name, String type, double delay) {
        super(name, type, delay);
    }

    @Override
    public void run() {
        AMESApplication.application().getAMESManager().getMicroManager().zouloutageDeMicro();
    }

    @Override
    public void stop() {

    }
}

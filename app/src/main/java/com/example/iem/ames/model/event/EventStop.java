package com.example.iem.ames.model.event;

import android.app.usage.UsageEvents;
import android.os.CountDownTimer;

import com.example.iem.ames.AMESApplication;

import java.util.ArrayList;

/**
 * Created by iem on 22/01/2018.
 */

public class EventStop extends AMESEvent {

    public EventStop(String name, String type, double delay) {
        super(name, type, delay);

    }
    @Override
    public void run() {
        stop();
    }

    @Override
    public void stop() {
        AMESApplication.application().getAMESManager().getStopManager().stopEvent(super.getName(), super.getType(), super.getDelay());
    }
}

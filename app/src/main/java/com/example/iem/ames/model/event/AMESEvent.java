package com.example.iem.ames.model.event;

import android.util.Log;

/**
 * Created by Jo' on 16/01/2018.
 */

public abstract class AMESEvent {
    private String name;
    private String type;
    private double delay;

    public AMESEvent(String name, String type, double delay) {
        this.name = name;
        this.type = type;
        this.delay = delay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDelay() {
        return delay;
    }

    public long getDelayInMillisecond() {
        return (long)delay * 1000;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public abstract void run();
}



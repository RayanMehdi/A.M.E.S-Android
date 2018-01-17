package com.example.iem.ames.model.event;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESEvent {
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

    public void setDelay(double delay) {
        this.delay = delay;
    }
}



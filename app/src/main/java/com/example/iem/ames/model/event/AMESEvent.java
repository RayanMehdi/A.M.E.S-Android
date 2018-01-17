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
}



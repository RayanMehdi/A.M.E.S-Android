package com.example.iem.ames.model.event;

/**
 * Created by iem on 18/01/2018.
 */

public class EventBackgroundSound extends AMESEvent {
    //TODO constructor
    public EventBackgroundSound(String name, String type, double delay) {
        super(name, type, delay);
    }

    @Override
    public void run() {
    }
    @Override
    public  void stop(){

    }
}

package com.example.iem.ames.model.event;

/**
 * Created by iem on 18/01/2018.
 */

public class EventSound extends AMESEvent {
    private int soundId;

    public EventSound(String name, String type, double delay, int soundId) {
        super(name, type, delay);
        this.soundId = soundId;
    }

    @Override
    public void run() {

    }
}

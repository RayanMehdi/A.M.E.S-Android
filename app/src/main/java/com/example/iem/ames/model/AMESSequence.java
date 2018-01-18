package com.example.iem.ames.model;

import com.example.iem.ames.model.event.AMESEvent;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESSequence {
    private ArrayList<AMESEvent> events;
    private  int nextEventIndex;
    private String backgroundSound;

    public AMESSequence() {
        this.events = new ArrayList<AMESEvent>();
    }

    public ArrayList<AMESEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<AMESEvent> events) {
        for (AMESEvent amesEvent: events) {
            this.events.add(amesEvent);
        }
    }

    public void addEvent(AMESEvent event) {
        this.events.add(event);
    }

    public int getNextEventIndex() {
        return nextEventIndex;
    }

    public void setNextEventIndex(int nextEventIndex) {
        this.nextEventIndex = nextEventIndex;
    }

    public String getBackgroundSound() {
        return backgroundSound;
    }

    public void setBackgroundSound(String backgroundSound) {
        this.backgroundSound = backgroundSound;
    }

    public String toString(){
        String res = "[\n";
        for (AMESEvent amesEvent: this.events) {
            res += "{\n  Name : " + amesEvent.getName() + "\n"
                + "  Type : " + amesEvent.getType() + "\n"
                + "  Delay : " + amesEvent.getDelay() + "\n},\n";
        }
        res += "]";
        return res;
    }
}

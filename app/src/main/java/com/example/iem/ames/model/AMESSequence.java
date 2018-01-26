package com.example.iem.ames.model;

import android.os.CountDownTimer;
import android.util.Log;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.event.AMESEvent;
import com.example.iem.ames.model.event.EventBackgroundSound;
import com.example.iem.ames.model.event.EventButton;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESSequence {
    private ArrayList<AMESEvent> events;
    private EventBackgroundSound backgroundSound;
    private int currentIndex;

    public AMESSequence() {
        this.events = new ArrayList<AMESEvent>();
        this.currentIndex = 0;
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

    public EventBackgroundSound getBackgroundSound() {
        return backgroundSound;
    }

    public void setBackgroundSound(EventBackgroundSound backgroundSound) {
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

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void firstRun(){
        // When sequence is starting, running background sound
        if(backgroundSound.getClass() == EventBackgroundSound.class)
            runEvent:backgroundSound.run();
        this.run();
    }

    public void run(){
            // Run event
        if(currentIndex < events.size()){
            Log.d("SEQ", String.valueOf(this.currentIndex));
            events.get(currentIndex).run();
        }
        else {
            AMESApplication.application().getAMESManager().getCurrentGame().preparNextSequence();
            int nextSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex() +1;
            AMESApplication.application().getAMESManager().getCurrentGame().setCurrentSequenceIndex(nextSequenceIndex);
        }

    }
}

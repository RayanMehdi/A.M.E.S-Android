package com.example.iem.ames.model;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.event.AMESEvent;
import com.example.iem.ames.model.event.EventBackgroundSound;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESSequence {
    private ArrayList<AMESEvent> events;
    private  int nextEventIndex;
    private EventBackgroundSound backgroundSound;

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

    public void firstRun(){
        // When sequence is starting, running background sound
        if(backgroundSound.getClass() == EventBackgroundSound.class)
            runEvent:backgroundSound.run();
        this.run();
    }

    public void run(){
        // Firing successive events with a timer
        if(this.nextEventIndex < this.events.size())
        {
            AMESEvent currentEvent = this.events.get(this.nextEventIndex);
            this.nextEventIndex++;
            currentEvent.run();
            if (currentEvent.getDelay() > 0.0) // If event has a finite duration
            {
                // wait the delay of the current event
                try
                {
                    Thread.sleep(currentEvent.getDelayInMillisecond());
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // Stop looping sound her
        else
        {
            this.nextEventIndex = 0; // reseting sequence, in case it has to be played several times.
            // Modifying the background event to stop sound. Will be called at the end of the sequence
            if (backgroundSound != null) {
                backgroundSound.stop();
            }
            // Signal the game that sequence is over.
            AMESApplication.application().getAMESManager().getCurrentGame().run();
        }
    }
}

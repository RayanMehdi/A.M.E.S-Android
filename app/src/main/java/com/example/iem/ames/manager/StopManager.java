package com.example.iem.ames.manager;

import android.os.CountDownTimer;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.event.AMESEvent;

import java.util.ArrayList;

/**
 * Created by iem on 22/01/2018.
 */

public class StopManager {
    private ArrayList<AMESEvent> events;

    public StopManager() {

    }

    public void stopEvent(String name, String type, double delay){
        this.events = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex()).getEvents();
        boolean isfinish=false;
        final int currentSequenceIndex=AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        int start = currentEventIndex-1;
        for(int position = start; !isfinish && position >=0; position-- ){
            if(events.get(position).getType().equals("animated text") && type.equals("animated text")){
                events.get(position).stop();
                isfinish = true;

            } else {
                if (events.get(position).getName()==name) {
                    events.get(position).stop();
                    isfinish = true;
                }
            }
        }


        new CountDownTimer((long) (delay*1000), 1000) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                int newindex = currentEventIndex+1;
                AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(newindex);
                AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
            }
        }.start();

    }
}

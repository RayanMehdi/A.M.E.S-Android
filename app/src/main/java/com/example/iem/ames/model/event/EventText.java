package com.example.iem.ames.model.event;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Text;

/**
 * Created by Jo' on 16/01/2018.
 */

public class EventText extends AMESEvent{
    private Text text;

    public EventText(String name, String type, double delay, Text text) {
        super(name, type, delay);
        this.text = text;
    }

    @Override
    public void run() {
        if(!this.getName().equals("hour display")){
            AMESApplication.application().getAMESManager().getTextManager().displayText(text);
        }else{
            AMESApplication.application().getAMESManager().getTextManager().displayHour(text);
        }
    }


    @Override
    public void stop() {
        /*final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        AMESApplication.application().getAMESManager().getTextManager().stop(currentSequenceIndex, currentEventIndex);*/
        try{AMESApplication.application().getAMESManager().getTextManager().destroy();}catch (Exception e){}
    }
}

package com.example.iem.ames.model.event;

import android.content.Context;
import android.util.Log;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 18/01/2018.
 */

public class EventSound extends AMESEvent {
    private String soundName;
    private Context context;
    private boolean infinite;

    public EventSound(String name, String type, double delay, String soundName, boolean infinite) {
        super(name, type, delay);
        this.soundName = soundName.substring(0, soundName.length()-4);
        Log.d("TEST", this.soundName);
        this.infinite=infinite;
        context= AMESApplication.application().getAMESManager().getContextView();
    }

    public int getsoundID(){
        return context.getResources().getIdentifier(soundName, "raw", context.getPackageName());
    }

    public boolean isInfinite() {
        return infinite;
    }

    @Override
    public void run() {
        AMESApplication.application().getAMESManager().getSoundManager().playSound(getsoundID(), this.infinite);
    }
}

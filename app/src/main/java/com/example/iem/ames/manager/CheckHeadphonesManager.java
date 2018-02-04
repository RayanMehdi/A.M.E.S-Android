package com.example.iem.ames.manager;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 22/01/2018.
 */

public class CheckHeadphonesManager {

    private Context context;
    private AudioManager audioManager;

    public CheckHeadphonesManager(Context context) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void checkHeadphones(){
        boolean check = false;
        while(check != true){
            if(audioManager.isWiredHeadsetOn()){
                check = true;
            }
        }
        int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        // Run the next event (with the new current index in sequence)
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(currentEventIndex +   1);
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
    }

}

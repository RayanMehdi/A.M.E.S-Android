package com.example.iem.ames.manager;

import android.os.CountDownTimer;

import com.example.iem.ames.AMESApplication;

/**
 * Created by iem on 25/01/2018.
 */

public class MicroManager {



    public void zouloutageDeMicro(){
        final int currentSequenceIndex= AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        long delay = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond();

        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                runNextEvent(currentSequenceIndex, currentEventIndex);
            }
        }.start();
    }

    private void runNextEvent(int currentSequenceIndex, int currentEventIndex){
        int nexEvent = currentEventIndex+1;
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nexEvent);
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
    }
}

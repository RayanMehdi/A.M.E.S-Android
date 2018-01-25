package com.example.iem.ames.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;

/**
 * Created by iem on 18/01/2018.
 */

public class SoundManager {

    private Context context;
    private MediaPlayer mediaPlayer;

    public SoundManager(Context context) {
        this.context = context;
    }

    public void playSound(int soundID, boolean isInfinite){
        mediaPlayer = MediaPlayer.create(context, soundID);
        mediaPlayer.setLooping(isInfinite);
        mediaPlayer.start();
       final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
       final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        new CountDownTimer(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond(), 1000) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                int nextEvent = currentEventIndex+1;
                AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nextEvent);
                AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();

            }
        }.start();

    }

    public void stopSounds(double delay){
        this.mediaPlayer.stop();


        int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        if (delay > 0.0) // If event has a finite duration
        {
            // wait the delay of the current event
            new CountDownTimer((long)delay, 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {

                }
            }.start();
        }
        currentEventIndex+=1;
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(currentEventIndex);
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
    }
}

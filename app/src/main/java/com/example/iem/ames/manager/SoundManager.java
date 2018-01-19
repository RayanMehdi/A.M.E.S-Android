package com.example.iem.ames.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;

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

        //TODO Gestion du stop
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mediaPlayer.stop();
            }
        }.start();
    }

    public void stopSounds(){
        this.mediaPlayer.stop();
    }
}

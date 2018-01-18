package com.example.iem.ames.manager;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.iem.ames.R;

/**
 * Created by iem on 18/01/2018.
 */

public class SoundManager {

    private Context context;

    public SoundManager(Context context) {
        this.context = context;
    }

    public void playSound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.davidgoodenough_sound);
        mediaPlayer.start();
    }
}

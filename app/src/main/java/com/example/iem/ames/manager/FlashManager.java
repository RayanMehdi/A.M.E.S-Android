package com.example.iem.ames.manager;

import android.hardware.Camera;
import android.os.CountDownTimer;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.event.EventFlash;

/**
 * Created by iem on 25/01/2018.
 */

public class FlashManager {

    private Camera mCamera;

    public FlashManager() {

    }

    public void turnLightOn(EventFlash flash){

        mCamera = AMESApplication.application().getAMESManager().getCameraManager().getmCamera();
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(params);
        mCamera.startPreview();
        new CountDownTimer(flash.getDelayInMillisecond(), 1000) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
                int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
                int nextEvent = currentEventIndex+1;
                AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nextEvent);
                AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
            }
        }.start();
    }

    public void turnLightOff(){
        mCamera = AMESApplication.application().getAMESManager().getCameraManager().getmCamera();
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(params);
        mCamera.startPreview();
    }
}

package com.example.iem.ames.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.util.Log;

import com.example.iem.ames.AMESApplication;

import static android.content.ContentValues.TAG;


/**
 * Created by iem on 22/01/2018.
 */

public class CheckLightManager {


    private Context context;
    private SensorManager sensorManager;
    private boolean light = false;
    private int checkLightIndex;
    public CheckLightManager(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    final SensorEventListener LightSensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(!light){
                if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
                    if(sensorEvent.values[0] < SensorManager.LIGHT_CLOUDY){
                        light = true;
                        Log.d("TEST", "onSensorChanged: " + sensorEvent.values[0]);
                        int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
                        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(checkLightIndex +   1);
                        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
                    }
            }
         }
         else {
                Log.d("TEST", "false: " + sensorEvent.values[0]);
            }
        }




    };
    public void checkLight(){
        int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        this.checkLightIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(
                LightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);


        }

    }




package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.Screen;

/**
 * Created by iem on 22/01/2018.
 */

public class AccelerometerManager implements SensorEventListener{

    private Context context;
    private Screen screen;
    private SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    private float x,y,z;
    //private boolean stop = false;

    public AccelerometerManager(Context context, Screen screen) {
        this.screen = screen;
        this.context = context;
        mSensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];

        /*if(x < 10 && x > 8 && y > -5 && y < 5 && z > -5 && z < 5 && !stop){
            stop = true;

        }*/

        //Log.d("XYZ ", "X: " + x + " Y: " + y + " Z: " + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

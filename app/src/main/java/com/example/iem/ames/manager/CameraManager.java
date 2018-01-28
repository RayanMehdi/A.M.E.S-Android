package com.example.iem.ames.manager;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventCamera;

import java.io.IOException;

/**
 * Created by iem on 24/01/2018.
 */

public class CameraManager implements SurfaceHolder.Callback {
    private Activity activity;
    private Screen screen;
    private int nextEventIndex;

    private SurfaceView surfaceView;
    private Size imageDimension;
    private EventCamera eventCamera;
    private Camera mCamera;
    private SurfaceHolder surfaceHolder;




    public CameraManager(Activity activity, Screen screen) {
        this.activity = activity;
        this.screen = screen;
        this.nextEventIndex = 0;

    }

    public void displayCamera(EventCamera eventCamera, double delay) {
        this.eventCamera = eventCamera;
        surfaceView = new SurfaceView(activity);
        surfaceHolder = surfaceView.getHolder();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        this.screen.getRelativeLayout().addView(surfaceView);

        this.screen.getRelativeLayout().getChildCount();

        //TODO

//        AMESApplication.application().getAMESManager().getImageManager().displayNewImage(eventCamera.getOverlayImage());


        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        final int nextEvent = currentEventIndex+1;
        Log.d("ZAMASEUM", ""+delay);
            // wait the delay of the current event
            new CountDownTimer((long)delay, 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {

                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nextEvent);
                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
                }
            }.start();



    }

    public void selectCamera(){
        for (int camNo = 0; camNo < Camera.getNumberOfCameras(); camNo++) {
            Camera.CameraInfo camInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(camNo, camInfo);

            if (camInfo.facing==(Camera.CameraInfo.CAMERA_FACING_FRONT) && !eventCamera.isRear_front() ) {

                mCamera = Camera.open(camNo);
                zoom();
            }
        }
        if (mCamera == null) {
            // no front-facing camera, use the first back-facing camera instead.
            // you may instead wish to inform the user of an error here...
            mCamera = Camera.open();
        }
    }

    public void destroy(){


        AMESApplication.application().getAMESManager().getImageManager().destroyImageView(eventCamera.getName(), "image");
        ViewGroup vg = (ViewGroup)(screen.getRelativeLayout());
        vg.removeView(this.surfaceView);
//        vg.removeView(screen.getRelativeLayout());
//        screen.getRelativeLayout().setBackgroundColor(AMESApplication.application().getAMESManager().getContextView().getResources().getColor(R.color.black));

        activity.setContentView(screen.getRelativeLayout());


    }

    public void zoom()
    {
        Camera.Parameters params=mCamera.getParameters();
        params.setZoom(params.getMaxZoom());
        mCamera.setParameters(params);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        selectCamera();

        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        surfaceView.setLayoutParams(new FrameLayout.LayoutParams( previewSize.width, previewSize.height, Gravity.CENTER));
        Camera.Parameters param;
        param = mCamera.getParameters();
        param.setPreviewSize(previewSize.width, previewSize.height);
        mCamera.setParameters(param);

        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (IOException t) {
            t.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera=null;
        this.screen.getRelativeLayout().bringToFront();
    }
}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mTextureView = new TextureView(this);
//        mTextureView.setSurfaceTextureListener(this);
//
//        setContentView(mTextureView);
//    }
//

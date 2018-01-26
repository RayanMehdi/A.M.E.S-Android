package com.example.iem.ames.manager;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventCamera;

import java.io.IOException;

/**
 * Created by iem on 24/01/2018.
 */

public class CameraManager implements TextureView.SurfaceTextureListener {
    private Activity activity;
    private Screen screen;
    private int nextEventIndex;

    private TextureView textureView;
    private Size imageDimension;
    private EventCamera eventCamera;
    private Camera mCamera;

    // CAMERA


    public Camera getmCamera() {
        return mCamera;
    }



    public CameraManager(Activity activity, Screen screen) {
        this.activity = activity;
        this.screen = screen;
        this.nextEventIndex = 0;

    }

    public void displayCamera(EventCamera eventCamera, double delay) {
        this.eventCamera = eventCamera;
        textureView = new TextureView(activity);
        textureView.setSurfaceTextureListener(this);

        ViewGroup vg = (ViewGroup)(screen.getRelativeLayout().getParent());
        vg.removeView(screen.getRelativeLayout());
      //  screen.getRelativeLayout().addView(textureView, 0);
        activity.setContentView(textureView);
        //screen.getRelativeLayout().addView(textureView);
        screen.getRelativeLayout().setBackgroundColor(0x00000000);
        activity.addContentView(screen.getRelativeLayout(), screen.getRelativeLayout().getLayoutParams());
        AMESApplication.application().getAMESManager().getImageManager().displayNewImage(eventCamera.getOverlayImage());


         final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        Log.d("ZAMASEUM", ""+delay);
            // wait the delay of the current event
            new CountDownTimer((long)delay, 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {

                    int nextEvent = currentEventIndex+1;
                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nextEvent);
                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
                }
            }.start();



    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, the Camera does all the work for us
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Update your view here!
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

        selectCamera();

        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        textureView.setLayoutParams(new FrameLayout.LayoutParams(

                previewSize.width, previewSize.height, Gravity.CENTER));

        try {
            mCamera.setPreviewTexture(surfaceTexture);
        } catch (IOException t) {
        }

        mCamera.startPreview();
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


        ViewGroup vg = (ViewGroup)(screen.getRelativeLayout());
        vg.removeAllViews();
        //vg.removeView(screen.getRelativeLayout());
        screen.getRelativeLayout().setBackgroundColor(AMESApplication.application().getAMESManager().getContextView().getResources().getColor(R.color.black));
        activity.setContentView(screen.getRelativeLayout());

    }

    public void zoom()
    {
        Camera.Parameters params=mCamera.getParameters();
        params.setZoom(params.getMaxZoom());
        mCamera.setParameters(params);
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

package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ViewPropertyAnimator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.ImageAnimation;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventCamera;
import com.example.iem.ames.model.event.EventImage;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by iem on 18/01/2018.
 */

public class ImageManager {
    private Context context;
    private Screen screen;
    private HashMap<String, ImageView> arrayImage;


    public ImageManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
        arrayImage = new HashMap<String, ImageView>();

    }

    public void displayNewImage(Image img){
        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        final ImageView imageView = new ImageView(this.context);
        try{
            arrayImage.put(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getName(),imageView);
        }catch (Exception e){
            Log.d("IMAGE ERROR", "" + e);
        }

        imageView.setImageResource(img.getID());

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(this.context.getResources(), img.getID(), dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;

        // Setting layout params to our RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);

        // Setting position of our ImageView
        Double x = (img.getScaleX() != 1) ? this.screen.getWidth()*img.getX() - width/2 : 0;
        Double y = (img.getScaleY() != 1) ? this.screen.getHeight()*img.getY() - height/2 : 0;
        Double scaleX = (img.getScaleX() != 0) ? this.screen.getHeight()*img.getScaleX() : height;
        Double scaleY = (img.getScaleY() != 0) ? this.screen.getWidth()*img.getScaleY() : width;
        layoutParams.leftMargin = x.intValue();
        layoutParams.topMargin = this.screen.getHeight() - y.intValue();
        layoutParams.width = scaleX.intValue();
        layoutParams.height = scaleY.intValue();

        // Finally Adding the imageView to RelativeLayout and its position
        this.screen.getRelativeLayout().addView(imageView, layoutParams);
        long delay = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond();

        if(delay>0.0 && AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getType().equals("image")){
            new CountDownTimer(delay, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    runNextEvent(currentSequenceIndex, currentEventIndex);
                }
            }.start();
        }else{
            // TODO VERIFY THIS BECAUSE COULD BE NOT WORKING : if other image than "Image fin" for credits has delay = 0 (it will not working)
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void displayAnimation(final ImageAnimation image){
        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        final ImageView imageAnim = new ImageView(this.context);
        final AnimationDrawable animation = new AnimationDrawable();

        arrayImage.put(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getName(),imageAnim);

        this.screen.getRelativeLayout().addView(imageAnim);
        animation.setOneShot(false);


        for(int i = 0; i < image.getNumberOfFile(); i++){
            animation.addFrame(this.context.getResources().getDrawable(context.getResources().getIdentifier(image.getFilename() + "_" + i, "drawable", this.context.getPackageName())), image.getDuration()* 1000/image.getNumberOfFile());
        }
        imageAnim.setBackgroundDrawable(animation);

        imageAnim.post(new Runnable(){
            @Override
            public void run() {
                Double translationx = image.getTranslationX()*screen.getWidth();
                Double translationy = image.getTranslationY()*screen.getHeight();
                Double translationz = image.getTranslationZ();

                imageAnim.animate().x(translationx.floatValue()).y(translationy.floatValue()).scaleX(translationz.floatValue()).scaleY(translationz.floatValue());
                imageAnim.animate().setDuration((long) image.getMovementDuration()*1000);
                animation.start();
            }
        });

        long delay = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond();
        EventImage temp = (EventImage) AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex);
        temp.setImageView(imageAnim);
        if(delay>0.0){
            new CountDownTimer(delay, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {

                    //screen.getRelativeLayout().removeView(arrayImage.get("test"));
                    runNextEvent(currentSequenceIndex, currentEventIndex);
                }
            }.start();
        }

        new CountDownTimer(image.getDuration() * image.getNumberOfRepeat() * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                animation.stop();

            }
        }.start();
    }

    private void runNextEvent(int currentSequenceIndex, int currentEventIndex){
        int nexEvent = currentEventIndex+1;
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nexEvent);
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
    }

    public void destroyImageView(String name){
        //screen.getRelativeLayout().removeView(arrayImage.get(name));
        //screen.getRelativeLayout().removeView(img);

        arrayImage.get(name).clearAnimation();
        screen.getRelativeLayout().removeView(arrayImage.get(name));

    }

}

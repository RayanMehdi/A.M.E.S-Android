package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v4.view.ViewCompat;
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
import com.example.iem.ames.model.event.EventDavidGoodEnough;
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
    private ImageView imageView;
    public ImageManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
        arrayImage = new HashMap<String, ImageView>();

    }

    public void displayNewImage(Image img){
        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        imageView = new ImageView(this.context);
        try{
            arrayImage.put(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getName(),imageView);
        }catch (Exception e){
            Log.d("IMAGE ERROR", "" + e);
        }

        imageView.setImageResource(img.getID());

        // TODO verify aspect ratio :/ because this will deform
        if(img.getScaleY() == 1 && img.getScaleX() == 1)
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        else
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        // Setting layout params to our RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( this.screen.getWidth(), this.screen.getHeight());

        // Setting position of our ImageView
        Double scaleY = this.screen.getHeight()*img.getScaleY();
        Double scaleX = this.screen.getWidth()*img.getScaleX();

        Double x = (img.getX() != 0) ? this.screen.getWidth()*img.getX() - scaleX/2 : 0;
        Double y = (img.getY() != 0) ? this.screen.getHeight()*img.getY() - scaleY/2 : 0;

        // Add height of the image because the display in the bottom left corner of the ImageView
        y+=scaleY;

        layoutParams.leftMargin = x.intValue();
        layoutParams.topMargin = this.screen.getHeight() - y.intValue();
        layoutParams.width = scaleX.intValue();
        layoutParams.height = scaleY.intValue();

        // FOR DEBUG
//        imageView.setBackgroundColor(Color.GREEN);
        //

        // Finally Adding the imageView to RelativeLayout and its position

//        screen.getRelativeLayout().bringChildToFront(imageView);

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
        }
//        else{
//            // TODO VERIFY THIS BECAUSE COULD BE NOT WORKING : if other image than "Image fin" for credits has delay = 0 (it will not working)
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
    }

    public void displayAnimation(final ImageAnimation image){
        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        final ImageView imageAnim = new ImageView(this.context);
        final AnimationDrawable animation = new AnimationDrawable();

        arrayImage.put(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getName(),imageAnim);



        // SET SIZE AND POSITION
        // TODO verify aspect ratio :/ because this will deform
        if(image.getScaleX() == 1 && image.getScaleY() == 1)
            imageAnim.setScaleType(ImageView.ScaleType.FIT_XY);
        else
            imageAnim.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        // Setting layout params to our RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.screen.getWidth(), this.screen.getHeight());

        // Setting position of our ImageView
        Double scaleY = this.screen.getHeight()*image.getScaleY();
        Double scaleX = this.screen.getWidth()*image.getScaleX();

        final Double x = (image.getX() != 0) ? this.screen.getWidth()*image.getX() - scaleX/2 : 0;
        final Double y = (image.getY() != 0) ? this.screen.getHeight()*image.getY() + scaleY- scaleY/2 : 0 +scaleY;


        layoutParams.leftMargin = x.intValue();
        layoutParams.topMargin = this.screen.getHeight() - y.intValue();
        layoutParams.width = scaleX.intValue();
        layoutParams.height = scaleY.intValue();

        // END SCALE AND POSITION


        this.screen.getRelativeLayout().addView(imageAnim, layoutParams);
//        this.screen.getRelativeLayout().bringChildToFront(imageAnim);

        animation.setOneShot(false);


        for(int i = 0; i < image.getNumberOfFile(); i++){
            animation.addFrame(this.context.getResources().getDrawable(context.getResources().getIdentifier(image.getFilename() + "_" + i, "drawable", this.context.getPackageName())), image.getDuration()* 1000/image.getNumberOfFile());
        }

        imageAnim.setImageDrawable(animation);

        // FOR DEBUG
//        imageAnim.setBackgroundColor(Color.BLUE);
        //

        imageAnim.post(new Runnable(){
            @Override
            public void run() {

                Double translationx = (image.getTranslationX() != 0) ? x + image.getTranslationX()*screen.getWidth() : x;
                Double translationy = (image.getTranslationY() != 0) ? y + image.getTranslationY()*(y) : screen.getHeight() -y;
                Double translationz = (image.getTranslationZ() != 0) ? image.getTranslationZ() : 1;

                imageAnim.animate().x(translationx.floatValue()).y(translationy.floatValue()).scaleX(translationz.floatValue()).scaleY(translationz.floatValue());
                imageAnim.animate().setDuration((long) (image.getMovementDuration()*1000));
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

                    //screen.getRelativeLayout().removeView(imageView);
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

    public void destroyImageView(String name, String type){
        if(type.equals("animation")) {
            arrayImage.get(name).clearAnimation();
            screen.getRelativeLayout().removeView(arrayImage.get(name));
            arrayImage.remove(name);
        }else if(type.equals("image")) {
            screen.getRelativeLayout().removeView(arrayImage.get(name));
            arrayImage.remove(name);
        }

    }

    public void displayFadeInImageAnimation(final ImageAnimation image){
        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();
        final ImageView imageAnim = new ImageView(this.context);
        final AnimationDrawable animation = new AnimationDrawable();

        arrayImage.put(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getName(),imageAnim);



        // SET SIZE AND POSITION
        // TODO verify aspect ratio :/ because this will deform
        if(image.getScaleX() == 1 && image.getScaleY() == 1)
            imageAnim.setScaleType(ImageView.ScaleType.FIT_XY);
        else
            imageAnim.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        // Setting layout params to our RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.screen.getWidth(), this.screen.getHeight());

        // Setting position of our ImageView
        Double scaleY = this.screen.getHeight()*image.getScaleY();
        Double scaleX = this.screen.getWidth()*image.getScaleX();
        final Double x = (image.getX() != 0) ? this.screen.getWidth()*image.getX() - scaleX/2 : 0;
        final Double y = (image.getY() != 0) ? this.screen.getHeight()*image.getY() + scaleY- scaleY/2 : 0 +scaleY;


        layoutParams.leftMargin = x.intValue();
        layoutParams.topMargin = this.screen.getHeight() - y.intValue();
        layoutParams.width = scaleX.intValue();
        layoutParams.height = scaleY.intValue();

        // END SCALE AND POSITION


        this.screen.getRelativeLayout().addView(imageAnim, layoutParams);

        animation.addFrame(this.context.getResources().getDrawable(context.getResources().getIdentifier(image.getFilename(), "drawable", this.context.getPackageName())), image.getDuration()* 1000/image.getNumberOfFile());
        animation.setOneShot(true);

        imageAnim.setAlpha(0.f);
        imageAnim.setImageDrawable(animation);

        imageAnim.post(new Runnable(){
            @Override
            public void run() {

                Double translationz = (image.getTranslationZ() != 0) ? image.getTranslationZ() : 1;

                imageAnim.animate().scaleX(translationz.floatValue()).scaleY(translationz.floatValue()).alpha(1.f);
                imageAnim.animate().setDuration((long) (image.getMovementDuration()*1000));
                animation.start();
            }
        });

        long delay = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond();
        EventDavidGoodEnough temp = (EventDavidGoodEnough) AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex);

        temp.setImageView(imageAnim);
        if(delay>0.0){
            new CountDownTimer(delay, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
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

}

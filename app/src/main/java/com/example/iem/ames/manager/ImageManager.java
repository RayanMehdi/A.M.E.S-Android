package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.view.ViewPropertyAnimator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.ImageAnimation;
import com.example.iem.ames.model.element.Screen;

/**
 * Created by iem on 18/01/2018.
 */

public class ImageManager {
    private Context context;
    private Screen screen;

    public ImageManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
    }

    public void displayNewImage(Image img){
        final ImageView imageView = new ImageView(this.context);
        imageView.setImageResource(img.getID());

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(this.context.getResources(), img.getID(), dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;

        // Setting layout params to our RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        Double d = this.screen.getWidth()*img.getX() - width/2;
        // Setting position of our ImageView
        Double x = this.screen.getWidth()*img.getX() - width/2;
        Double y = this.screen.getHeight()*img.getY() - height/2;
        layoutParams.leftMargin = x.intValue();
        layoutParams.topMargin = y.intValue();

        // Finally Adding the imageView to RelativeLayout and its position
        this.screen.getRelativeLayout().addView(imageView, layoutParams);


    }

    public void displayAnimation(final ImageAnimation image){
        final ImageView imageAnim = new ImageView(this.context);
        final AnimationDrawable animation = new AnimationDrawable();
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

        new CountDownTimer(image.getDuration() * image.getNumberOfRepeat() * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                animation.stop();
            }
        }.start();
    }

}

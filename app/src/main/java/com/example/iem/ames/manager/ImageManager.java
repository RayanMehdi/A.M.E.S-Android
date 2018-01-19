package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.Screen;

import java.io.InputStream;

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
        if(img.isGIF()){
            InputStream imageStream = this.context.getResources().openRawResource(img.getID());
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(bitmap);
            Glide.with(this.context).load(img.getID()).into(imageView);
        }
        else{
            imageView.setImageResource(img.getID());
        }

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

        if(img.isGIF() && img.getDuration() > 0){
            new CountDownTimer(img.getDuration()*1000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    screen.getRelativeLayout().removeView(imageView);
                }
            }.start();
        }

    }

}

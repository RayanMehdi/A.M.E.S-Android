package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.element.Screen;

import java.io.InputStream;

/**
 * Created by iem on 18/01/2018.
 */

public class ButtonManager {
    private Context context;
    private Screen screen;

    public ButtonManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
    }

    public void displayNewButton(Button button){
        final ImageButton imageButton = new ImageButton(this.context);

        /*
        if(button.isGIF()){
            InputStream imageStream = this.context.getResources().openRawResource(button.getID());
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(bitmap);
            Glide.with(this.context).load(button.getID()).into(imageView);
        }*/
        //else{
            imageButton.setImageResource(button.getID());
        //}

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(this.context.getResources(), button.getID(), dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;

        // Setting layout params to our RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);

        System.out.println("H : " + this.screen.getWidth()*button.getX());
        Double d = this.screen.getWidth()*button.getX() - width/2;
        System.out.println("H : " + d.toString());
        System.out.println("W : " + width);

        // Setting position of our ImageView
        Double x = this.screen.getWidth()*button.getX() - width/2;
        Double y = this.screen.getHeight()*button.getY() - height/2;
        layoutParams.leftMargin = x.intValue();
        layoutParams.topMargin = y.intValue();

        // Finally Adding the imageView to RelativeLayout and its position
        this.screen.getRelativeLayout().addView(imageButton, layoutParams);

        // TODO event click -> clear the imageButton
        /*
        new CountDownTimer(button.getDuration()*1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                screen.getRelativeLayout().removeView(imageButton);
            }
        }.start();*/

    }

}

package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventButton;

import java.util.ArrayList;


/**
 * Created by iem on 18/01/2018.
 */

public class ButtonManager {
    private Context context;
    private Screen screen;
    private int nextEventIndex;
    private ArrayList<ImageButton> imageButtons;

    public ButtonManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
        this.imageButtons = new ArrayList<>();
        this.nextEventIndex = 0;
    }

    public void displayNewButton(final EventButton eventButton){
        // For all buttons :

        for (final Button button:eventButton.getButtons()) {

            final ImageButton imageButton = new ImageButton(this.context);

            imageButton.setImageResource(button.getID());


            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;
            Bitmap mBitmap = BitmapFactory.decodeResource(this.context.getResources(), button.getID(), dimensions);
            int height = dimensions.outHeight;
            int width = dimensions.outWidth;

            // Setting layout params to our RelativeLayout
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);

            //System.out.println("H : " + this.screen.getWidth() * button.getX());
            Double d = this.screen.getWidth() * button.getX() - width / 2;
            //System.out.println("H : " + d.toString());
            //System.out.println("W : " + width);

            // Setting position of our ImageView
            Double x = this.screen.getWidth() * button.getX() - width / 2;
            Double y = this.screen.getHeight() * button.getY() - height / 2;
            layoutParams.leftMargin = x.intValue();
            layoutParams.topMargin = y.intValue();

            // Finally Adding the imageView to RelativeLayout and its position
            this.screen.getRelativeLayout().addView(imageButton, layoutParams);


            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TEST", String.valueOf(button.getNextEventIndex()));


                    // Get the next index event
                    nextEventIndex = button.getNextEventIndex();
                    // Get the sequence index
                    int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
                    Log.d("TEST", String.valueOf(currentSequenceIndex));
                    // Set the current index event
                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(nextEventIndex);
                    // Run the next event (with the new current index in sequence)

                    if(eventButton.getName() != "Scan_button")
                        removeAllButtons();
                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();

                }
            });

            imageButtons.add(imageButton);
        }

    }

    public int getNextEventIndex() {
        return nextEventIndex;
    }

    public void removeAllButtons(){


            for (ImageButton imageButton : imageButtons) {
                this.screen.getRelativeLayout().removeView(imageButton);
            }
        this.imageButtons.clear();
        }
    }


package com.example.iem.ames.manager;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Screen;

/**
 * Created by iem on 18/01/2018.
 */

public class TextManager {

    private Context context;
    private Screen screen;
    private TextView textView;

    public TextManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
        textView = new TextView(this.context);
        screen.getRelativeLayout().addView(textView);
    }

    public void displayText(String message){
        textView.setHeight(this.screen.getHeight());
        textView.setWidth(this.screen.getWidth());
        textView.setTextSize(20);
        textView.setText(message);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setGravity(Gravity.CENTER);
    }

}

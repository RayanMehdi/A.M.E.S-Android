package com.example.iem.ames.manager;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.element.Text;

/**
 * Created by iem on 18/01/2018.
 */

public class TextManager {

    private Context context;
    private Screen screen;
    private TextView textView;
    private Handler mHandler;
    private int mIndex;
    private long mDelay = 100;
    private Runnable characterAdder;
    private boolean actionToDo;

    public TextManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
        textView = new TextView(this.context);
        mHandler = new Handler();
    }

    public void displayText(Text text){
        textView.setHeight(this.screen.getHeight());
        textView.setWidth(this.screen.getWidth());
        textView.setTextSize(20);
        textView.setText(text.getDisplayedText());
        textView.setTextColor(context.getResources().getColor(R.color.white));
        if(text.isAnimated()){
            displayTextWithDelay(text);
        }
        setPosition(text);
    }

    private void displayTextWithDelay(Text text){
        final String msg = text.getDisplayedText();
        setCharacterDelay((long) text.getSpeed());
        this.screen.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(msg);
            }
        });
        animateText(text.getDisplayedText());
    }

    private void setPosition(Text text){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screen.getWidth(),screen.getHeight());
        layoutParams.leftMargin = (int) text.getX();
        layoutParams.topMargin = (int) text.getY();
        centerText();
        screen.getRelativeLayout().addView(textView, layoutParams);
    }

    public void centerText(){
        this.textView.setGravity(Gravity.CENTER);
    }

    public void removeTextView(){
        this.screen.getRelativeLayout().removeView(this.textView);
    }

    private void animateText(CharSequence text) {
        final CharSequence msg = text;
        characterAdder = new Runnable() {
            @Override
            public void run() {
                if(textView.getText().length() >= msg.length()){
                    screen.getRelativeLayout().setOnClickListener(null);
                }
                else{
                    textView.setText(msg.subSequence(0, mIndex++) + "-");
                    if(mIndex < msg.length()) {
                        mHandler.postDelayed(characterAdder, mDelay);
                    }
                    else{
                        textView.setText(msg);
                    }
                }
            }
        };
        mIndex = 0;
        this.textView.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    private void setCharacterDelay(long millis) {
        mDelay = millis;
    }

}

package com.example.iem.ames.manager;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iem.ames.AMESApplication;
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

        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        if(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond() > 0.0){
            new CountDownTimer((AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond()), 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    runNexEvent(currentSequenceIndex, currentEventIndex);
                }
            }.start();
        }else {
            this.screen.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    screen.getRelativeLayout().setOnClickListener(null);
                    stop(currentSequenceIndex, currentEventIndex);
                }
            });
        }
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

    public void textNotInSequence(String message){
        textView.setHeight(this.screen.getHeight());
        textView.setWidth(this.screen.getWidth());
        textView.setTextSize(20);
        textView.setText(message);
        Log.d("TEST", message);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        centerText();
        screen.getRelativeLayout().addView(textView);

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {

                destroy();
            }
        }.start();
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
        mDelay = millis*1000;
    }

    private void destroy(){
        screen.getRelativeLayout().removeView(textView);
    }

    private void runNexEvent(int currentSequenceIndex, int currentEventIndex){
        currentEventIndex = currentEventIndex+1;
        Log.d("INDEX", String.valueOf(currentEventIndex));
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(currentEventIndex++);
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
    }

    public void stop(int currentSequenceIndex, int currentEventIndex){
        try{destroy();}catch (Exception e){}
        runNexEvent(currentSequenceIndex, currentEventIndex);
    }
}

package com.example.iem.ames.manager;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
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
    private boolean isfinished;

    public TextManager(Context context, Screen screen) {
        this.context = context;
        this.screen = screen;
        textView = new TextView(this.context);
        mHandler = new Handler();
    }

    public void displayText(final Text text){
        isfinished=false;
        textView.setHeight(this.screen.getHeight());
        textView.setWidth(this.screen.getWidth());
        textView.setTextSize(20);
        textView.setText(text.getDisplayedText());
        textView.setTextColor(context.getResources().getColor(R.color.white));
        if(text.isAnimated()){
            displayTextWithDelay(text);
        }else{
            isfinished=true;
        }
        setPosition(text);

        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        if(AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelay() > 0.0){

            this.screen.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(text.isAnimated()) {
                        isfinished = true;
                        screen.getRelativeLayout().setOnClickListener(null);
                        textView.setText(text.getDisplayedText());
                    }else {

                    }
                }
            });
            new CountDownTimer((AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond()), 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    runNextEvent(currentSequenceIndex, currentEventIndex);
                }
            }.start();

        }else {
            this.screen.getRelativeLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if(isfinished == false) {
                           isfinished = true;
                            textView.setText(text.getDisplayedText());
                        }else{
                            screen.getRelativeLayout().setOnClickListener(null);
                            stop(currentSequenceIndex, currentEventIndex);
                        }
                }
            });
        }
    }

    private void displayTextWithDelay(final Text text){
        final String msg = text.getDisplayedText();
        setCharacterDelay((long) text.getSpeed());
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
                    isfinished=true;
                }
                else{
                    textView.setText(msg.subSequence(0, mIndex++) + "-");
                    if(mIndex < msg.length()) {
                        mHandler.postDelayed(characterAdder, mDelay);
                    }
                    else{
                        isfinished=true;
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

    public void destroy(){
        screen.getRelativeLayout().removeView(textView);
    }

    private void runNextEvent(int currentSequenceIndex, int currentEventIndex){
        currentEventIndex = currentEventIndex+1;
        Log.d("INDEX", String.valueOf(currentEventIndex));
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).setCurrentIndex(currentEventIndex);
        AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).run();
    }

    public void stop(int currentSequenceIndex, int currentEventIndex){
        try{destroy();}catch (Exception e){}
        runNextEvent(currentSequenceIndex, currentEventIndex);
    }
}

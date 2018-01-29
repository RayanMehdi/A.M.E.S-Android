package com.example.iem.ames.manager;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.element.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        try{destroy();}catch (Exception e){}
        isfinished=false;
        textView.setTextSize(15);
        textView.setText(text.getDisplayedText());
        textView.setTextColor(context.getResources().getColor(R.color.white));
        // FOR DEBUG
        //textView.setBackgroundColor(Color.RED);
        //
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
        layoutParams.leftMargin = (int) text.getX()+50;
        layoutParams.rightMargin = 50;
        layoutParams.topMargin = (int) text.getY();
        centerTextVertical();

        screen.getRelativeLayout().addView(textView, layoutParams);
//        screen.getRelativeLayout().bringChildToFront(textView);
    }

    public void centerTextVertical
            (){
        this.textView.setGravity(Gravity.CENTER_VERTICAL);
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

    public void displayHour(Text text){
        TextView hour = new TextView(context);
        hour.setTextColor(context.getResources().getColor(R.color.white));
        hour.setTextSize(12);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy HH:mm");
        String formattedDate = df.format(c.getTime());
        hour.setText(formattedDate);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screen.getWidth(),screen.getHeight());
        layoutParams.leftMargin = (int) (screen.getWidth()*text.getX());
        layoutParams.rightMargin = 50;
        layoutParams.topMargin = (int) (screen.getHeight()-screen.getHeight()*text.getY());
        layoutParams.width = (int) (screen.getWidth()-screen.getWidth()*text.getWidth());
        layoutParams.height = (int) (screen.getHeight()-screen.getHeight()*text.getHeight());

        screen.getRelativeLayout().addView(hour, layoutParams);
        final int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
        final int currentEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCurrentIndex();

        new CountDownTimer((AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(currentEventIndex).getDelayInMillisecond()), 1000) {

            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                runNextEvent(currentSequenceIndex, currentEventIndex);
            }
        }.start();
    }
}

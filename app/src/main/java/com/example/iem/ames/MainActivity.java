package com.example.iem.ames;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.iem.ames.model.AMESGame;
import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.parser.AMESParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        setContentView(R.layout.activity_main);
        RelativeLayout cl = findViewById(R.id.layout);

        TextView tv = new TextView(this);
        tv.setHeight(height);
        tv.setWidth(width);
        tv.setTextSize(20);
        tv.setText(R.string.loadingTextLabel);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setGravity(Gravity.CENTER);
        cl.addView(tv);

        AMESParser parser = new AMESParser();
        AMESGame amesGame = new AMESGame();
        AMESSequence testSequence = new AMESSequence(amesGame);
        parser.AMESParser(this, testSequence);
        Log.d("TEST",testSequence.toString());
    }
}

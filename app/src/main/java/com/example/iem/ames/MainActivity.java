package com.example.iem.ames;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.iem.ames.parser.AMESParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.Map;

import static javax.xml.xpath.XPathConstants.STRING;

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

        AMESGame currentGame=AMESApplication.application().getAMESManager().getCurrentGame();

        TextView tv = new TextView(this);
        tv.setHeight(height);
        tv.setWidth(width);
        tv.setTextSize(20);
        tv.setText(R.string.loadingTextLabel);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setGravity(Gravity.CENTER);
        cl.addView(tv);

        if(isEligibleforAMES(tv)){
            Log.d("Test", "Ok");
            loadSequenceFile();
            //TODO Method currentGame.run();
        }


    }
    private boolean areCamAvailable(){
        PackageManager pm = getPackageManager();
        boolean frontCam, rearCam;
        frontCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        rearCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(!frontCam || !rearCam){
            return false;
        }
        return true;
    }

    private boolean isEligibleforAMES(TextView loadingTextLabel){
        boolean response = true;
        String displayMessage="";
        if(areCamAvailable()){
            if(this.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                displayMessage="AMES is ready, press start !";
            }else
                displayMessage="Warning, no Torch. Experience will be less enjoying. Press Start";

            if(isNetworkAvailable()){

            }
        }else {
            displayMessage = "Sorry, AMES needs a front camera to run, please restart the application with a good device";
            response = false;
        }

        //loadingTextLabel.setText(displayMessage);
        //TODO changer le label  avec le displayMessage
        return response;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadSequenceFile(){
        AMESParser parser = AMESApplication.application().getAMESManager().getParser();
        parser.CreateSequenceFromFile(R.raw.firstsequence);
        //parser.CreateSequenceFromFile(R.raw.secondsequence);
        //parser.CreateSequenceFromFile(R.raw.thirdsequence);
        //parser.CreateSequenceFromFile(R.raw.fourthsequence);
       // parser.CreateSequenceFromFile(R.raw.testsequence);
    }

}

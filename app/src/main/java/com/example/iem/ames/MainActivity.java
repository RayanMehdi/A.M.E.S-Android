package com.example.iem.ames;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.iem.ames.manager.AMESManager;
import com.example.iem.ames.model.AMESGame;
import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventButton;
import com.example.iem.ames.parser.AMESParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int width, height;
    private RelativeLayout rl;
    private AMESManager amesManager;

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
        width = size.x;
        height = size.y;


        setContentView(R.layout.activity_main);
        rl = findViewById(R.id.layout);
        amesManager = AMESApplication.application().getAMESManager();
        AMESGame currentGame=AMESApplication.application().getAMESManager().getCurrentGame();
        amesManager.setContextView(this.getApplicationContext());
        amesManager.createManager(new Screen(this.rl, height, width));
        amesManager.getTextManager().displayText(getResources().getString(R.string.loadingTextLabel));


        if(isEligibleforAMES()){
            Log.d("Test", "Ok");
            //amesManager.getImageManager().displayNewImage(new Image("oeil", 0.1, 0.1, true, 3));
            //amesManager.getImageManager().displayNewImage(new Image("davidgoodenough", 0.5, 0.5, true, 5));

            EventButton eventButton = new EventButton("david&oeil","button", 0);

            EventButton eventButton2 = new EventButton("david","button", 0);
            EventButton eventButton3 = new EventButton("oeil","button", 0);
            ArrayList<Button> buttons = new ArrayList<>();
            ArrayList<Button> buttons2 = new ArrayList<>();
            ArrayList<Button> buttons3 = new ArrayList<>();

            buttons.add(new Button("davidgoodenough", 1,0.5, 0.5));
            buttons.add(new Button("oeil", 2,0.9, 0.9));
            buttons2.add(new Button("davidgoodenough", 2,0.1, 0.1));
            buttons3.add(new Button("oeil", 1,0.8, 0.1));

            eventButton.setButtons(buttons);
            eventButton2.setButtons(buttons2);
            eventButton3.setButtons(buttons3);

            AMESSequence amesSequence = new AMESSequence();
            amesSequence.addEvent(eventButton);
            amesSequence.addEvent(eventButton2);
            amesSequence.addEvent(eventButton3);

            currentGame.addSequence(amesSequence);

            currentGame.run();

            //loadSequenceFile();
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

    private boolean isEligibleforAMES(){
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
            //response = false;
        }

        AMESApplication.application().getAMESManager().getTextManager().displayText(displayMessage);
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
        //parser.CreateSequenceFromFile(R.raw.testsequence);
    }

}

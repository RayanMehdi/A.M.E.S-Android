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
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.element.Text;
import com.example.iem.ames.model.event.EventImage;
import com.example.iem.ames.model.event.EventSound;
import com.example.iem.ames.model.event.EventText;
import com.example.iem.ames.parser.AMESParser;

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


        if(isEligibleforAMES()){
            Log.d("Test", "Ok");
            //amesManager.getImageManager().displayNewImage(new Image("oeil", 0.1, 0.1, true, 3));
            //amesManager.getImageManager().displayNewImage(new Image("davidgoodenough", 0.5, 0.5, false, 9));
            Image testimg = new Image("davidgoodenough.png", 0.5, 0.5, false, 1);
            Image testimgGIF = new Image("oeil", 0.1, 0.1, true, 8);
            EventImage test2 = new EventImage("imagetest","image", 0.5, testimg );
            EventImage test3 = new EventImage("imagetest2", "animation", 0.5, testimgGIF);
            EventSound test = new EventSound("test", "son", 0.1, "davidgoodenough_sound.mp3", true);
            Text text = new Text("", 150.0, 150.0, 500, 500, true, 0.25);
            EventText eventText = new EventText("test text", "", 15.0, text);
            test.run();
            //test2.run();
            test3.run();
            //eventText.run();
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

    private boolean isEligibleforAMES(){
        boolean response = true;
        String displayMessage="";
        if(areCamAvailable()){
            if(this.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                displayMessage = getResources().getString(R.string.AMESready);
            }else
                displayMessage = getResources().getString(R.string.torchError);

            if(isNetworkAvailable()){

            }
        }else {
            displayMessage = getResources().getString(R.string.cameraError);
            //response = false;
        }
        AMESApplication.application().getAMESManager().getTextManager().displayText(new Text(displayMessage, 0, 0, 0.9,0.4, true, 0.25));
       // AMESApplication.application().getAMESManager().getTextManager().centerText();
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

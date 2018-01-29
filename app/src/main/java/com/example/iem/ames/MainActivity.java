package com.example.iem.ames;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.iem.ames.manager.AMESManager;
import com.example.iem.ames.model.AMESGame;
import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.ImageAnimation;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventButton;
import com.example.iem.ames.model.element.Text;
import com.example.iem.ames.model.event.EventCamera;
import com.example.iem.ames.model.event.EventCheckLight;
import com.example.iem.ames.model.event.EventImage;
import com.example.iem.ames.model.event.EventSound;
import com.example.iem.ames.model.event.EventStop;
import com.example.iem.ames.model.event.EventText;
import com.example.iem.ames.parser.AMESParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import io.keiji.plistparser.PListArray;
import io.keiji.plistparser.PListDict;
import io.keiji.plistparser.PListException;
import io.keiji.plistparser.PListObject;
import io.keiji.plistparser.PListParser;


public class MainActivity extends AppCompatActivity {

    private int width, height;
    private RelativeLayout rl;
    private AMESManager amesManager;

    static int CAMERA_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
Log.d("MainActivity","OnCreate");
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
        amesManager.setContextView(this.getApplicationContext());
        amesManager.createManager(new Screen(this.rl, height, width), this);

if(!amesManager.isStart()) {
    amesManager.setStart(true);
    //       test();

    loadSequenceFile();
    isEligibleforAMES();
    AMESApplication.application().getAMESManager().getCurrentGame().run();
}
    }

    private boolean areCamAvailable(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);

        }
        PackageManager pm = getPackageManager();
        boolean frontCam, rearCam;
        frontCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        rearCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(!frontCam || !rearCam){
            return false;
        }
        return true;
    }

    private void isEligibleforAMES(){
        boolean response = true;
        String displayMessage="";
        if(areCamAvailable()){
            if(this.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                displayMessage = getResources().getString(R.string.AMESready);
            }else{
                displayMessage = getResources().getString(R.string.torchError);
            }
            if(isNetworkAvailable()){
            }

            Text initText = new Text(displayMessage,0,0,0.4,0.9, true, 0.025);
            EventText initEvent = new EventText("init", "text", 5, initText);
            AMESSequence sequence = new AMESSequence();
            sequence.addEvent(initEvent);
            ArrayList<AMESSequence> test = AMESApplication.application().getAMESManager().getCurrentGame().getSequences();
            test.add(0, sequence);
            AMESApplication.application().getAMESManager().getCurrentGame().setSequences(test);
        }else {
            displayMessage = getResources().getString(R.string.cameraError);
            AMESApplication.application().getAMESManager().getCurrentGame().getSequences().clear();
            AMESApplication.application().getAMESManager().getCurrentGame().addSequence(new AMESSequence());
            Text initText = new Text(displayMessage,0,0,0.4,0.9, false, 0);
            EventText initEvent = new EventText("init", "text", 0, initText);
            AMESSequence sequence = new AMESSequence();
            sequence.addEvent(initEvent);
            AMESApplication.application().getAMESManager().getCurrentGame().getSequences().add(0, sequence);
        }
        // AMESApplication.application().getAMESManager().getCurrentGame().getSequence(0).getEvents().add(0,initEvent);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadSequenceFile() {

        AMESParser parser = AMESApplication.application().getAMESManager().getParser();

     //   parser.CreateSequenceFromFile(R.raw.firstsequence);
        parser.CreateSequenceFromFile(R.raw.secondsequence);
//        parser.CreateSequenceFromFile(R.raw.thirdsequence);
//        parser.CreateSequenceFromFile(R.raw.fourthsequence);
//        parser.CreateSequenceFromFile(R.raw.testsequence);

    }

    private void test(){

        AMESGame currentGame=AMESApplication.application().getAMESManager().getCurrentGame();

        AMESSequence amesSequence = new AMESSequence();

        Button button = new Button("scan.png",1, 0.2, 0.2, 0.2, 0.2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(button);
        EventButton eventButton = new EventButton("ekg.png", "animation", 5, arrayList);
        amesSequence.addEvent(eventButton);

        Image img = new Image("ekg.png",0.5, 0.5, false,1,1);
        EventImage eventImage = new EventImage("ekg.png", "animation", 5, img);
        amesSequence.addEvent(eventImage);

        currentGame.addSequence(amesSequence);
        currentGame.run();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    int currentSequenceIndex = AMESApplication.application().getAMESManager().getCurrentGame().getCurrentSequenceIndex();
                    int creditEventIndex = AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getCreditsEventIndex();

                    AMESApplication.application().getAMESManager().getCurrentGame().getSequence(currentSequenceIndex).getEvents().get(creditEventIndex).run();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

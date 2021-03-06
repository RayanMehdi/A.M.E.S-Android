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
import com.example.iem.ames.model.event.EventDavidGoodEnough;
import com.example.iem.ames.model.event.EventImage;
import com.example.iem.ames.model.event.EventMicro;
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


            loadSequenceFile();
            test();
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

        parser.CreateSequenceFromFile(R.raw.firstsequence);
        parser.CreateSequenceFromFile(R.raw.secondsequence);

//        parser.CreateSequenceFromFile(R.raw.thirdsequence);
//        parser.CreateSequenceFromFile(R.raw.fourthsequence);
//        parser.CreateSequenceFromFile(R.raw.testsequence);

    }

    private void test(){

        AMESGame currentGame=AMESApplication.application().getAMESManager().getCurrentGame();

        Text text = new Text("Le saviez vous...", 150.0, 150.0, 500, 500, false, 1.25);
        Text text3 = new Text("Bravo, ça marche bien !", 150.0, 150.0, 500, 500, true, 0.05);
        Text text2 = new Text("Si vous avez trois chocapics et qu'un cerf venue d'Irlande chevauchant un lièvre vous demande un selfis, alors Zinedine Zidane vous OS avec un solide druide lvl 2 forme phoque", 150.0, 150.0, 500, 500, true, 0.025);
        EventText eventText = new EventText("test text", "animated text", 2, text);
        EventText eventText2 = new EventText("test text2", "animated text", 5, text2);
        EventText eventText3 = new EventText("test text3", "animated text", 0, text3);
        EventStop eventStop = new EventStop("test text", "animated text", 1);
        EventStop eventStop2 = new EventStop("test text", "animated text", 5);
        EventStop eventStop3 = new EventStop("test", "animation", 5);
        ImageAnimation img = new ImageAnimation("bed", 10,10, true,5, 15, 2, 1, 0.5, 0.1, 2, 0.25, 0.25);
        Image imgCamera = new Image("davidgoodenough.png", 0.5, 0.5, false, 0.25, 0.25);
        Image imgCamera2 = new Image("no.png", 0.2, 0.2, false, 0.3, 0.3);
        EventStop eventStop4 = new EventStop("camera", "camera", 3);
        EventImage eventImage = new EventImage("test", "test", 5, img);
        EventImage eventImage2 = new EventImage("test2", "test", 5, imgCamera2);
        EventCamera eventCamera = new EventCamera("camera", "camera", 6,false, imgCamera);
        Image img3 = new Image("imageboot.png", 0.5, 0.5, false, 0.25, 0.25);
        EventImage eventImage3 = new EventImage("test", "image", 5, img3);
        EventMicro eventMicro = new EventMicro("Wait","micro", 5);


        Text textTeaser1 = new Text("Prêt pour la plus grande frayeur de votre vie ?", 0.2, 0.2, 0.5, 0.1, false, 1.25);
        EventText eventTextTeaser = new EventText("text teaser 1", "text david", 0, textTeaser1);
       /* Text textTeaser2 = new Text("Chasser les fantômes ne vous fait pas peur ?", 0.2, 0.2, 0.5, 0.1, false, 1.25);
        EventText eventTextTeaser2 = new EventText("text teaser 2", "text david", 0, textTeaser2);*/
        EventSound eventSoundDavid = new EventSound("davidgoodenough", "test david", 0.1, "davidgoodenough_sound.mp3", false);
        ImageAnimation imgDavid = new ImageAnimation("davidgoodenough.png", 0.5,0.6, false, 1, 1, 1, 0.0, 0.0, 1.4, 3, 0.4, 0.4);
        EventDavidGoodEnough eventImageDavid = new EventDavidGoodEnough("test", "image", 3, imgDavid);
        ImageAnimation imgPP = new ImageAnimation("paypal.png", 0.15,0.2, false, 1, 1, 1, 0.0, 0.0, 1.4, 3, 0.2, 0.2);
        EventDavidGoodEnough eventImagePP = new EventDavidGoodEnough("test", "image", 3, imgPP);
        ImageAnimation imgTP = new ImageAnimation("tipeee.png", 0.85,0.2, false, 1, 1, 1, 0.0, 0.0, 1.4, 3, 0.2, 0.2);
        EventDavidGoodEnough eventimageTP = new EventDavidGoodEnough("test", "image", 0.1, imgTP);
        Text textDavid = new Text("Cette fonctionnalité sera disponible dans un prochain DLC...", 0.25, 0.2, 0.5, 0.1, false, 1.25);
        EventText eventTextDavid = new EventText("text david", "text david", 5, textDavid);
        EventStop eventStopDavid = new EventStop("david", "david", 25);

        AMESSequence amesSequence = new AMESSequence();
        //amesSequence.addEvent(eventButton);
        //amesSequence.addEvent(eventButton2);
        //amesSequence.addEvent(eventButton3);
        //amesSequence.addEvent(eventText);
        //amesSequence.addEvent(eventButton);
        //amesSequence.addEvent(eventStop);
        //amesSequence.addEvent(eventText2);
        //amesSequence.addEvent(eventImage2);
        //amesSequence.addEvent(eventStop2);
        //amesSequence.addEvent(eventStop3);
        //amesSequence.addEvent(eventText3);
        //amesSequence.addEvent(eventStop4);
        //amesSequence.addEvent(eventImage3);
        amesSequence.addEvent(eventMicro);
        amesSequence.addEvent(eventTextTeaser);
        //amesSequence.addEvent(eventTextTeaser2);
        amesSequence.addEvent(eventSoundDavid);
        amesSequence.addEvent(eventImageDavid);
        amesSequence.addEvent(eventTextDavid);
        amesSequence.addEvent(eventImagePP);
        amesSequence.addEvent(eventimageTP);
        amesSequence.addEvent(eventStopDavid);
        currentGame.addSequence(amesSequence);
       // currentGame.run();

        //Image testimg = new Image("davidgoodenough.png", 0.5, 0.5, false, 1);
        //Image testimgGIF = new Image("oeil", 0.1, 0.1, true, 8);
        //EventImage test2 = new EventImage("imagetest","image", 0.5, testimg );
        //EventImage test3 = new EventImage("imagetest2", "animation", 0.5, testimgGIF);
        //EventSound test = new EventSound("test", "son", 0.1, "davidgoodenough_sound.mp3", true);
        //Text text = new Text("", 150.0, 150.0, 500, 500, true, 0.25);
        //EventText eventText = new EventText("test text", "", 15.0, text);
        //test.run();
        //test2.run();
        //test3.run();
        //eventText.run();
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

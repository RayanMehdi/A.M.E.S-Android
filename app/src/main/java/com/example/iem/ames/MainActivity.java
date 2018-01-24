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
import com.example.iem.ames.model.element.ImageAnimation;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.model.event.EventButton;
import com.example.iem.ames.model.element.Text;
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
        AMESGame currentGame = AMESApplication.application().getAMESManager().getCurrentGame();
        amesManager.setContextView(this.getApplicationContext());
        amesManager.createManager(new Screen(this.rl, height, width));

        if(isEligibleforAMES()){
            Log.d("Test", "Ok");


            //test();

            loadSequenceFile();
             // AMESApplication.application().getAMESManager().getCurrentGame().run();
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
        //AMESApplication.application().getAMESManager().getTextManager().textNotInSequence(displayMessage);
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

    private void test(){
        //amesManager.getImageManager().displayNewImage(new Image("oeil", 0.1, 0.1, true, 3));
        //amesManager.getImageManager().displayNewImage(new Image("davidgoodenough", 0.5, 0.5, true, 5));
        AMESGame currentGame=AMESApplication.application().getAMESManager().getCurrentGame();
       EventButton eventButton = new EventButton("Zamasalerace","button", 0);
//
//        EventButton eventButton2 = new EventButton("david","button", 0);
//        EventButton eventButton3 = new EventButton("oeil","button", 0);
       ArrayList<Button> buttons = new ArrayList<>();
//        ArrayList<Button> buttons2 = new ArrayList<>();
//        ArrayList<Button> buttons3 = new ArrayList<>();
//
       buttons.add(new Button("davidgoodenough.png", 2,0.5, 0.1));
//        buttons.add(new Button("oeil", 2,0.9, 0.9));
//        buttons2.add(new Button("davidgoodenough", 2,0.1, 0.1));
//        buttons3.add(new Button("oeil", 1,0.8, 0.1));
//
        eventButton.setButtons(buttons);
//        eventButton2.setButtons(buttons2);
//        eventButton3.setButtons(buttons3);

        Text text = new Text("Le saviez vous...", 150.0, 150.0, 500, 500, false, 1.25);
        Text text3 = new Text("Bravo, ça marche bien !", 150.0, 150.0, 500, 500, true, 0.05);
        Text text2 = new Text("Si vous avez trois chocapics et qu'un cerf venue d'Irlande chevauchant un lièvre vous demande un selfis, alors Zinedine Zidane vous OS avec un solide druide lvl 2 forme phoque", 150.0, 150.0, 500, 500, true, 0.025);
        EventText eventText = new EventText("test text", "animated text", 2, text);
        EventText eventText2 = new EventText("test text2", "animated text", 5, text2);
        EventText eventText3 = new EventText("test text3", "animated text", 0, text3);
        EventStop eventStop = new EventStop("test text", "animated text", 1);
        EventStop eventStop2 = new EventStop("test text", "animated text", 5);
        EventStop eventStop3 = new EventStop("test", "animation", 3);
        ImageAnimation img = new ImageAnimation("bed", 10,10, true,5, 15, 2);
        EventImage eventImage = new EventImage("test", "test", 5, img);
        AMESSequence amesSequence = new AMESSequence();
        //amesSequence.addEvent(eventButton);
//       amesSequence.addEvent(eventButton2);
//       amesSequence.addEvent(eventButton3);
        amesSequence.addEvent(eventText);
        amesSequence.addEvent(eventButton);
        amesSequence.addEvent(eventStop);
        amesSequence.addEvent(eventText2);
        amesSequence.addEvent(eventImage);
        amesSequence.addEvent(eventStop2);
        amesSequence.addEvent(eventStop3);
        amesSequence.addEvent(eventText3);


        currentGame.addSequence(amesSequence);

        currentGame.run();

//            Image testimg = new Image("davidgoodenough.png", 0.5, 0.5, false, 1);
//            Image testimgGIF = new Image("oeil", 0.1, 0.1, true, 8);
//            EventImage test2 = new EventImage("imagetest","image", 0.5, testimg );
//            EventImage test3 = new EventImage("imagetest2", "animation", 0.5, testimgGIF);
//            EventSound test = new EventSound("test", "son", 0.1, "davidgoodenough_sound.mp3", true);
//            Text text = new Text("", 150.0, 150.0, 500, 500, true, 0.25);
//            EventText eventText = new EventText("test text", "", 15.0, text);
//            test.run();
//            //test2.run();
//            test3.run();
//            //eventText.run();
    }
}

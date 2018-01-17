package com.example.iem.ames;

import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.iem.ames.model.AMESGame;

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
    private TextView loadingTextLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AMESGame currentGame=AMESApplication.application().getAMESManager().getCurrentGame();
        //loadingTextLabel = (TextView) findViewById(R.id.loadingTextLabel);
        loadingTextLabel = (TextView) findViewById(R.id.hello);

        if(isEligibleforAMES(loadingTextLabel)){
            Log.d("Test", "Ok");
            //TODO method currentGame.loadFile()
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

        loadingTextLabel.setText(displayMessage);
        //TODO changer le label  avec le displayMessage
        return response;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

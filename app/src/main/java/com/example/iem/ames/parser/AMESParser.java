package com.example.iem.ames.parser;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.model.event.AMESEvent;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iem on 16/01/2018.
 */

public class AMESParser {
    final String
            KEY = "key",
            STRING = "string",
            REAL = "real",
            INTEGER = "integer",
            NAME = "Name",
            TYPE = "Type",
            DELAY = "Duration";


    public void CreateSequenceFromFile(int idFile){
        Context context = AMESApplication.application().getAMESManager().getContextView();
        AMESSequence amesSequence = new AMESSequence();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream in_s = context.getResources().openRawResource(idFile);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            ArrayList<HashMap> listOfEvent = new ArrayList<>();
            HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

            int eventType = parser.getEventType();

            String lastTag = null;
            String lastKey = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    lastTag = parser.getName();
                }
                else if (eventType == XmlPullParser.TEXT) {
                    // some text
                    if (KEY.equalsIgnoreCase(lastTag) && parser.getText().matches(".*[\\w]+.*")) {
                        // start tracking a new key
                        lastKey = parser.getText();

                        //Log.d("TEST", "KEY : "+lastKey);
                        if(NAME.equalsIgnoreCase(lastKey)){
                            if(!map.isEmpty()){
                                listOfEvent.add(map);
                            }

                            map = new HashMap<String, ArrayList<String>>();
                        }
                    }
                    else if ((STRING.equalsIgnoreCase(lastTag) ||
                            REAL.equalsIgnoreCase(lastTag) ||
                            INTEGER.equalsIgnoreCase(lastTag)
                    ) && parser.getText().matches(".*[\\w]+.*") ) {
                        // a new string for the last encountered key
                        if (!map.containsKey(lastKey)) {
                            map.put(lastKey, new ArrayList<String>());
                        }
                        //Log.d("TEST", "VALUE : "+parser.getText());
                        map.get(lastKey).add(parser.getText());
                    }
                }

                eventType = parser.next();
            }
            // For the last event of the sequence
            if(!map.isEmpty()){
                listOfEvent.add(map);
            }

            //Log.d("TEST", listOfEvent.toString());


            for (HashMap<String, ArrayList<String>> hashmap: listOfEvent) {

                String amesEventName = hashmap.get(NAME).get(0);
                String amesEventType = hashmap.get(TYPE).get(0);
                double amesEventDelay = Double.parseDouble(hashmap.get(DELAY).get(0));

                AMESEvent event = null;

                switch (hashmap.get(TYPE).get(0)){
                    case "animated text":
                        break;
                    case "animation":
                        break;
                    case "battery level":
                        break;
                    case "button":
                        if(hashmap.containsKey("Number of buttons")) {
                            int numberButtons = Integer.parseInt(hashmap.get("Number of buttons").get(0));
                            Log.d("TEST", String.valueOf(numberButtons));
                            for (int i = 1; i < numberButtons + 1; i++) {
                                Log.d("TEST", hashmap.get("Image filename for button " + String.valueOf(i)).get(0));
                            }
                        }
                        break;
                    case "camera":
                        break;
                    case "ghost":
                        break;
                    case "flash":
                        break;
                    case "image":
                        break;
                    case "micro":
                        break;
                    case "son":
                        break;
                    case "text":
                        break;
                    default :
                        break;
                }
                if(event != null) {
                    amesSequence.addEvent(event);
                }
            }
            Log.d("TEST", amesSequence.toString());
            AMESApplication.application().getAMESManager().getCurrentGame().addSequence(amesSequence);
            }catch (Exception e){

        }

    }
}

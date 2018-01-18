package com.example.iem.ames.parser;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.event.AMESEvent;
import com.example.iem.ames.model.event.EventButton;
import com.example.iem.ames.model.event.EventSound;

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
            DELAY = "Duration",
            NUMBER_OF_BUTTONS = "Number of buttons",
            IMAGE_FILENAME_FOR_BUTTON = "Image filename for button ",
            NEXT_EVENT_INDEX_BUTTON = "Next event index for button ",
            X_POSITION_FOR_BUTTON = "X position for button ",
            Y_POSITION_FOR_BUTTON = "Y position for button ";


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

            Log.d("TEST", listOfEvent.toString());


            for (HashMap<String, ArrayList<String>> hashmap: listOfEvent) {

                String amesEventName = hashmap.get(NAME).get(0);
                String amesEventType = hashmap.get(TYPE).get(0);
                double amesEventDelay = Double.valueOf(hashmap.get(DELAY).get(0));

                AMESEvent event = null;

                switch (hashmap.get(TYPE).get(0)){
                    case "animated text":
                        break;
                    case "animation":
                        break;
                    case "battery level":
                        break;
                    case "button":
                        if(hashmap.containsKey(NUMBER_OF_BUTTONS)) {
                            int numberButtons =  Integer.parseInt(hashmap.get(NUMBER_OF_BUTTONS).get(0));
                            ArrayList<Button> buttons = new ArrayList<>();
                            Log.d("TEST", String.valueOf(numberButtons));
                            for (int i = 0; i < numberButtons; i++) {
                                Log.d("TEST", hashmap.get(IMAGE_FILENAME_FOR_BUTTON + String.valueOf(i+1)).get(0));
                                String buttonFilename = hashmap.get(IMAGE_FILENAME_FOR_BUTTON + String.valueOf(i+1)).get(0);
                                int buttonNextEvent = Integer.valueOf(hashmap.get(NEXT_EVENT_INDEX_BUTTON + String.valueOf(i+1)).get(0));
                                double buttonX = Double.valueOf(hashmap.get(X_POSITION_FOR_BUTTON + String.valueOf(i+1)).get(0));
                                double buttonY = Double.valueOf(hashmap.get(Y_POSITION_FOR_BUTTON + String.valueOf(i+1)).get(0));
                                Button button = new Button(buttonFilename, buttonNextEvent, buttonX, buttonY);
                                buttons.add(button);
                            }
                            event = new EventButton(amesEventName,amesEventType,amesEventDelay,buttons);
                        }else{
                            event = new EventButton(amesEventName,amesEventType,amesEventDelay);
                        }

                        /*int numberButtons =  Integer.parseInt(hashmap.get(NUMBER_OF_BUTTONS).get(0));
                        ArrayList<Button> buttons = new ArrayList<>();
                        for(int i=1; i<numberButtons+2; i++){
                            String buttonFileName = hashmap.get(IMAGE_FILENAME_FOR_BUTTON).get(i);
                        }
                       for(int i =1 ; i < numberButtons + 1;i++){
                            Log.d("TEST", hashmap.get("Image filename for button "+String.valueOf(i)).get(0));

                        }*/

                        //event = new AMESEvent(amesEventName, amesEventType, amesEventDelay);
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
                        event = new EventSound(amesEventName, amesEventType, amesEventDelay, )
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
                e.printStackTrace();
        }

    }
}

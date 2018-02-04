package com.example.iem.ames.parser;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.model.element.Button;
import com.example.iem.ames.model.element.Image;
import com.example.iem.ames.model.element.ImageAnimation;
import com.example.iem.ames.model.element.Text;
import com.example.iem.ames.model.event.AMESEvent;
import com.example.iem.ames.model.event.EventButton;
import com.example.iem.ames.model.event.EventCamera;
import com.example.iem.ames.model.event.EventCheckHeadphones;
import com.example.iem.ames.model.event.EventCheckLight;
import com.example.iem.ames.model.event.EventFlash;
import com.example.iem.ames.model.event.EventImage;
import com.example.iem.ames.model.event.EventMicro;
import com.example.iem.ames.model.event.EventSound;
import com.example.iem.ames.model.event.EventStop;
import com.example.iem.ames.model.event.EventText;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.keiji.plistparser.PListArray;
import io.keiji.plistparser.PListDict;
import io.keiji.plistparser.PListException;
import io.keiji.plistparser.PListObject;
import io.keiji.plistparser.PListParser;

/**
 * Created by iem on 16/01/2018.
 */

public class AMESParser {
    final private String
            CREDITS = "Image fin",
            PARAMETERS = "Parameters",
            KEY = "key",
            STRING = "string",
            REAL = "real",
            INTEGER = "integer",
            NAME = "Name",
            TYPE = "Type",
            DELAY = "Duration",
            INFINITE = "Infinite",
            STOP = "Stop",
            NUMBER_OF_BUTTONS = "Number of buttons",
            IMAGE_FILENAME_FOR_BUTTON = "Image filename for button ",
            NEXT_EVENT_INDEX_BUTTON = "Next event index for button ",
            X_POSITION_FOR_BUTTON = "X position for button ",
            Y_POSITION_FOR_BUTTON = "Y position for button ",
            X_POSITION ="Position X",
            NUMBER_OF_FILE = "Number of images",
            REPEAT_NUMBER = "Number of repeats",
            ANIMATION_DURATION = "Animation duration",
            ANIMATION_POSITION_X = "Animation location X",
            ANIMATION_POSITION_Y = "Animation location Y",
            Y_POSITION ="Position Y",
            SOUND_FILE = "Sound file",
            IMAGE_FILE = "Image file",
            FILENAME_FOR_IMAGES = "Filename for images",
            CHECK_HEADPHONES = "check headphones",
            DISPLAY_OFF = "display Off",
            DISPLAYED_TEXT= "displayed text",
            X_LOCATION="x location",
            Y_LOCATION="y location",
            HEIGHT="height",
            WIDTH="width",
            TEXT_SPEED="text speed",
            OFF = "Off",
            ON_OR_OFF = "On or off",
            REAR_OR_FRONT = "Rear or front",
            OVERLAY_IMAGE_FILE = "Overlay image file",
            TRANSLATION_X = "Translation X",
            TRANSLATION_Y = "Translation Y",
            TRANSLATION_Z = "Translation Z",
            MOVEMENT_DURATION = "Movement duration",
            SCALE_X = "Scale X",
            SCALE_Y = "Scale Y",
            SCALE_X_FOR_BUTTON = "Scale X button ",
            SCALE_Y_FOR_BUTTON = "Scale Y button ",
            OVERLAY_LAYER_NAME = "Overlay layer name";



    public void CreateSequenceFromFile(int idFile){
        Context context = AMESApplication.application().getAMESManager().getContextView();
        AMESSequence amesSequence = new AMESSequence();
        try{

            InputStream inputStream = context.getResources().openRawResource(idFile);

            PListArray listOfEvent = null;

            try {
                listOfEvent = PListParser.parse(inputStream);
                //Log.d("TEST",listOfEvent.toString());

            } catch (PListException e) {
                e.printStackTrace();
            }

            //Log.d("TEST", listOfEvent.toString());

            for (int j = 0; j < listOfEvent.size(); j++) {
                PListDict pListEvent = (PListDict) listOfEvent.get(j);


                PListDict pListEventParameter = null;


                if(pListEvent.has(PARAMETERS)){
                    pListEventParameter = pListEvent.getPListDict(PARAMETERS);
                }

                String amesEventName = getValueInString(pListEvent,NAME);
                String amesEventType = getValueInString(pListEvent,TYPE);


                double amesEventDelay = Double.parseDouble(getValueInString(pListEvent, DELAY));

                AMESEvent event = null;

                switch (getValueInString(pListEvent,TYPE)){
                    case "animated text":
                        if(pListEventParameter.has(DISPLAY_OFF)){
                            event = new EventStop(amesEventName, amesEventType, amesEventDelay);
                        }else {

                            Text text = new Text(getValueInString(pListEventParameter,DISPLAYED_TEXT),
                                    Double.parseDouble(getValueInString(pListEventParameter, X_LOCATION)),
                                    Double.parseDouble(getValueInString(pListEventParameter, Y_LOCATION)),
                                    Double.parseDouble(getValueInString(pListEventParameter, WIDTH)),
                                    Double.parseDouble(getValueInString(pListEventParameter, HEIGHT)),
                                    true,
                                    Double.parseDouble(getValueInString(pListEventParameter, TEXT_SPEED)));
                            event = new EventText(amesEventName,amesEventType,amesEventDelay, text);
                        }
                        break;
                    case "animation":
                        if(pListEventParameter.has(OFF)){
                            event = new EventStop(amesEventName, amesEventType, amesEventDelay);
                        }else {

                            Image imgAnimation = new ImageAnimation(getValueInString(pListEventParameter,FILENAME_FOR_IMAGES),
                                            Double.parseDouble(getValueInString(pListEventParameter, ANIMATION_POSITION_X)),
                                            Double.parseDouble(getValueInString(pListEventParameter, ANIMATION_POSITION_Y)),
                                            true,
                                            Double.parseDouble(getValueInString(pListEventParameter, ANIMATION_DURATION)),
                                            Integer.parseInt(getValueInString(pListEventParameter, NUMBER_OF_FILE)),
                                            Integer.parseInt(getValueInString(pListEventParameter, REPEAT_NUMBER)),
                                            (pListEventParameter.has(TRANSLATION_X)) ? Double.parseDouble(getValueInString(pListEventParameter, TRANSLATION_X)) : 0.0,
                                            (pListEventParameter.has(TRANSLATION_Y)) ? Double.parseDouble(getValueInString(pListEventParameter, TRANSLATION_Y)) : 0.0,
                                            (pListEventParameter.has(TRANSLATION_Z)) ? Double.parseDouble(getValueInString(pListEventParameter, TRANSLATION_Z)) : 0.0,
                                            (pListEventParameter.has(MOVEMENT_DURATION)) ? Double.parseDouble(getValueInString(pListEventParameter, MOVEMENT_DURATION)) : 0.0,
                                            (pListEventParameter.has(SCALE_X)) ? Double.parseDouble(getValueInString(pListEventParameter, SCALE_X)) : 0.0,
                                            (pListEventParameter.has(SCALE_Y)) ? Double.parseDouble(getValueInString(pListEventParameter, SCALE_Y)) : 0.0);
                            event = new EventImage(amesEventName, amesEventType, amesEventDelay, imgAnimation);
                        }
                       
                        break;
                    case "image":
                        if(amesEventName.equals(CREDITS)){
                            amesSequence.setCreditsEventIndex(j);
                        }
                        if(pListEventParameter.has(OFF)){
                            event = new EventStop(amesEventName, amesEventType, amesEventDelay);
                        }
                        else{
                        Image img = new Image(getValueInString(pListEventParameter, IMAGE_FILE),
                                Double.parseDouble(getValueInString(pListEventParameter, X_POSITION)),
                                Double.parseDouble(getValueInString(pListEventParameter, Y_POSITION)),
                                false,
                                Double.parseDouble(getValueInString(pListEventParameter, SCALE_X)),
                                Double.parseDouble(getValueInString(pListEventParameter, SCALE_Y)));
                        event = new EventImage(amesEventName, amesEventType, amesEventDelay, img);
                        }
                        break;
                    case "battery level":
                        Image img = new Image("battery_middle.png",
                                0.9,
                                0.9,
                                false,
                                0.1,
                                0.1);
                        event = new EventImage(amesEventName, "image", amesEventDelay, img);
                        break;
                    case "button":
                        if(pListEventParameter.has(OFF)){
                            event = new EventStop(amesEventName,amesEventType,amesEventDelay);
                        }else {
                            if (pListEventParameter.has(NUMBER_OF_BUTTONS)) {
                                int numberButtons = Integer.parseInt(getValueInString(pListEventParameter, NUMBER_OF_BUTTONS));
                                ArrayList<Button> buttons = new ArrayList<>();
                                //Log.d("TEST", String.valueOf(numberButtons));
                                for (int i = 0; i < numberButtons; i++) {
                                    //Log.d("TEST", getValueInString(pListEventParameter,IMAGE_FILENAME_FOR_BUTTON + String.valueOf(i + 1)));
                                    String buttonFilename = getValueInString(pListEventParameter,IMAGE_FILENAME_FOR_BUTTON + String.valueOf(i + 1));
                                    int buttonNextEvent = Integer.parseInt(getValueInString(pListEventParameter, NEXT_EVENT_INDEX_BUTTON + String.valueOf(i + 1)));
                                    double buttonX = Double.parseDouble(getValueInString(pListEventParameter, X_POSITION_FOR_BUTTON + String.valueOf(i + 1)));
                                    double buttonY = Double.parseDouble(getValueInString(pListEventParameter, Y_POSITION_FOR_BUTTON + String.valueOf(i + 1)));
                                    double buttonScaleX = Double.parseDouble(getValueInString(pListEventParameter, SCALE_X_FOR_BUTTON + String.valueOf(i + 1)));
                                    double buttonScaleY = Double.parseDouble(getValueInString(pListEventParameter, SCALE_Y_FOR_BUTTON + String.valueOf(i + 1)));
                                    Button button = new Button(buttonFilename, buttonNextEvent, buttonX, buttonY, buttonScaleX, buttonScaleY);
                                    buttons.add(button);
                                }
                                event = new EventButton(amesEventName, amesEventType, amesEventDelay, buttons);
                            } else {
                                event = new EventButton(amesEventName, amesEventType, amesEventDelay);
                            }
                        }
                        break;
                    case "camera":
                        if(pListEventParameter.getBool(ON_OR_OFF)){
                            boolean rear_or_front = pListEventParameter.getBool(REAR_OR_FRONT);
                            String filename = "";
                            if(pListEventParameter.has(OVERLAY_IMAGE_FILE)) {
                                filename = pListEventParameter.getString(OVERLAY_IMAGE_FILE);
                            }else if(pListEventParameter.has(OVERLAY_LAYER_NAME)){
                                filename = pListEventParameter.getString(OVERLAY_LAYER_NAME);
                            }
                            Image image = new Image(filename, 0.5 , 0.5, false,1,1);
                            event = new EventCamera(amesEventName, amesEventType, amesEventDelay, rear_or_front, image);
                        }else{
                            event = new EventStop(amesEventName, amesEventType, amesEventDelay);
                        }
                        break;
                    case "ghost":
                        break;
                    case "flash":
                        if(pListEventParameter.has(ON_OR_OFF)) {
                            if(pListEventParameter.getBool(ON_OR_OFF)) {
                                event = new EventFlash(amesEventName, amesEventType, amesEventDelay);
                            }else{
                                event = new EventStop(amesEventName,amesEventType,amesEventDelay);
                            }
                        }
                        break;
                    case "micro":
                        event = new EventMicro(amesEventName, amesEventType, amesEventDelay);
                        break;
                    case "son":
                        if(pListEventParameter.has(STOP)){
                            event = new EventStop(amesEventName,amesEventType,amesEventDelay);
                        }
                        else{
                            if(pListEventParameter.has(INFINITE)){
                                event = new EventSound(amesEventName, amesEventType, amesEventDelay, getValueInString(pListEventParameter, SOUND_FILE), true);
                            }
                            else{
                                event = new EventSound(amesEventName, amesEventType, amesEventDelay, getValueInString(pListEventParameter, SOUND_FILE), false);
                            }
                        }

                        break;
                    case "text":
                        if(pListEventParameter.has(DISPLAY_OFF)){

                        }else{
                            Text text = new Text((pListEventParameter.has(DISPLAYED_TEXT)) ? getValueInString(pListEventParameter,DISPLAYED_TEXT) : "",
                                    Double.parseDouble(getValueInString(pListEventParameter, X_LOCATION)),
                                    Double.parseDouble(getValueInString(pListEventParameter, Y_LOCATION)),
                                    Double.parseDouble(getValueInString(pListEventParameter, WIDTH)),
                                    Double.parseDouble(getValueInString(pListEventParameter, HEIGHT)),
                                    false,
                                    0);
                            event = new EventText(amesEventName,amesEventType,amesEventDelay, text);
                        }
                        break;
                    case  "check headphones":
                        event = new EventCheckHeadphones(amesEventName, amesEventType, amesEventDelay);
                        break;
                    case "check light":
                        event = new EventCheckLight(amesEventName,amesEventType,amesEventDelay);
                        break;
                    default :
                        break;
                }
                if(event != null) {
                    amesSequence.addEvent(event);
                }
                
            }
            Log.d("TEST", amesSequence.toString());
            Log.d("TEST", String.valueOf(amesSequence.getEvents().size()));
            AMESApplication.application().getAMESManager().getCurrentGame().addSequence(amesSequence);
            }catch (Exception e){
                e.printStackTrace();
        }
    }

    public String getValueInString(PListDict pListDict, String key){
        String val = null;
        try{
            val = String.valueOf(pListDict.getString(key));
            return val;
        }catch (Exception e){
        }
        try{
            val = String.valueOf(pListDict.getReal(key));
            return val;
        }catch (Exception e){
        }
        try{
            val = String.valueOf(pListDict.getBool(key));
            return val;
        }catch (Exception e){
        }
        try{
            val = String.valueOf(pListDict.getInt(key));
            return val;
        }catch (Exception e){
        }
        try{
            val = String.valueOf(pListDict.getDate(key));
            return val;
        }catch (Exception e){
        }
        return val;
    }
}

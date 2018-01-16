package com.example.iem.ames.model;

import com.example.iem.ames.model.event.AMESEvent;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESSequence {
    private ArrayList<AMESEvent> events;
    private  int nextEventIndex;
    private String backgroundSound;
    private AMESGame associatedGame;
}

package com.example.iem.ames.controller;

import com.example.iem.ames.model.AMESSequence;
import com.example.iem.ames.model.event.AMESEvent;

/**
 * Created by iem on 18/01/2018.
 */

public class AMESEventHandler {

   public void AMESEventHandler(AMESEvent event)
    {
        //TODO KESAKO
        //self.mainTextView.userInteractionEnabled = NO;
        event.run();
    }

}

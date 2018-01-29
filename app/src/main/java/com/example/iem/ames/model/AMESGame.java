package com.example.iem.ames.model;

import android.util.Log;

import com.example.iem.ames.AMESApplication;
import com.example.iem.ames.R;
import com.example.iem.ames.model.element.Screen;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESGame {
    private ArrayList<AMESSequence> sequences;
    private int currentSequenceIndex;

    public AMESGame() {
        this.sequences = new ArrayList<AMESSequence>();
        this.currentSequenceIndex = 0;
    }

    public ArrayList<AMESSequence> getSequences() {
        return sequences;
    }

    public void setSequences(ArrayList<AMESSequence> sequences) {
        this.sequences = sequences;
    }

    public void addSequence(AMESSequence sequence) {
        this.sequences.add(sequence);
    }

    public AMESSequence getSequence(int index) {
        Log.d("DEBUG", String.valueOf(index));
        return this.sequences.get(index);
    }

    public int getCurrentSequenceIndex() {
        return currentSequenceIndex;
    }

    public void setCurrentSequenceIndex(int currentSequenceIndex) {
        this.currentSequenceIndex = currentSequenceIndex;
    }

    public void prepareNextSequence(){
        Screen screen = AMESApplication.application().getAMESManager().getScreen();
        screen.getRelativeLayout().removeAllViews();
        this.currentSequenceIndex++;
        this.run();
    }
    public void run(){
        if (currentSequenceIndex < this.sequences.size())
        {
            sequences.get(currentSequenceIndex).run();
        }
        // TODO QUIT
    }
}

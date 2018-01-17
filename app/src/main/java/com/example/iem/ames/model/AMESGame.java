package com.example.iem.ames.model;

import java.util.ArrayList;

/**
 * Created by Jo' on 16/01/2018.
 */

public class AMESGame {
    private ArrayList<AMESSequence> sequences;
    private int currentSequenceIndex;

    public AMESGame() {
        this.sequences = new ArrayList<AMESSequence>();
    }

    public ArrayList<AMESSequence> getSequences() {
        return sequences;
    }

    public void setSequences(ArrayList<AMESSequence> sequences) {
        for (AMESSequence amesEvent: sequences) {
            this.sequences.add(amesEvent);
        }
    }

    public void addSequence(AMESSequence sequence) {
        this.sequences.add(sequence);
    }

    public int getCurrentSequenceIndex() {
        return currentSequenceIndex;
    }

    public void setCurrentSequenceIndex(int currentSequenceIndex) {
        this.currentSequenceIndex = currentSequenceIndex;
    }
}

package com.example.iem.ames.manager;

import android.content.Context;

import com.example.iem.ames.model.AMESGame;
import com.example.iem.ames.parser.AMESParser;

/**
 * Created by iem on 17/01/2018.
 */

public class AMESManager {
    AMESGame currentGame;
    Context contextView;
    AMESParser parser;

    public AMESManager(AMESGame currentGame) {
        this.currentGame = currentGame;
    }

    public AMESManager() {
        currentGame = new AMESGame();
        contextView = null;
        parser = new AMESParser();
    }

    public AMESGame getCurrentGame(){
        return currentGame;
    }

    public Context getContextView() {
        return contextView;
    }

    public void setContextView(Context contextView) {
        this.contextView = contextView;
    }

    public AMESParser getParser() {
        return parser;
    }

    public void setParser(AMESParser parser) {
        this.parser = parser;
    }
}

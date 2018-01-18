package com.example.iem.ames.manager;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.iem.ames.model.AMESGame;
import com.example.iem.ames.model.element.Screen;
import com.example.iem.ames.parser.AMESParser;

/**
 * Created by iem on 17/01/2018.
 */

public class AMESManager {
    private AMESGame currentGame;
    private Context contextView;
    private AMESParser parser;
    private TextManager textManager;
    private Screen screen;
    private ImageManager imageManager;

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

    public Screen getScreen() {
        return screen;
    }

    public void createManager(Screen screen) {
        this.screen = screen;
        this.textManager = new TextManager(this.contextView, this.screen);
        this.imageManager = new ImageManager(this.contextView, this.screen);
    }

    public TextManager getTextManager() {
        return textManager;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }
}

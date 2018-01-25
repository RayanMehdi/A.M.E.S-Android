package com.example.iem.ames.manager;

import android.app.Activity;
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
    private ButtonManager buttonManager;
    private SoundManager soundManager;
    private AccelerometerManager accelerometerManager;
    private CheckHeadphonesManager checkHeadphonesManager;
    private CheckLightManager checkLightManager;
    private StopManager stopManager;
    private CameraManager cameraManager;
    private MicroManager microManager;

    public AMESManager(AMESGame currentGame) {
        this.currentGame = currentGame;
    }

    public AMESManager() {
        currentGame = new AMESGame();
        contextView = null;
        parser = new AMESParser();
    }

    public StopManager getStopManager() {
        return stopManager;
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

    public void createManager(Screen screen, Activity activity) {
        this.screen = screen;
        this.textManager = new TextManager(this.contextView, this.screen);
        this.imageManager = new ImageManager(this.contextView, this.screen);
        this.buttonManager = new ButtonManager(this.contextView, this.screen);
        this.soundManager = new SoundManager(this.contextView);
        this.checkHeadphonesManager = new CheckHeadphonesManager(this.contextView);
        this.checkLightManager = new CheckLightManager(this.contextView);
        this.cameraManager = new CameraManager(activity, this.screen);
        this.microManager = new MicroManager();

        this.stopManager = new StopManager();
    }

    public TextManager getTextManager() {
        return textManager;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public AccelerometerManager getAccelerometerManager() {
        return accelerometerManager;
    }

    public CheckHeadphonesManager getCheckHeadphonesManager() {
        return checkHeadphonesManager;
    }

    public CheckLightManager getCheckLightManager() {
        return checkLightManager;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public MicroManager getMicroManager() {
        return microManager;
    }
}

package com.example.iem.ames.manager;

import com.example.iem.ames.model.AMESGame;

/**
 * Created by iem on 17/01/2018.
 */

public class AMESManager {
    AMESGame currentGame;

    public AMESManager(AMESGame currentGame) {
        this.currentGame = currentGame;
    }

    public AMESManager() {
        currentGame = new AMESGame();
    }

    public AMESGame getCurrentGame(){
        return currentGame;
    }


}

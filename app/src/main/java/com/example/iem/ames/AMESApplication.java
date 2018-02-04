package com.example.iem.ames;

import android.app.Application;

import com.example.iem.ames.manager.AMESManager;

/**
 * Created by iem on 17/01/2018.
 */

public class AMESApplication extends Application{
    // Variable privée qui retiendra l'instance de l'objet application
    private static AMESApplication application;

    // getter public pour récupérer l'instance statique de l'objet application
    public static AMESApplication application() {
        return application;
    }

    // Variable privée qui retiendra la seule instance du manager
    private AMESManager amesManager;


    // getter public pour récupérer l'instance unique de l'objet manager
    public AMESManager getAMESManager() {
        return amesManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        this.amesManager = new AMESManager();
    }
}

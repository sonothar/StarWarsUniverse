package de.sonothar.starwarsuniverse;

import android.app.Application;

import de.sonothar.starwarsuniverse.sw.StarWarsApi;

/**
 * Created by dennis on 30.03.15.
 */
public class SWUniverseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        StarWarsApi.init();
    }
}

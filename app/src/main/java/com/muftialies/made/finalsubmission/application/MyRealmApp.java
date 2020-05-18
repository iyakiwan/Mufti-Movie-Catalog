package com.muftialies.made.finalsubmission.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyRealmApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("favorite.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

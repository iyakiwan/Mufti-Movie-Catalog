package com.muftialies.made.finalsubmission.sharedpreferance;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedReminder {
    private static final String PREFS_NAME = "reminder_pref";

    private final String KEY_REMINDER_DAILY = "key_reminder_daily";
    private final String KEY_REMINDER_MOVIE_RELEASE = "key_reminder_movie_release";

    private final SharedPreferences preferences;

    public SharedReminder(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setDailyReminder(boolean reminder){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_REMINDER_DAILY, reminder);
        editor.apply();
    }

    public void setReleaseMovieReminder(boolean reminder){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_REMINDER_MOVIE_RELEASE, reminder);
        editor.apply();
    }

    public boolean getDailyReminder(){
        return preferences.getBoolean(KEY_REMINDER_DAILY, false);
    }

    public boolean getReleaseMovieReminder(){
        return preferences.getBoolean(KEY_REMINDER_MOVIE_RELEASE, false);
    }
}

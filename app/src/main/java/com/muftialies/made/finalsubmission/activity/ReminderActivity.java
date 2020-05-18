package com.muftialies.made.finalsubmission.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.notification.ReminderDailyHelper;
import com.muftialies.made.finalsubmission.notification.ReminderReleaseMovieHelper;
import com.muftialies.made.finalsubmission.sharedpreferance.SharedReminder;


public class ReminderActivity extends AppCompatActivity {
    private Switch sw_daily, sw_today;
    private SharedReminder sharedReminder;
    private ReminderDailyHelper reminderDailyHelper;
    private ReminderReleaseMovieHelper reminderReleaseMovieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sharedReminder = new SharedReminder(ReminderActivity.this);
        reminderDailyHelper = new ReminderDailyHelper();
        reminderReleaseMovieHelper = new ReminderReleaseMovieHelper();

        sw_daily = findViewById(R.id.sw_reminder_1);
        sw_today = findViewById(R.id.sw_reminder_2);

        if (sharedReminder.getDailyReminder()) {
            sw_daily.setChecked(true);
        }
        if (sharedReminder.getReleaseMovieReminder()) {
            sw_today.setChecked(true);
        }

        sw_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_daily.isChecked()) {
                    reminderDailyHelper.setReminderDaily(ReminderActivity.this);
                    sharedReminder.setDailyReminder(true);
                } else {
                    reminderDailyHelper.cancelReminderDaily(ReminderActivity.this);
                    sharedReminder.setDailyReminder(false);
                }
            }
        });

        sw_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_today.isChecked()) {
                    reminderReleaseMovieHelper.setReminderReleaseMovie(ReminderActivity.this);
                    sharedReminder.setReleaseMovieReminder(true);
                } else {
                    reminderReleaseMovieHelper.cancelReminderReleaseMovie(ReminderActivity.this);
                    sharedReminder.setReleaseMovieReminder(false);
                }
            }
        });
    }
}

package com.muftialies.made.finalsubmission.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.fragment.FavoriteFragment;
import com.muftialies.made.finalsubmission.fragment.MovieFragment;
import com.muftialies.made.finalsubmission.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragmentApp;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragmentApp = new MovieFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragmentApp, fragmentApp.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tv_show:
                    fragmentApp = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragmentApp, fragmentApp.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorite:
                    fragmentApp = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragmentApp, fragmentApp.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_movie);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_reminder_settings) {
            Intent mIntent = new Intent(this, ReminderActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.muftialies.made.finalsubmission.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.muftialies.made.finalsubmission.model.MovieModel;
import com.muftialies.made.finalsubmission.model.TvShowModel;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.muftialies.made.finalsubmission.provider.DatabaseContract.*;

public class ShowProvider extends ContentProvider {
    private static final int SHOW_MOV = 51;
    private static final int SHOW_TV = 52;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.example.rgher.realmtodo/tasks
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/movie", SHOW_MOV);

        // content://com.example.rgher.realmtodo/tasks/id
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME+ "/tv", SHOW_TV);
    }
    @Override
    public boolean onCreate() {
        //Innitializing RealmDB
        /*Realm.init(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new MyRealmMigration())
                .build();
        Realm.setDefaultConfiguration(config);*/

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int match = sUriMatcher.match(uri);
        Log.v("RealmDB","cekoyy");
        //Get Realm Instance
//        Realm realm = Realm.getDefaultInstance();
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(configuration);
        MatrixCursor myCursor = new MatrixCursor( new String[]{ShowColumns.ID, ShowColumns.TITLE, ShowColumns.OVERVIEW
                , ShowColumns.RATING, ShowColumns.IMAGE_POSTER, ShowColumns.DATE});

        try {
            switch (match) {
                //Expected "query all" Uri: content://com.example.rgher.realmtodo/tasks

                case SHOW_MOV:
                    Log.v("RealmDB","cek1");
                    RealmResults<MovieModel> movieRealmResult = realm.where(MovieModel.class).findAll();
                    for (MovieModel myModel : movieRealmResult) {
                        Object[] rowData = new Object[]{myModel.getMovieId(), myModel.getMovieTitle(), myModel.getMovieOverview()
                                , myModel.getMovieRating(), myModel.getMoviePoster(), myModel.getMovieRelease()};
                        myCursor.addRow(rowData);
                        Log.v("RealmDB", myModel.toString());
                    }
                    Log.v("RealmDB","cek2");
                    break;

                //Expected "query one" Uri: content://com.example.rgher.realmtodo/tasks/{id}
                case SHOW_TV:
                    Log.v("RealmDB","cek4");
                    RealmResults<TvShowModel> tvRealmResults = realm.where(TvShowModel.class).findAll();
                    for (TvShowModel myModel : tvRealmResults) {
                        Object[] rowData = new Object[]{myModel.getTvId(), myModel.getTvTitle(), myModel.getTvOverview()
                                , myModel.getTvRating(), myModel.getTvPoster(), myModel.getTvAiring()};
                        myCursor.addRow(rowData);
                        Log.v("RealmDB", myModel.toString());
                    }
                    Log.v("RealmDB","cek5");
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }


            myCursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        } finally {
            realm.close();
        }
        return myCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

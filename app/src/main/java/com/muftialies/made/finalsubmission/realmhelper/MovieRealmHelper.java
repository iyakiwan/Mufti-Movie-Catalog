package com.muftialies.made.finalsubmission.realmhelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.muftialies.made.finalsubmission.model.MovieModel;

import java.util.List;

import io.realm.Realm;

public class MovieRealmHelper {
    private Realm realm;

    public MovieRealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void insert(final MovieModel movieModel) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealm(movieModel);
                Log.e("OnInsert", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("OnErrorRealm", error.getMessage());
            }
        });
    }

    public List<MovieModel> readAllMovie() {
        return realm.where(MovieModel.class).findAll();
    }

    public long checkMovie(final String id) {
        return realm.where(MovieModel.class).equalTo("MovieId", id).count();
    }

    public MovieModel readSelectedMovie(final String id) {
        return realm.where(MovieModel.class).equalTo("MovieId", id).findFirst();
    }

    public void deleteById(final String id) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                MovieModel model = realm.where(MovieModel.class).equalTo("MovieId", id).findFirst();
                assert model != null;
                model.deleteFromRealm();
                Log.e("Delete", "Success deleted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("OnErrorRealm", error.getMessage());
            }
        });
    }
}

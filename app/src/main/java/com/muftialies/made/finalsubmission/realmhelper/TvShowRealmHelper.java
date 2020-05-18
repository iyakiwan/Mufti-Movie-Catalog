package com.muftialies.made.finalsubmission.realmhelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.muftialies.made.finalsubmission.model.TvShowModel;

import java.util.List;

import io.realm.Realm;

public class TvShowRealmHelper {
    private Realm realm;

    public TvShowRealmHelper(Realm realm){
        this.realm = realm;
    }

    public void insert(final TvShowModel tvShowModel){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                Log.e("Created", "Database was created");
                realm.copyToRealm(tvShowModel);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("OnErrorRealm", error.getMessage());
            }
        });
    }

    public List<TvShowModel> readAllTvShow(){
        return realm.where(TvShowModel.class).findAll();
    }

    public long checkTvShow(final String id){
        return realm.where(TvShowModel.class).equalTo("TvId", id).count();
    }

    public TvShowModel readSelectedTvShow(final String id) {
        return realm.where(TvShowModel.class).equalTo("TvId", id).findFirst();
    }

    public void deleteById(final String id){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                TvShowModel model = realm.where(TvShowModel.class).equalTo("TvId", id).findFirst();
                assert model != null;
                model.deleteFromRealm();
                Log.e("Delete", "Successfully deleted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                Log.e("OnErrorRealm", error.getMessage());

            }
        });
    }
}

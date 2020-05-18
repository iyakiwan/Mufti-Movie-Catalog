package com.muftialies.made.finalsubmission.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.realmhelper.MovieRealmHelper;
import com.muftialies.made.finalsubmission.model.MovieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<String> mWidgetItemsFavorite;
    private ArrayList<String> widgetIdItemFavorite;
    private ArrayList<String> widgetNameItemFavorite;

    private Context mContext;
    private static final String url = "https://image.tmdb.org/t/p/w342";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(configuration);

        MovieRealmHelper movieRealmHelper = new MovieRealmHelper(realm);
        List<MovieModel> imageMovie = movieRealmHelper.readAllMovie();

        mWidgetItemsFavorite = new ArrayList<>();
        widgetIdItemFavorite = new ArrayList<>();
        widgetNameItemFavorite = new ArrayList<>();

        for (int i = 0; i < imageMovie.size(); i++) {
            mWidgetItemsFavorite.add(imageMovie.get(i).getMoviePoster());
            widgetIdItemFavorite.add(imageMovie.get(i).getMovieId());
            widgetNameItemFavorite.add(imageMovie.get(i).getMovieTitle());
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItemsFavorite.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_favorite);
        try {
            Bitmap image = Glide.with(mContext)
                    .asBitmap()
                    .load(url + mWidgetItemsFavorite.get(position))
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.img_favorite_widget, image);
//            Log.d("mWidget", url + mWidgetItemsFavorite.get(position) + " " + position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putString(FavoriteMovieWidget.EXTRA_ITEM_1, widgetIdItemFavorite.get(position));
        extras.putString(FavoriteMovieWidget.EXTRA_ITEM_2, widgetNameItemFavorite.get(position));

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.img_favorite_widget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

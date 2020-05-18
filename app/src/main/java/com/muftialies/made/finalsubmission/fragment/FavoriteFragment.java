package com.muftialies.made.finalsubmission.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leochuan.ScaleLayoutManager;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.adapter.FavoriteAdapterMovie;
import com.muftialies.made.finalsubmission.adapter.FavoriteAdapterTvShow;
import com.muftialies.made.finalsubmission.model.MovieModel;
import com.muftialies.made.finalsubmission.model.TvShowModel;
import com.muftialies.made.finalsubmission.realmhelper.MovieRealmHelper;
import com.muftialies.made.finalsubmission.realmhelper.TvShowRealmHelper;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private FavoriteAdapterMovie favoriteAdapterMovie;
    private FavoriteAdapterTvShow favoriteAdapterTvShow;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteAdapterMovie = new FavoriteAdapterMovie();
        favoriteAdapterMovie.notifyDataSetChanged();

        favoriteAdapterTvShow = new FavoriteAdapterTvShow();
        favoriteAdapterTvShow.notifyDataSetChanged();


        ScaleLayoutManager scaleLayoutManagerMovie = new ScaleLayoutManager(view.getContext(), 0);
        scaleLayoutManagerMovie.getCurrentPosition();

        ScaleLayoutManager scaleLayoutManagerTvShow = new ScaleLayoutManager(view.getContext(), 0);
        scaleLayoutManagerTvShow.getCurrentPosition();


        RecyclerView favoriteRecyclerViewMovie = view.findViewById(R.id.rv_fragment_favorite_movie);
        favoriteRecyclerViewMovie.setLayoutManager(scaleLayoutManagerMovie);
        favoriteRecyclerViewMovie.setAdapter(favoriteAdapterMovie);

        RecyclerView favoriteRecyclerViewTvShow = view.findViewById(R.id.rv_fragment_favorite_tv);
        favoriteRecyclerViewTvShow.setLayoutManager(scaleLayoutManagerTvShow);
        favoriteRecyclerViewTvShow.setAdapter(favoriteAdapterTvShow);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(configuration);

        MovieRealmHelper movieRealmHelper = new MovieRealmHelper(realm);
        List<MovieModel> movieModels;

        TvShowRealmHelper tvShowRealmHelper = new TvShowRealmHelper(realm);
        List<TvShowModel> tvShowModels;

        movieModels = movieRealmHelper.readAllMovie();
        tvShowModels = tvShowRealmHelper.readAllTvShow();

        favoriteAdapterMovie.setData(movieModels);
        favoriteAdapterTvShow.setData(tvShowModels);
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteAdapterMovie.notifyDataSetChanged();
        favoriteAdapterTvShow.notifyDataSetChanged();
    }
}

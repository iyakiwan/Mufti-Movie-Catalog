package com.muftialies.made.finalsubmission.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.model.MovieModel;
import com.muftialies.made.finalsubmission.model.TvShowModel;
import com.muftialies.made.finalsubmission.realmhelper.MovieRealmHelper;
import com.muftialies.made.finalsubmission.realmhelper.TvShowRealmHelper;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteDetailActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_SHOW = "extra_show";
    public static final String EXTRA_TITLE = "extra_title";
    private ImageView imgPoster, imgPosterMini;
    private TextView tvTitle, tvGenre, tvRuntime, tvRelease, tvLanguage, tvOverview, tvRating;
    private AppCompatRatingBar RatingBar;
    private String ShowId, show;
    private MovieRealmHelper movieRealmHelper;
    private TvShowRealmHelper tvShowRealmHelper;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));

        Realm.init(FavoriteDetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(configuration);

        movieRealmHelper = new MovieRealmHelper(realm);
        tvShowRealmHelper = new TvShowRealmHelper(realm);

        FloatingActionButton fabDelete = findViewById(R.id.fab_delete);
        RatingBar = findViewById(R.id.rb_detail_rating);
        MaterialFavoriteButton favoriteButton = findViewById(R.id.fb_detail_Favorite);
        imgPosterMini = findViewById(R.id.img_detail_poster_mini);
        imgPoster = findViewById(R.id.img_detail_poster);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvGenre = findViewById(R.id.tv_detail_genre);
        tvRuntime = findViewById(R.id.tv_detail_runtime);
        tvRelease = findViewById(R.id.tv_detail_release);
        tvLanguage = findViewById(R.id.tv_detail_language);
        tvOverview = findViewById(R.id.tv_detail_overview);
        tvRating = findViewById(R.id.tv_detail_rating);

        AVLoadingIndicatorView loadingHeader = findViewById(R.id.loading_detail_header);
        AVLoadingIndicatorView loadingBody = findViewById(R.id.loading_detail_body);

        TextView tvNetworks = findViewById(R.id.textView_runtime_networks);
        TextView tvDate = findViewById(R.id.textView_release_air);

        favoriteButton.setVisibility(View.GONE);
        loadingHeader.setVisibility(View.GONE);
        loadingBody.setVisibility(View.GONE);
        fabDelete.setVisibility(View.VISIBLE);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_condensed_bold);
        collapsingToolbar.setExpandedTitleTypeface(typeface);
        collapsingToolbar.setCollapsedTitleTypeface(typeface);

        ShowId = getIntent().getStringExtra(EXTRA_ID);
        show = getIntent().getStringExtra(EXTRA_SHOW);

        if (show.equals("movie")) {
            showDataMovie(ShowId);
        } else if (show.equals("tv")) {
            tvNetworks.setText(getString(R.string.networks));
            tvDate.setText(getString(R.string.last_air_date));

            showDataTvShow(ShowId);
        }

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        String dialogTitle, dialogMessage;

        dialogMessage = getString(R.string.alert_delete_message);
        dialogTitle = getString(R.string.alert_delete_title);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage).setCancelable(false).setPositiveButton(getString(R.string.alert_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (show.equals("movie")) {
                    movieRealmHelper.deleteById(ShowId);
                } else if (show.equals("tv")) {
                    tvShowRealmHelper.deleteById(ShowId);
                }
                finish();
            }
        }).setNegativeButton(getString(R.string.alert_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showDataMovie(String id) {
        MovieModel movieModel = movieRealmHelper.readSelectedMovie(id);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w400" + movieModel.getMoviePosterMini())
                .apply(new RequestOptions())
                .into(imgPoster);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w342" + movieModel.getMoviePoster())
                .apply(new RequestOptions())
                .into(imgPosterMini);
        tvTitle.setText(movieModel.getMovieTitle());
        tvGenre.setText(movieModel.getMovieGenre());
        tvRuntime.setText(movieModel.getMovieRuntime());
        tvRelease.setText(movieModel.getMovieRelease());
        tvLanguage.setText(movieModel.getMovieLanguage());
        tvOverview.setText(movieModel.getMovieOverview());
        tvRating.setText(movieModel.getMovieRating());
        float ratingValue = Float.parseFloat(movieModel.getMovieRating()) / 2;
        RatingBar.setRating(ratingValue);

    }

    private void showDataTvShow(String id) {
        TvShowModel tvShowModel = tvShowRealmHelper.readSelectedTvShow(id);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w400" + tvShowModel.getTvPosterMini())
                .apply(new RequestOptions())
                .into(imgPoster);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w342" + tvShowModel.getTvPoster())
                .apply(new RequestOptions())
                .into(imgPosterMini);
        tvTitle.setText(tvShowModel.getTvTitle());
        tvGenre.setText(tvShowModel.getTvGenre());
        tvRuntime.setText(tvShowModel.getTvNetwork());
        tvRelease.setText(tvShowModel.getTvAiring());
        tvLanguage.setText(tvShowModel.getTvLanguage());
        tvOverview.setText(tvShowModel.getTvOverview());
        tvRating.setText(tvShowModel.getTvRating());
        float ratingValue = Float.parseFloat(tvShowModel.getTvRating()) / 2;
        RatingBar.setRating(ratingValue);
    }
}

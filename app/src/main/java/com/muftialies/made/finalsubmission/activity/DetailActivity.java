package com.muftialies.made.finalsubmission.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.model.MovieModel;
import com.muftialies.made.finalsubmission.model.TvShowModel;
import com.muftialies.made.finalsubmission.realmhelper.MovieRealmHelper;
import com.muftialies.made.finalsubmission.realmhelper.TvShowRealmHelper;
import com.muftialies.made.finalsubmission.viewmodel.MovieDetailViewModel;
import com.muftialies.made.finalsubmission.viewmodel.TvShowDetailViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_SHOW = "extra_show";
    private ImageView imgPoster, imgPosterMini;
    private TextView tvTitle, tvGenre, tvRuntime, tvRelease, tvLanguage, tvOverview, tvRating;
    private AppCompatRatingBar RatingBar;
    private String ShowId, ShowPoster, ShowPosterBack;
    private Boolean CheckFav = false;
    private MaterialFavoriteButton favoriteButton;
    private AVLoadingIndicatorView loadingHeader, loadingBody;
    private View viewContentDetailMovie;
    private Realm realm;
    private MovieRealmHelper movieRealmHelper;
    private MovieModel movieModel;
    private TvShowRealmHelper tvShowRealmHelper;
    private TvShowModel tvShowModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        }

        Realm.init(DetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        movieRealmHelper = new MovieRealmHelper(realm);
        tvShowRealmHelper = new TvShowRealmHelper(realm);


        favoriteButton = findViewById(R.id.fb_detail_Favorite);
        RatingBar = findViewById(R.id.rb_detail_rating);
        imgPosterMini = findViewById(R.id.img_detail_poster_mini);
        imgPoster = findViewById(R.id.img_detail_poster);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvGenre = findViewById(R.id.tv_detail_genre);
        tvRuntime = findViewById(R.id.tv_detail_runtime);
        tvRelease = findViewById(R.id.tv_detail_release);
        tvLanguage = findViewById(R.id.tv_detail_language);
        tvOverview = findViewById(R.id.tv_detail_overview);
        tvRating = findViewById(R.id.tv_detail_rating);

        TextView tvNetworks = findViewById(R.id.textView_runtime_networks);
        TextView tvDate = findViewById(R.id.textView_release_air);

        loadingHeader = findViewById(R.id.loading_detail_header);
        loadingBody = findViewById(R.id.loading_detail_body);

        viewContentDetailMovie = findViewById(R.id.content_detail);

        viewContentDetailMovie.setVisibility(View.GONE);
        favoriteButton.setVisibility(View.GONE);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.roboto_condensed_bold);
        collapsingToolbar.setExpandedTitleTypeface(typeface);
        collapsingToolbar.setCollapsedTitleTypeface(typeface);

        ShowId = getIntent().getStringExtra(EXTRA_ID);
        final String show = getIntent().getStringExtra(EXTRA_SHOW);

        if (show.equals("movie")) {
            MovieDetailViewModel mainViewModelDetailMovie = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
            mainViewModelDetailMovie.setContext(this);

            mainViewModelDetailMovie.setDetailMovie(ShowId);
            mainViewModelDetailMovie.getDetailMovie().observe(this, getDetailMovie);
        } else if (show.equals("tv")) {
            tvNetworks.setText(getString(R.string.networks));
            tvDate.setText(getString(R.string.last_air_date));

            TvShowDetailViewModel mainViewModelDetailTvShow = ViewModelProviders.of(this).get(TvShowDetailViewModel.class);
            mainViewModelDetailTvShow.setContext(this);

            mainViewModelDetailTvShow.setDetailTvShow(ShowId);
            mainViewModelDetailTvShow.getDetailTvShow().observe(this, getDetailTv);
        }

        favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                movieRealmHelper = new MovieRealmHelper(realm);
                if (favorite && !CheckFav) {
                    if (show.equals("movie")) {
                        movieRealmHelper.insert(movieModel);
                    } else if (show.equals("tv")) {
                        tvShowRealmHelper.insert(tvShowModel);
                    }

                    CheckFav = true;
                    Toast.makeText(DetailActivity.this, getString(R.string.realm_insert_notification), Toast.LENGTH_SHORT).show();

                } else if (!favorite && CheckFav) {
                    if (show.equals("movie")) {
                        movieRealmHelper.deleteById(ShowId);
                    } else if (show.equals("tv")) {
                        tvShowRealmHelper.deleteById(ShowId);
                    }

                    CheckFav = false;
                    Toast.makeText(DetailActivity.this, getString(R.string.realm_delete_notification), Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (movieRealmHelper.checkMovie(ShowId) == 1 || tvShowRealmHelper.checkTvShow(ShowId) == 1) {
            CheckFav = true;
            favoriteButton.setFavorite(true);
        }
    }

    private final Observer<ArrayList<String>> getDetailMovie = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> detailMovie) {
            if (detailMovie != null) {
                Glide.with(getApplicationContext())
                        .load("https://image.tmdb.org/t/p/w400" + detailMovie.get(0))
                        .apply(new RequestOptions())
                        .into(imgPoster);
                Glide.with(getApplicationContext())
                        .load("https://image.tmdb.org/t/p/w342" + detailMovie.get(1))
                        .apply(new RequestOptions())
                        .into(imgPosterMini);
                ShowPosterBack = detailMovie.get(0);
                ShowPoster = detailMovie.get(1);
                tvTitle.setText(detailMovie.get(2));
                tvGenre.setText(detailMovie.get(3));
                tvRuntime.setText(detailMovie.get(4));
                tvRelease.setText(detailMovie.get(5));
                tvLanguage.setText(detailMovie.get(6));
                tvOverview.setText(detailMovie.get(7));
                tvRating.setText(detailMovie.get(8));
                float ratingValue = Float.parseFloat(detailMovie.get(8)) / 2;
                RatingBar.setRating(ratingValue);

                movieModel = new MovieModel();
                movieModel.setMovieId(ShowId);
                movieModel.setMoviePoster(ShowPoster);
                movieModel.setMovieTitle(tvTitle.getText().toString());
                movieModel.setMovieGenre(tvGenre.getText().toString());
                movieModel.setMovieRuntime(tvRuntime.getText().toString());
                movieModel.setMovieRelease(tvRelease.getText().toString());
                movieModel.setMovieLanguage(tvLanguage.getText().toString());
                movieModel.setMovieOverview(tvOverview.getText().toString());
                movieModel.setMovieRating(tvRating.getText().toString());
                movieModel.setMoviePosterMini(ShowPosterBack);

                viewContentDetailMovie.setVisibility(View.VISIBLE);
                favoriteButton.setVisibility(View.VISIBLE);
                loadingBody.hide();
                loadingHeader.hide();
            }
        }
    };

    private final Observer<ArrayList<String>> getDetailTv = new Observer<ArrayList<String>>() {
        @Override
        public void onChanged(ArrayList<String> detailTv) {
            if (detailTv != null) {
                Glide.with(getApplicationContext())
                        .load("https://image.tmdb.org/t/p/w400" + detailTv.get(0))
                        .apply(new RequestOptions())
                        .into(imgPoster);
                Glide.with(getApplicationContext())
                        .load("https://image.tmdb.org/t/p/w342" + detailTv.get(1))
                        .apply(new RequestOptions())
                        .into(imgPosterMini);
                ShowPosterBack = detailTv.get(0);
                ShowPoster = detailTv.get(1);
                tvTitle.setText(detailTv.get(2));
                tvGenre.setText(detailTv.get(3));
                tvRuntime.setText(detailTv.get(4));
                tvRelease.setText(detailTv.get(5));
                tvLanguage.setText(detailTv.get(6));
                tvOverview.setText(detailTv.get(7));
                tvRating.setText(detailTv.get(8));
                float ratingValue = Float.parseFloat(detailTv.get(8)) / 2;
                RatingBar.setRating(ratingValue);

                tvShowModel = new TvShowModel();
                tvShowModel.setTvId(ShowId);
                tvShowModel.setTvPoster(ShowPoster);
                tvShowModel.setTvTitle(tvTitle.getText().toString());
                tvShowModel.setTvGenre(tvGenre.getText().toString());
                tvShowModel.setTvNetwork(tvRuntime.getText().toString());
                tvShowModel.setTvAiring(tvRelease.getText().toString());
                tvShowModel.setTvLanguage(tvLanguage.getText().toString());
                tvShowModel.setTvOverview(tvOverview.getText().toString());
                tvShowModel.setTvRating(tvRating.getText().toString());
                tvShowModel.setTvPosterMini(ShowPosterBack);

                viewContentDetailMovie.setVisibility(View.VISIBLE);
                favoriteButton.setVisibility(View.VISIBLE);
                loadingBody.hide();
                loadingHeader.hide();
            }
        }
    };
}

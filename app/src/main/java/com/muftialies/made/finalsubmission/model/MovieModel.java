package com.muftialies.made.finalsubmission.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.muftialies.made.finalsubmission.provider.DatabaseContract;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static com.muftialies.made.finalsubmission.provider.DatabaseContract.getColumnString;

public class MovieModel extends RealmObject implements Parcelable {
    @PrimaryKey
    private String MovieId;
    private String MovieTitle, MovieRating, MovieGenre, MovieRuntime, MovieRelease, MovieLanguage, MoviePoster, MovieOverview, MoviePosterMini;

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        MovieTitle = movieTitle;
    }

    public String getMovieRating() {
        return MovieRating;
    }

    public void setMovieRating(String movieRating) {
        MovieRating = movieRating;
    }

    public String getMovieGenre() {
        return MovieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        MovieGenre = movieGenre;
    }

    public String getMovieRuntime() {
        return MovieRuntime;
    }

    public void setMovieRuntime(String movieRuntime) {
        MovieRuntime = movieRuntime;
    }

    public String getMovieRelease() {
        return MovieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        MovieRelease = movieRelease;
    }

    public String getMovieLanguage() {
        return MovieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        MovieLanguage = movieLanguage;
    }

    public String getMoviePoster() {
        return MoviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        MoviePoster = moviePoster;
    }

    public String getMovieOverview() {
        return MovieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        MovieOverview = movieOverview;
    }

    public String getMoviePosterMini() {
        return MoviePosterMini;
    }

    public void setMoviePosterMini(String moviePosterMini) {
        MoviePosterMini = moviePosterMini;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MovieId);
        dest.writeString(this.MovieTitle);
        dest.writeString(this.MovieRating);
        dest.writeString(this.MovieGenre);
        dest.writeString(this.MovieRuntime);
        dest.writeString(this.MovieRelease);
        dest.writeString(this.MovieLanguage);
        dest.writeString(this.MoviePoster);
        dest.writeString(this.MovieOverview);
        dest.writeString(this.MoviePosterMini);
    }

    public MovieModel() {
    }

    public MovieModel(String movieId, String movieTitle, String movieRating, String movieRelease, String moviePoster, String movieOverview) {
        MovieId = movieId;
        MovieTitle = movieTitle;
        MovieRating = movieRating;
        MovieRelease = movieRelease;
        MoviePoster = moviePoster;
        MovieOverview = movieOverview;
    }

    public MovieModel(Cursor cursor) {
        MovieId = getColumnString(cursor, DatabaseContract.ShowColumns.ID);
        MovieTitle = getColumnString(cursor, DatabaseContract.ShowColumns.TITLE);
        MovieRating = getColumnString(cursor, DatabaseContract.ShowColumns.RATING);
        MovieRelease = getColumnString(cursor, DatabaseContract.ShowColumns.DATE);
        MoviePoster = getColumnString(cursor, DatabaseContract.ShowColumns.IMAGE_POSTER);
        MovieOverview = getColumnString(cursor, DatabaseContract.ShowColumns.OVERVIEW);
    }

    protected MovieModel(Parcel in) {
        this.MovieId = in.readString();
        this.MovieTitle = in.readString();
        this.MovieRating = in.readString();
        this.MovieGenre = in.readString();
        this.MovieRuntime = in.readString();
        this.MovieRelease = in.readString();
        this.MovieLanguage = in.readString();
        this.MoviePoster = in.readString();
        this.MovieOverview = in.readString();
        this.MoviePosterMini = in.readString();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}

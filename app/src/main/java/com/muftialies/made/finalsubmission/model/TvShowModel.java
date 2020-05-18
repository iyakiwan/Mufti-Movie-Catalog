package com.muftialies.made.finalsubmission.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TvShowModel extends RealmObject {
    @PrimaryKey
    private String TvId;
    private String TvTitle, TvRating, TvGenre, TvNetwork, TvAiring, TvLanguage, TvPoster, TvOverview, TvPosterMini;

    public String getTvId() {
        return TvId;
    }

    public void setTvId(String tvId) {
        TvId = tvId;
    }

    public String getTvTitle() {
        return TvTitle;
    }

    public void setTvTitle(String tvTitle) {
        TvTitle = tvTitle;
    }

    public String getTvRating() {
        return TvRating;
    }

    public void setTvRating(String tvRating) {
        TvRating = tvRating;
    }

    public String getTvGenre() {
        return TvGenre;
    }

    public void setTvGenre(String tvGenre) {
        TvGenre = tvGenre;
    }

    public String getTvNetwork() {
        return TvNetwork;
    }

    public void setTvNetwork(String tvNetwork) {
        TvNetwork = tvNetwork;
    }

    public String getTvAiring() {
        return TvAiring;
    }

    public void setTvAiring(String tvAiring) {
        TvAiring = tvAiring;
    }

    public String getTvLanguage() {
        return TvLanguage;
    }

    public void setTvLanguage(String tvLanguage) {
        TvLanguage = tvLanguage;
    }

    public String getTvPoster() {
        return TvPoster;
    }

    public void setTvPoster(String tvPoster) {
        TvPoster = tvPoster;
    }

    public String getTvOverview() {
        return TvOverview;
    }

    public void setTvOverview(String tvOverview) {
        TvOverview = tvOverview;
    }

    public String getTvPosterMini() {
        return TvPosterMini;
    }

    public void setTvPosterMini(String tvPosterMini) {
        TvPosterMini = tvPosterMini;
    }
}

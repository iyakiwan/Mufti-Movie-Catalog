package com.muftialies.made.finalsubmission.model;

import android.annotation.SuppressLint;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class ShowItems {
    private String vote_average, title, poster_path, overview, release_date, id;

    public String  getId() {
        return id;
    }

    private void setId(String  id) {
        this.id = id;
    }

    public String getVote_average() {
        return vote_average;
    }

    private void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    private void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    private void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    private void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public ShowItems(JSONObject object, String show) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fromUser = new SimpleDateFormat("dd MMMM yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            setId(object.getString("id"));
            setVote_average(object.getString("vote_average"));
            setTitle((show.equals("movie")) ? object.getString("title") : object.getString("name"));
            setPoster_path(object.getString("poster_path"));
            setOverview(object.getString("overview"));
            setRelease_date(fromUser.format(myFormat.parse((show.equals("movie")) ? object.getString("release_date") : object.getString("first_air_date"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

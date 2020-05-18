package com.muftialies.made.finalsubmission.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.muftialies.made.finalsubmission.BuildConfig;
import com.muftialies.made.finalsubmission.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieDetailViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private final MutableLiveData<ArrayList<String>> itemDetailMovie = new MutableLiveData<>();

    public LiveData<ArrayList<String>> getDetailMovie() {
        return itemDetailMovie;
    }

    public void setDetailMovie(final String id) {
        if (itemDetailMovie.getValue() == null) {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<String> listDetailMovie = new ArrayList<>();

            String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + API_KEY + "&language=en-US";

            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    try {
                        JSONObject responseObject = new JSONObject(result);
                        listDetailMovie.add(responseObject.getString("backdrop_path"));
                        listDetailMovie.add(responseObject.getString("poster_path"));
                        listDetailMovie.add(responseObject.getString("title"));
                        StringBuilder genre = new StringBuilder();
                        JSONArray listGenre = responseObject.getJSONArray("genres");
                        for (int i = 0; i < listGenre.length(); i++) {
                            if (i == listGenre.length() - 1) {
                                genre.append(listGenre.getJSONObject(i).getString("name"));
                            } else {
                                genre.append(listGenre.getJSONObject(i).getString("name")).append(", ");
                            }
                        }
                        listDetailMovie.add(genre.toString());
                        String runtime = "";

                        if (responseObject.getString("runtime") != null){
                            int time = Integer.parseInt((responseObject.getString("runtime").equals("null") || responseObject.getString("runtime").equals("")) ? "0" : responseObject.getString("runtime"));
                            int jam = time / 60;
                            int minute = time % 60;
                            if (jam == 0 && minute == 0) {
                                runtime = "-";
                            } else if (jam == 0) {
                                runtime = minute + " " + context.getString(R.string.runtime_hour);
                            } else {
                                runtime = jam + " " + context.getString(R.string.runtime_hour) + ", " +
                                        minute + " " + context.getString(R.string.runtime_minute);
                            }
                        }
                        listDetailMovie.add(runtime);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat fromUser = new SimpleDateFormat("dd MMMM yyyy");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

                        listDetailMovie.add(fromUser.format(myFormat.parse(responseObject.getString("release_date"))));
                        listDetailMovie.add(responseObject.getString("original_language"));
                        listDetailMovie.add(responseObject.getString("overview"));
                        listDetailMovie.add(responseObject.getString("vote_average"));

                        itemDetailMovie.postValue(listDetailMovie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure", error.getMessage());
                    onError();
                }
            });
        }
    }

    private void onError() {
        Toast.makeText(context, context.getString(R.string.failure_data), Toast.LENGTH_LONG).show();
    }
}

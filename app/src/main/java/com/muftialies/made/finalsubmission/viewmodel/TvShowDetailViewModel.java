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

public class TvShowDetailViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private final MutableLiveData<ArrayList<String>> itemDetailTvShow = new MutableLiveData<>();

    public LiveData<ArrayList<String>> getDetailTvShow() {
        return itemDetailTvShow;
    }

    public void setDetailTvShow(final String id) {
        if (itemDetailTvShow.getValue() == null) {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<String> listDetailTvShow = new ArrayList<>();

            String url = "https://api.themoviedb.org/3/tv/" + id + "?api_key=" + API_KEY + "&language=en-US";

            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("onSuccess", "Nice bro");
                    String result = new String(responseBody);
                    try {
                        JSONObject responseObject = new JSONObject(result);
                        listDetailTvShow.add(responseObject.getString("backdrop_path"));
                        listDetailTvShow.add(responseObject.getString("poster_path"));
                        listDetailTvShow.add(responseObject.getString("name"));
                        StringBuilder genre = new StringBuilder();
                        JSONArray listGenre = responseObject.getJSONArray("genres");
                        for (int i = 0; i < listGenre.length(); i++) {
                            if (i == listGenre.length() - 1) {
                                genre.append(listGenre.getJSONObject(i).getString("name"));
                            } else {
                                genre.append(listGenre.getJSONObject(i).getString("name")).append(", ");
                            }
                        }
                        listDetailTvShow.add(genre.toString());
                        StringBuilder networks = new StringBuilder();
                        JSONArray listNetworks = responseObject.getJSONArray("networks");
                        for (int i = 0; i < listNetworks.length(); i++) {
                            if (i == listNetworks.length() - 1) {
                                networks.append(listNetworks.getJSONObject(i).getString("name"));
                            } else {
                                networks.append(listNetworks.getJSONObject(i).getString("name")).append(", ");
                            }
                        }
                        listDetailTvShow.add(networks.toString());
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat fromUser = new SimpleDateFormat("dd MMMM yyyy");
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

                        listDetailTvShow.add(fromUser.format(myFormat.parse(responseObject.getString("last_air_date"))));
                        listDetailTvShow.add(responseObject.getString("original_language"));
                        listDetailTvShow.add(responseObject.getString("overview"));
                        listDetailTvShow.add(responseObject.getString("vote_average"));

                        itemDetailTvShow.postValue(listDetailTvShow);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure TvSHow", error.getMessage());
                    onError();
                }
            });
        }
    }

    private void onError() {
        Toast.makeText(context, context.getString(R.string.failure_data), Toast.LENGTH_LONG).show();
    }
}

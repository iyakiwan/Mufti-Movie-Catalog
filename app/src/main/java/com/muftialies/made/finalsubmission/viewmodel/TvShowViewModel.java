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
import com.muftialies.made.finalsubmission.model.ShowItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private final MutableLiveData<ArrayList<ShowItems>> itemTvShows = new MutableLiveData<>();

    public LiveData<ArrayList<ShowItems>> getListTvShows() {
        return itemTvShows;
    }

    public void setListTvShow(boolean cek) {
        if (cek) {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<ShowItems> listItemsTv = new ArrayList<>();
            final String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("results");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject jsonObject = list.getJSONObject(i);
                            ShowItems tvShowItems = new ShowItems(jsonObject, "tv");
                            listItemsTv.add(tvShowItems);
                        }
                        itemTvShows.postValue(listItemsTv);

                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
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

    public void setSearchTvShow(String search) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ShowItems> listItemsTv = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + search;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    if (responseObject.getString("total_results").equals("0")){
                        zero();
                    }
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject = list.getJSONObject(i);
                        ShowItems tvShowItems = new ShowItems(jsonObject, "tv");
                        listItemsTv.add(tvShowItems);
                    }
                    itemTvShows.postValue(listItemsTv);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                onError();
            }
        });
    }

    private void zero() {
        Toast.makeText(context, context.getString(R.string.no_result), Toast.LENGTH_LONG).show();
    }

    private void onError() {
        Toast.makeText(context, context.getString(R.string.failure_data), Toast.LENGTH_LONG).show();
    }
}

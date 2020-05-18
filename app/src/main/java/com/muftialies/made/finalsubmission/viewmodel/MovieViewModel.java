package com.muftialies.made.finalsubmission.viewmodel;

import android.annotation.SuppressLint;
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

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private MutableLiveData<ArrayList<ShowItems>> itemMovies = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ShowItems>> getListMovies() {
        return itemMovies;
    }

    public void setListMovie(boolean cek) {
        if (cek) {
            AsyncHttpClient client = new AsyncHttpClient();
            final ArrayList<ShowItems> listItemsMovie = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String result = new String(responseBody);
                        JSONObject responseObject = new JSONObject(result);
                        JSONArray list = responseObject.getJSONArray("results");

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject jsonObject = list.getJSONObject(i);
                            ShowItems showItems = new ShowItems(jsonObject, "movie");
                            listItemsMovie.add(showItems);
                        }
                        itemMovies.postValue(listItemsMovie);

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

    public void setSearchMovie(String search) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<ShowItems> listItemsSearchMovie = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + search;
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
                        ShowItems showItems = new ShowItems(jsonObject, "movie");
                        listItemsSearchMovie.add(showItems);
                    }
                    itemMovies.postValue(listItemsSearchMovie);

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

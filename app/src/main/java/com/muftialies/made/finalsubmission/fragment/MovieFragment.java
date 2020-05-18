package com.muftialies.made.finalsubmission.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leochuan.ScaleLayoutManager;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.adapter.ShowAdapter;
import com.muftialies.made.finalsubmission.model.ShowItems;
import com.muftialies.made.finalsubmission.viewmodel.MovieViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    private static final String SEARCH_RESULT = "search_result";
    private ShowAdapter adapterMoviePopular;
    private AVLoadingIndicatorView loadingShow;
    private TextView tvPopular;
    private MovieViewModel mainViewModelMoviePopular;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModelMoviePopular = ViewModelProviders.of(requireActivity()).get(MovieViewModel.class);
        mainViewModelMoviePopular.setContext(getContext());
        if (savedInstanceState == null){
            mainViewModelMoviePopular.setListMovie(true);
            mainViewModelMoviePopular.getListMovies().observe(this, getMovie);
        } else  {
            mainViewModelMoviePopular.getListMovies().observe(this, getMovie);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_RESULT, tvPopular.getText().toString());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPopular = view.findViewById(R.id.tv_show_1);
        tvPopular.setText(R.string.movie_popular);

        MaterialSearchBar searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);

        loadingShow = view.findViewById(R.id.loading_show);

        adapterMoviePopular = new ShowAdapter("movie");
        adapterMoviePopular.notifyDataSetChanged();

        ScaleLayoutManager scaleLayoutManager = new ScaleLayoutManager(view.getContext(), 0);
        scaleLayoutManager.setInfinite(true);
        scaleLayoutManager.getCurrentPosition();

        RecyclerView rvFragmentMoviePopular = view.findViewById(R.id.rv_fragment_show);
        rvFragmentMoviePopular.setLayoutManager(scaleLayoutManager);
        rvFragmentMoviePopular.setAdapter(adapterMoviePopular);

        if (savedInstanceState != null){
            String result = savedInstanceState.getString(SEARCH_RESULT);
            tvPopular.setText(result);
        }
    }

    private final Observer<ArrayList<ShowItems>> getMovie = new Observer<ArrayList<ShowItems>>() {
        @Override
        public void onChanged(ArrayList<ShowItems> showItems) {
            if (showItems != null) {
                adapterMoviePopular.setData(showItems);
                loadingShow.hide();
            }
        }
    };

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled){
            loadingShow.show();
            mainViewModelMoviePopular.setListMovie(true);
            mainViewModelMoviePopular.getListMovies().observe(this, getMovie);
            tvPopular.setText(R.string.movie_popular);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        loadingShow.show();
        String query = getString(R.string.result_movie) + text.toString() + "\"";
        mainViewModelMoviePopular.setSearchMovie(text.toString());
        mainViewModelMoviePopular.getListMovies().observe(this, getMovie);
        tvPopular.setText(query);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}

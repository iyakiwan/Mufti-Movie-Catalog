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
import com.muftialies.made.finalsubmission.viewmodel.TvShowViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    private static final String SEARCH_RESULT = "search_result";
    private ShowAdapter adapterTvShow;
    private AVLoadingIndicatorView loadingShow;
    private TextView tvPopular;
    private TvShowViewModel mainTvShowViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainTvShowViewModel = ViewModelProviders.of(requireActivity()).get(TvShowViewModel.class);
        mainTvShowViewModel.setContext(getContext());
        if (savedInstanceState == null){
            mainTvShowViewModel.setListTvShow(true);
            mainTvShowViewModel.getListTvShows().observe(this, getTvShow);
        } else  {
            mainTvShowViewModel.getListTvShows().observe(this, getTvShow);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_RESULT, tvPopular.getText().toString());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPopular = view.findViewById(R.id.tv_show_1);
        tvPopular.setText(R.string.tv_popular);

        MaterialSearchBar searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setHint(getString(R.string.search_tv));

        loadingShow = view.findViewById(R.id.loading_show);

        adapterTvShow = new ShowAdapter("tv");
        adapterTvShow.notifyDataSetChanged();

        ScaleLayoutManager scaleLayoutManager = new ScaleLayoutManager(view.getContext(), 0);
        RecyclerView rvFragmentMovie = view.findViewById(R.id.rv_fragment_show);
        scaleLayoutManager.setInfinite(true);

        rvFragmentMovie.setLayoutManager(scaleLayoutManager);
        rvFragmentMovie.setAdapter(adapterTvShow);
        scaleLayoutManager.getCurrentPosition();

        if (savedInstanceState != null){
            String result = savedInstanceState.getString(SEARCH_RESULT);
            tvPopular.setText(result);
        }
    }

    private final Observer<ArrayList<ShowItems>> getTvShow = new Observer<ArrayList<ShowItems>>() {
        @Override
        public void onChanged(@Nullable ArrayList<ShowItems> showItems) {
            if (showItems != null) {
                adapterTvShow.setData(showItems);
                loadingShow.hide();
            }
        }
    };

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled){
            loadingShow.show();
            mainTvShowViewModel.setListTvShow(true);
            mainTvShowViewModel.getListTvShows().observe(this, getTvShow);
            tvPopular.setText(R.string.tv_popular);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        loadingShow.show();
        String query = getString(R.string.result_tv) + text.toString() + "\"";
        mainTvShowViewModel.setSearchTvShow(text.toString());
        mainTvShowViewModel.getListTvShows().observe(this, getTvShow);
        tvPopular.setText(query);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}

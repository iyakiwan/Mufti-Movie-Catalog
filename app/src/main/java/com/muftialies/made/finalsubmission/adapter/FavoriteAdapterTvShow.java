package com.muftialies.made.finalsubmission.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muftialies.made.finalsubmission.model.TvShowModel;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.activity.FavoriteDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapterTvShow extends RecyclerView.Adapter<FavoriteAdapterTvShow.FavoriteViewHolder> {
    private List<TvShowModel> tvShowModels = new ArrayList<>();
    private static final String url = "https://image.tmdb.org/t/p/w342";

    public void setData(List<TvShowModel> items) {
        tvShowModels.clear();
        tvShowModels = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, @SuppressLint("RecyclerView") final int position) {
        favoriteViewHolder.bind(tvShowModels.get(position));

        favoriteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailFavorite = new Intent(v.getContext(), FavoriteDetailActivity.class);
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_ID, tvShowModels.get(position).getTvId());
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_TITLE, tvShowModels.get(position).getTvTitle());
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_SHOW, "tv");
                v.getContext().startActivity(detailFavorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowModels.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRating, tvName, tvRelease, tvOverview;
        private final ImageView imgPoster;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRelease = itemView.findViewById(R.id.tv_item_release);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            imgPoster = itemView.findViewById(R.id.img_item_show);
        }

        void bind(TvShowModel tvShowModel) {
            tvName.setText(tvShowModel.getTvTitle());
            tvRelease.setText(tvShowModel.getTvAiring());
            tvOverview.setText(tvShowModel.getTvOverview());
            tvRating.setText(tvShowModel.getTvRating());
            Glide.with(itemView.getContext())
                    .load(url + tvShowModel.getTvPoster())
                    .apply(new RequestOptions())
                    .into(imgPoster);

        }
    }
}

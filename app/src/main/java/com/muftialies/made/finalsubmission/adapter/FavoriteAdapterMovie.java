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
import com.muftialies.made.finalsubmission.model.MovieModel;
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.activity.FavoriteDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapterMovie extends RecyclerView.Adapter<FavoriteAdapterMovie.FavoriteViewHolder> {
    private List<MovieModel> movieModels = new ArrayList<>();
    private static final String url = "https://image.tmdb.org/t/p/w342";

    public void setData(List<MovieModel> items) {
        movieModels.clear();
        movieModels = items;
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
        favoriteViewHolder.bind(movieModels.get(position));

        favoriteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailFavorite = new Intent(v.getContext(), FavoriteDetailActivity.class);
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_ID, movieModels.get(position).getMovieId());
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_TITLE, movieModels.get(position).getMovieTitle());
                detailFavorite.putExtra(FavoriteDetailActivity.EXTRA_SHOW, "movie");
                v.getContext().startActivity(detailFavorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
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

        void bind(MovieModel movieModel) {
            tvName.setText(movieModel.getMovieTitle());
            tvRelease.setText(movieModel.getMovieRelease());
            tvOverview.setText(movieModel.getMovieOverview());
            tvRating.setText(movieModel.getMovieRating());
            Glide.with(itemView.getContext())
                    .load(url + movieModel.getMoviePoster())
                    .apply(new RequestOptions())
                    .into(imgPoster);

        }
    }
}

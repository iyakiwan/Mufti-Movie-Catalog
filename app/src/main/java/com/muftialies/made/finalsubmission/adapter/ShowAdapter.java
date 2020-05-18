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
import com.muftialies.made.finalsubmission.R;
import com.muftialies.made.finalsubmission.activity.DetailActivity;
import com.muftialies.made.finalsubmission.model.ShowItems;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {
    private final ArrayList<ShowItems> dataShow = new ArrayList<>();
    private static final String url = "https://image.tmdb.org/t/p/w342";
    private final String show;

    public ShowAdapter(String show) {
        this.show = show;
    }

    public void setData(ArrayList<ShowItems> items) {
        dataShow.clear();
        dataShow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder showViewHolder, @SuppressLint("RecyclerView") final int position) {
        showViewHolder.bind(dataShow.get(position));

        showViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailShow = new Intent(v.getContext(), DetailActivity.class);
                detailShow.putExtra(DetailActivity.EXTRA_ID, dataShow.get(position).getId());
                detailShow.putExtra(DetailActivity.EXTRA_TITLE, dataShow.get(position).getTitle());
                detailShow.putExtra(DetailActivity.EXTRA_SHOW, show);
                v.getContext().startActivity(detailShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataShow.size();
    }

    class ShowViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRating, tvName, tvRelease, tvOverview;
        private final ImageView imgPoster;

        ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRelease = itemView.findViewById(R.id.tv_item_release);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            imgPoster = itemView.findViewById(R.id.img_item_show);
        }

        @SuppressLint("SetTextI18n")
        void bind(ShowItems showItems) {
            tvName.setText(showItems.getTitle());
            tvRelease.setText("("+ showItems.getRelease_date()+")");
            tvOverview.setText(showItems.getOverview());
            tvRating.setText(showItems.getVote_average());
            if (showItems.getPoster_path().equals("null")){
                Glide.with(itemView.getContext())
                        .load(R.mipmap.ic_launcher)
                        .apply(new RequestOptions())
                        .into(imgPoster);
            } else {
                Glide.with(itemView.getContext())
                        .load(url + showItems.getPoster_path())
                        .apply(new RequestOptions())
                        .into(imgPoster);
            }
        }
    }
}

package com.ecsm.android.movie.adaper;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.Url;
import com.ecsm.android.movie.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {
    private List<Movie> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public RecycleAdapter(Context c, List<Movie> movies) {
        mContext = c;
        mData = movies;
        mInflater = LayoutInflater.from(c);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.list_item, parent, false);
        Holder holder = new Holder(v);

        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setData(mData.get(position));
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView poster;

        public Holder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.image_icon);
        }

        public void setData(Movie movie) {

            Picasso.with(mContext).load(Url.Base.IMAGE + movie.getPosterPath() + Url.API_KEY).into(poster);
        }
    }
}

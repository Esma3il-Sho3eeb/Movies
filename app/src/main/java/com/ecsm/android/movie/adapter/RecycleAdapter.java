package com.ecsm.android.movie.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.Url;
import com.ecsm.android.movie.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {
    private List<Movie> mData;
    private MovieListener mListener;
    private ReachEndListener mEndListener;


    public RecycleAdapter(List<Movie> data) {
        mListener = new MovieListener() {
            @Override
            public void onClick(Movie movie) {

            }
        };
        mEndListener = new ReachEndListener() {
            @Override
            public void onReachEnd() {

            }
        };
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            mData = data;
        }
    }

    public RecycleAdapter() {
        this(null);
    }

    public void removeAll() {
      //  int previousPosition = mData.size() - 1;
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(MovieListener listener) {
        if (listener == null)
            mListener = new MovieListener() {
                @Override
                public void onClick(Movie movie) {

                }
            };
        mListener = listener;
    }

    public void setOnReachEndListener(@Nullable ReachEndListener listener) {
        if (listener == null)
            mEndListener = new ReachEndListener() {
                @Override
                public void onReachEnd() {

                }
            };
        mEndListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setup(mData.get(position), mListener);
        if (position == mData.size() - 1)
            mEndListener.onReachEnd();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(Movie movie) {
        mData.add(movie);
        notifyItemInserted(mData.size() - 1);
    }

    public void addAll(Collection<Movie> movies) {
        int previousSize = mData.size();
        mData.addAll(movies);
        notifyItemRangeInserted(previousSize, movies.size() - 1);

    }

    public interface MovieListener {
        void onClick(Movie movie);
    }

    public interface ReachEndListener {
        public void onReachEnd();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView poster;


        public Holder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.image_icon);
        }

        public void setup(final Movie movie, final MovieListener listener) {

            Picasso.with(poster.getContext()).load(Url.Base.IMAGE + movie.getPosterPath() + Url.API_KEY).into(poster);
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(movie);
                }
            });
        }
    }
}

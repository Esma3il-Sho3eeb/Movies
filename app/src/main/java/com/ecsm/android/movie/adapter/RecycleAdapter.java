package com.ecsm.android.movie.adapter;


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
    private MovieListener mListener;

    public RecycleAdapter(Context c, List<Movie> movies, MovieListener listener) {
        mContext = c;
        mListener = listener;
        mData = movies;
        mInflater = LayoutInflater.from(c);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = mInflater.inflate(R.layout.list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        holder.setup(mData.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface MovieListener {
        void onClick(Movie movie);
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView poster;


        public Holder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.image_icon);
        }

        public void setup(final Movie movie, final MovieListener listener) {

            Picasso.with(mContext).load(Url.Base.IMAGE + movie.getPosterPath() + Url.API_KEY).into(poster);
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(movie);
                }
            });
        }


    }

    public void add(Movie movie){
        mData.add(movie);

    }

}

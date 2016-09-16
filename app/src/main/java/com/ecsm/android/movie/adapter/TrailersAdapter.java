package com.ecsm.android.movie.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.Url;
import com.ecsm.android.movie.data.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.VideoHolder> {
    private List<Video> mData;
    private VideoListener mVideoListener;


    public TrailersAdapter(List<Video> videos) {

        mVideoListener = new VideoListener() {
            @Override
            public void onClick(Video video) {

            }
        };

        if (videos == null) {
            mData = new ArrayList<>();
        } else {
            mData = videos;
        }
    }

    public TrailersAdapter() {
        this(null);
    }

    public void
    setOnClickListener(VideoListener listener) {
        if (listener == null)
            mVideoListener = new VideoListener() {
                @Override
                public void onClick(Video video) {

                }
            };
        else
            mVideoListener = listener;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new VideoHolder(v);
    }

    public void add(Video video) {
        mData.add(video);
        notifyItemInserted(mData.size() - 1);
    }

    public void addAll(Collection<Video> videos) {
        int previousSize = mData.size();
        mData.addAll(videos);
        notifyItemRangeInserted(previousSize, videos.size() - 1);

    }

    public void removeAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        holder.setup(mData.get(position), mVideoListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static interface VideoListener {
        public void onClick(Video video);
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView mVideoName;

        public VideoHolder(View itemView) {
            super(itemView);
            mVideoName = (TextView) itemView.findViewById(R.id.videoName);
            poster = (ImageView) itemView.findViewById(R.id.videoThumbnail);
        }

        public void setup(final Video video, final VideoListener listener) {
            mVideoName.setText(video.getName());
            Picasso.with(poster.getContext()).load(Url.YoutubeThumbnail.full(video.getKey())).into(poster);
            poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(video);
                }
            });
        }
    }
}

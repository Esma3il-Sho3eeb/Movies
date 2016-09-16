package com.ecsm.android.movie.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.data.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.Holder> {
    private List<Review> mData;
    private ReviewListener mListener;
    private ReachEndListener mEndListener;


    public ReviewsAdapter(List<Review> data) {
        mListener = new ReviewListener() {
            @Override
            public void onClick(Review review) {

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

    public ReviewsAdapter() {
        this(null);
    }

    public void removeAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(@Nullable ReviewListener listener) {
        if (listener == null)
            mListener = new ReviewListener() {
                @Override
                public void onClick(Review movie) {

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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
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

    public void add(Review review) {
        mData.add(review);
        notifyItemInserted(mData.size() - 1);
    }

    public void addAll(Collection<Review> reviews) {
        int previousSize = mData.size();
        mData.addAll(reviews);
        notifyItemRangeInserted(previousSize, reviews.size() - 1);

    }

    public interface ReviewListener {
        void onClick(Review review);
    }

    public interface ReachEndListener {
        public void onReachEnd();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView authorName, reviewContent;


        public Holder(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.reviewAuthorName);
            reviewContent = (TextView) itemView.findViewById(R.id.reviewContent);

        }

        public void setup(final Review review, final ReviewListener listener) {
            authorName.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
            reviewContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(review);
                }
            });
            authorName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(review);
                }
            });
        }
    }
}

package com.ecsm.android.movie.ui;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ecsm.android.OverflowDialog;
import com.ecsm.android.VolleyQueue;
import com.ecsm.android.movie.R;
import com.ecsm.android.movie.Url;
import com.ecsm.android.movie.adapter.ReviewsAdapter;
import com.ecsm.android.movie.adapter.TrailersAdapter;
import com.ecsm.android.movie.data.Movie;
import com.ecsm.android.movie.data.Review;
import com.ecsm.android.movie.data.ReviewsContainer;
import com.ecsm.android.movie.data.Video;
import com.ecsm.android.movie.data.VideoContainer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class FragmentDetails extends Fragment {
    public static final String TAG = "details";

    private RecyclerView reviewersView, trailersView;
    private ImageView posterImage;
    private TrailersAdapter mTrailersAdapter;
    private ReviewsAdapter mReviewsAdapter;
    private TextView movieTitle, releaseDate, duration, rating, overview;
    private Button actionFavorite;
    private Movie mMovie;
    private RegisterChanges mRegisterChanges;
    private int currentPage = 1, pagesNumber = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRegisterChanges = (RegisterChanges) getActivity();
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        posterImage = (ImageView) v.findViewById(R.id.detailsPosterImage);
        movieTitle = (TextView) v.findViewById(R.id.detailsMovieTitle);
        releaseDate = (TextView) v.findViewById(R.id.detailsYearView);
        duration = (TextView) v.findViewById(R.id.detailsDurationView);
        rating = (TextView) v.findViewById(R.id.detailsRateView);
        overview = (TextView) v.findViewById(R.id.detailsOverview);
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OverflowDialog dialog = new OverflowDialog();
                Bundle extra = new Bundle();

                extra.putString(OverflowDialog.KEY_OVERVIEW, mMovie.getOverview());
                dialog.setArguments(extra);

                dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), "dialog");


            }
        });

        actionFavorite = (Button) v.findViewById(R.id.action_favorite);


        reviewersView = (RecyclerView) v.findViewById(R.id.reviewsRecyclerView);
        reviewersView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mReviewsAdapter = new ReviewsAdapter();
        mReviewsAdapter.setOnItemClickListener(new ReviewsAdapter.ReviewListener() {
            @Override
            public void onClick(Review review) {

            }
        });
        mReviewsAdapter.setOnReachEndListener(new ReviewsAdapter.ReachEndListener() {
            @Override
            public void onReachEnd() {
                getMovieReviews(false);
            }
        });
        reviewersView.setAdapter(mReviewsAdapter);

        trailersView = (RecyclerView) v.findViewById(R.id.trailersRecyclerView);
        trailersView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mTrailersAdapter = new TrailersAdapter();
        mTrailersAdapter.setOnClickListener(new TrailersAdapter.VideoListener() {
            @Override
            public void onClick(Video video) {
                Intent videoClient = new Intent(Intent.ACTION_VIEW);
                videoClient.setData(Uri.parse("http://m.youtube.com/watch?v=" + video.getKey()));
                startActivity(videoClient);
            }
        });
        trailersView.setAdapter(mTrailersAdapter);

        if (getArguments() != null)
            mMovie = (Movie) getArguments().getSerializable(Movie.KEY_EXTRA);
        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable(Movie.KEY_EXTRA) != null)
                mMovie = (Movie) savedInstanceState.getSerializable(Movie.KEY_EXTRA);
        }
        if (mMovie != null) onReceive(mMovie);
        actionFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable image;
                mRegisterChanges.onMovieStatusChange();
                if (mMovie.getIsFavorite() > 0) {
                    mMovie.setIsFavorite(0);
                    image = ContextCompat.getDrawable(
                            getActivity(), android.R.drawable.star_big_off);
                    actionFavorite.setText(R.string.add_to_favorite);

                } else {
                    mMovie.setIsFavorite(1);
                    image = ContextCompat.getDrawable(
                            getActivity(), android.R.drawable.star_big_on);
                    actionFavorite.setText(R.string.remove_from_favorite);

                }
                int h = image.getIntrinsicHeight();
                int w = image.getIntrinsicWidth();
                image.setBounds(0, 0, w, h);
                actionFavorite.setCompoundDrawables(image, null, null, null);

                mMovie.save();
            }
        });
        return v;
    }

    public void onReceive(Movie movie) {
        mMovie = movie;
        mRegisterChanges.onMovieChange(mMovie);

        //
        Movie sd = new Select().from(Movie.class).where("movieId =" + movie.getMovieId()).executeSingle();
        if (sd == null)
            mMovie.setIsFavorite(0);
        else
            mMovie.setIsFavorite(sd.getIsFavorite());
        //
        Picasso.with(getActivity())
                .load(Url.Base.IMAGE + movie.getPosterPath() + Url.API_KEY)
                .into(posterImage);

        movieTitle.setText(movie.getTitle());

        releaseDate.setText(movie.getReleaseDate().substring(0, 4));

        String string = movie.getVoteAverage() + "/" + movie.getVoteCount();
        rating.setText(string);

        overview.setText(movie.getOverview());
        Drawable image;
        if (mMovie.getIsFavorite() > 0) {

            image = ContextCompat.getDrawable(
                    getActivity(), android.R.drawable.star_big_on);
            actionFavorite.setText(R.string.remove_from_favorite);


        } else {
            actionFavorite.setText(R.string.add_to_favorite);
            image = ContextCompat.getDrawable(
                    getActivity(), android.R.drawable.star_off);
        }
        int h = image.getIntrinsicHeight();
        int w = image.getIntrinsicWidth();
        image.setBounds(0, 0, w, h);
        actionFavorite.setCompoundDrawables(image, null, null, null);

        getMovieFullDetails();
        getMovieTrailers();
        getMovieReviews(true);
        //

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getActivity().getSupportFragmentManager().putFragment(outState, TAG, this);
    }

    private void getMovieTrailers() {
        StringRequest request;

        request = new StringRequest(mMovie.getMovieTrailsUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson g = new Gson();
                VideoContainer videoContainer = g.fromJson(response, VideoContainer.class);
                mTrailersAdapter.removeAll();
                mTrailersAdapter.addAll(videoContainer.getVideos());

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private void getMovieReviews(boolean isNew) {
        if (isNew) {
            StringRequest request;

            request = new StringRequest(mMovie.getMovieReviewsUrl(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    ReviewsContainer reviewsContainer = g.fromJson(response, ReviewsContainer.class);
                    currentPage = reviewsContainer.getPage();
                    pagesNumber = reviewsContainer.getTotalPages();

                    mReviewsAdapter.removeAll();
                    mReviewsAdapter.addAll(reviewsContainer.getReviews());

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            VolleyQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

        } else {
            if ((currentPage + 1) > pagesNumber)
                return;

            StringRequest request;
            mReviewsAdapter.removeAll();
            request = new StringRequest(mMovie.getMovieReviewsUrl(currentPage + 1), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    ReviewsContainer reviewsContainer = g.fromJson(response, ReviewsContainer.class);
                    currentPage = reviewsContainer.getPage();
                    pagesNumber = reviewsContainer.getTotalPages();
                    mReviewsAdapter.addAll(reviewsContainer.getReviews());
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            VolleyQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

        }
    }

    private void getMovieFullDetails() {
        StringRequest request;

        request = new StringRequest(mMovie.getMovieDetailsUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson g = new Gson();
                mMovie = g.fromJson(response, Movie.class);
                String string198 = mMovie.getRuntime().toString().concat("min");
                duration.setText(string198);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }

    interface RegisterChanges {
        void onMovieChange(Movie movie);

        void onMovieStatusChange();
    }
}

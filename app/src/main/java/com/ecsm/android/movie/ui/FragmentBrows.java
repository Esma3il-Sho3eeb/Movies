package com.ecsm.android.movie.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ecsm.android.VolleyQueue;
import com.ecsm.android.movie.R;
import com.ecsm.android.movie.Url;
import com.ecsm.android.movie.adapter.RecycleAdapter;
import com.ecsm.android.movie.data.Data;
import com.ecsm.android.movie.data.Movie;
import com.google.gson.Gson;

import java.util.List;

public class FragmentBrows extends Fragment {
    private RecycleAdapter adapter;
    private String urlRequest = Url.Full.Popular;
    private RecyclerView recyclerView;
    private CallBack mCallBack;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brows, container, false);
        setHasOptionsMenu(true);

        //start coding
        mCallBack = (CallBack) getActivity();

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        setupRequest();

        //end coding
        return v;
    }

    private void setupRequest() {
        StringRequest request = new StringRequest(urlRequest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson g = new Gson();
                final Data d = g.fromJson(response, Data.class);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("ismail ", "dddd");
                        ActiveAndroid.beginTransaction();
                        try {
                            for (Movie movie : d.getMovies()) {
                                movie.save();
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                        List<Movie> x = new Select().from(Movie.class).execute();
                        for (Movie n : x) {
                            Log.v("ismail ", n.getMovieId() + "");
                        }
                    }

                }).start();
                adapter = new RecycleAdapter(getActivity(), d.getMovies(), new RecycleAdapter.MovieListener() {
                    @Override
                    public void onClick(Movie movie) {
                        mCallBack.onCall(movie);
                    }
                });
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }

    public interface CallBack {
         void onCall(Movie movie);
    }

}

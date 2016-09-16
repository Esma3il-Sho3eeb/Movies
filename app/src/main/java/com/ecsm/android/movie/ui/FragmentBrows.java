package com.ecsm.android.movie.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentBrows extends Fragment {
    public static final String TAG = "brows";
    public static final int TYPE_BROWS = 1;
    public static final int TYPE_FAVORITE = 2;

    private RecycleAdapter adapter;
    private String urlRequest;
    private CallBack mCallBack;
    private boolean recast = true;
    private int currentPage;
    private boolean wasPopular = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brows, container, false);
        setHasOptionsMenu(true);

        //start coding
        mCallBack = (CallBack) getActivity();
        urlRequest = PreferenceManager
                .getDefaultSharedPreferences(getActivity())
                .getString(getString(
                        R.string.pref_key_sort_with)
                        , Url.Full.Popular

                );



            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.list_column_number));
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecycleAdapter();
            adapter.setOnItemClickListener(new RecycleAdapter.MovieListener() {
                @Override
                public void onClick(Movie movie) {
                    mCallBack.onCall(movie);
                }
            });
            adapter.setOnReachEndListener(new RecycleAdapter.ReachEndListener() {
                @Override
                public void onReachEnd() {
                    setupRequest(false);

                }
            });
            setupRequest(true);
            recyclerView.setAdapter(adapter);


        //end coding
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSortType();
    }

    private void saveSortType() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_key_sort_with), urlRequest);
        editor.apply();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_fragment_brows, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                urlRequest = Url.Full.Popular;
                if (wasPopular) {
                    return true;
                }

                wasPopular = true;
                setupRequest(true);
                return true;

            case R.id.action_top_rated:
                urlRequest = Url.Full.TOP_RATED;
                if (wasPopular) {
                    wasPopular = false;
                    setupRequest(true);
                    return true;
                }

                return true;
        }
        return false;
    }

    private void setupRequest(boolean newRequest) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "sleep ex", e);
                } finally {
                    recast = true;
                }
            }
        });
//        if (ActivityBrows.isAvailable()) {
        if (recast) {
            recast = false;
        } else {
            return;
        }
        StringRequest request;
        if (newRequest) {
            request = new StringRequest(urlRequest, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    final Data d = g.fromJson(response, Data.class);
                    currentPage = d.getPage();
                    adapter.removeAll();
                    adapter.addAll(d.getMovies());
                    recast = true;

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } else {
            request = new StringRequest(urlRequest + "&page=" + (currentPage + 1), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    final Data d = g.fromJson(response, Data.class);
                    currentPage = d.getPage();
                    adapter.addAll(d.getMovies());
                    recast = true;

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        VolleyQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);


//        }

    }

    public interface CallBack {
        void onCall(Movie movie);
    }
}

package com.ecsm.android.movie.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ecsm.android.movie.R;
import com.ecsm.android.movie.Url;
import com.ecsm.android.movie.adaper.RecycleAdapter;
import com.ecsm.android.movie.data.Data;
import com.google.gson.Gson;

public class FragmentBrows extends Fragment {
    private RecycleAdapter adapter;
    private String urlRequest = Url.Full.Popular;
    private RecyclerView recyclerView;
private RequestQueue mRequestQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brows, container, false);
        setHasOptionsMenu(true);
        //start coding
mRequestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());

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
                Data d = g.fromJson(response, Data.class);
                adapter = new RecycleAdapter(getActivity(), d.getMovies());
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
//        VolleyQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue().add(request);
        mRequestQueue.add(request);
    }
}

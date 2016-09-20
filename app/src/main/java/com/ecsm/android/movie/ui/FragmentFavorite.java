package com.ecsm.android.movie.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.ecsm.android.movie.R;
import com.ecsm.android.movie.adapter.RecycleAdapter;
import com.ecsm.android.movie.data.Movie;

import java.util.List;

public class FragmentFavorite extends Fragment {
    public static final String TAG = "favorite";
    public static final int TYPE_BROWS = 1;
    public static final int TYPE_FAVORITE = 2;

    private RecycleAdapter adapter;
    private FragmentBrows.CallBack mCallBack;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_brows, container, false);

        ((ActivityBrows)getActivity()).favoriteOn=true;
        //start coding
        mCallBack = (FragmentBrows.CallBack) getActivity();


        if (savedInstanceState == null) {


            ((ActivityBrows)getActivity()).setActivityTitle(getString(R.string.fragment_favorite_title));


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

            recyclerView.setAdapter(adapter);
            getData();
        }
        //end coding
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        ((ActivityBrows)getActivity()).favoriteOn=false;
    }

    public void getData() {
        adapter.removeAll();
        List<Movie> movies = new Select().from(Movie.class).where("markedFavorite > 0").execute();
        adapter.addAll(movies);
    }


}

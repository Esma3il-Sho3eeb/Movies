package com.ecsm.android.movie.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.data.Movie;

public class FragmentDetails extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_details,container,false);
        return v;
    }
    public void onReceive(Movie movie){
        Toast.makeText(getActivity(), "fuck you", Toast.LENGTH_SHORT).show();

    }
}

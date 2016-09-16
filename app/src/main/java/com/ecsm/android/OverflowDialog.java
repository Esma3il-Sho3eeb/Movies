package com.ecsm.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecsm.android.movie.R;


public class OverflowDialog extends DialogFragment {
    public static final String KEY_OVERVIEW = "overview key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        getDialog().setTitle(getString(R.string.overview));
        TextView overview = (TextView) v.findViewById(R.id.detailsOverview);
        overview.setText(getArguments().getString(KEY_OVERVIEW));
        getDialog().setCanceledOnTouchOutside(true);

        return v;

    }

}

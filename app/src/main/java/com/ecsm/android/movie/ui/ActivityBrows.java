package com.ecsm.android.movie.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.data.Movie;

public class ActivityBrows extends AppCompatActivity implements FragmentBrows.CallBack {
    public static boolean available = false, prefWifi = true;
    protected static boolean refreshStatus = false;
    private boolean is_status_text_in_layout = false;
    private NetworkReceiver mNetworkReceiver;
    private TextView mStatusText;
    private FragmentDetails fragmentDetails;
    private FragmentDetails mFragmentDetails;
    private FragmentBrows mFragmentBrows;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        prefWifi = sh.getBoolean(getString(R.string.pref_key_over_wifi), true);


        if (refreshStatus)
            checkConnection();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetworkReceiver != null) {
            this.unregisterReceiver(mNetworkReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brows);
        setUp();
        mFragmentBrows = new FragmentBrows();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_fragment_brows, mFragmentBrows).commit();

        if (findViewById(R.id.container_fragment_details) != null) {
            mFragmentDetails = new FragmentDetails();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_fragment_details, mFragmentDetails).commit();

        }


    }

    private void setUp() {
        mStatusText = new TextView(this);
        mStatusText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mStatusText.setTextSize(20);
        mStatusText.setBackgroundColor(Color.RED);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkReceiver = new NetworkReceiver();
        this.registerReceiver(mNetworkReceiver, filter);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void checkConnection() {
        boolean wifi, mobileData;
        ConnectivityManager cn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            wifi = info.getType() == ConnectivityManager.TYPE_WIFI;
            mobileData = info.getType() == ConnectivityManager.TYPE_MOBILE;
            if (prefWifi) {
                available = wifi;
            } else {
                available = wifi || mobileData;
            }
        } else {
            available = false;
        }
        refreshStatusTextView(available);
    }

    private void refreshStatusTextView(boolean status) {


        if (status) {

            mStatusText.setBackgroundColor(Color.GREEN);
            mStatusText.setText(R.string.internet_available);

            Thread autoHideTextView = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Log.e("", e.toString());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayout linearLayout =
                                    (LinearLayout) findViewById(R.id.brows_activity_parent_layout);
                            linearLayout.removeView(mStatusText);
                        }
                    });
                }
            });
            if (is_status_text_in_layout) {
                autoHideTextView.start();
                is_status_text_in_layout = false;
            }


        } else {
            if (!is_status_text_in_layout) {

                ((LinearLayout) findViewById(R.id.brows_activity_parent_layout)).addView(mStatusText, 1);
                is_status_text_in_layout = true;

            }
            mStatusText.setBackgroundColor(Color.RED);
            mStatusText.setText(R.string.internet_not_available);
        }
    }

    @Override
    public void onCall(Movie movie) {
        if(mFragmentDetails!=null){
            mFragmentDetails.onReceive(movie);
        }else{
            FragmentDetails fragmentDetails=new FragmentDetails();
            Bundle bundle=new Bundle();
            bundle.putSerializable(Movie.KEY_EXTRA,movie);
            fragmentDetails.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment_brows,fragmentDetails).addToBackStack(null).commit();

        }
    }


    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            boolean wifi, mobileData;
            ConnectivityManager cn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = cn.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                wifi = info.getType() == ConnectivityManager.TYPE_WIFI;
                mobileData = info.getType() == ConnectivityManager.TYPE_MOBILE;
                if (prefWifi) {
                    available = wifi;
                } else {
                    available = wifi || mobileData;
                }
            } else {
                available = false;
            }

            refreshStatusTextView(available);

        }
    }

}



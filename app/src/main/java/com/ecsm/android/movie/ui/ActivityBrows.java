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
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecsm.android.movie.R;

public class ActivityBrows extends AppCompatActivity {
    protected static boolean refreshStatus = false;
    public static boolean available = false, prefWifi = true;
    private NetworkReceiver mNetworkReceiver;
    private TextView mStatusText;


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        prefWifi = sh.getBoolean(getString(R.string.pref_key_over_wifi), true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    if(mNetworkReceiver!=null){
        this.unregisterReceiver(mNetworkReceiver);
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_brows);
        //
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkReceiver = new NetworkReceiver();
        this.registerReceiver(mNetworkReceiver, filter);
        //

        mStatusText = new TextView(this);
        mStatusText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mStatusText.setTextSize(20);
        mStatusText.setBackgroundColor(Color.RED);


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
        refreshStatus(available);
    }

    private void refreshStatus(boolean status) {
        if (!(((LinearLayout) findViewById(R.id.brows_activity_parent_layout)).getChildAt(1) instanceof TextView)) {

            ((LinearLayout) findViewById(R.id.brows_activity_parent_layout)).addView(mStatusText, 1);

        }

        if (status) {

            mStatusText.setBackgroundColor(Color.GREEN);
            mStatusText.setText("Internet is available");
            new Thread(new Runnable() {
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
            }).start();

        } else {
            mStatusText.setBackgroundColor(Color.RED);
            mStatusText.setText("Internet is not available");
        }
    }



    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
boolean availableStatus=available;
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

            refreshStatus(available);

        }
    }

}



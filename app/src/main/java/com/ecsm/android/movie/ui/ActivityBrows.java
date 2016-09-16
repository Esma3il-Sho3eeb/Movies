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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecsm.android.movie.R;
import com.ecsm.android.movie.data.Movie;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class ActivityBrows extends AppCompatActivity implements FragmentBrows.CallBack {
    private static boolean available = false, prefWifi = true;
    private static boolean refreshStatus = true;
    private boolean is_status_text_in_layout = false;
    private NetworkReceiver mNetworkReceiver;
    private TextView mStatusText;
    private FragmentDetails mFragmentDetails;
    private Toolbar mToolbar;

    public static boolean isAvailable() {
        return available;
    }

    public static boolean isPrefWifi() {
        return prefWifi;
    }

    public static boolean isRefreshStatus() {
        return refreshStatus;
    }

    public static void setRefreshStatus(boolean refreshStatus) {
        ActivityBrows.refreshStatus = refreshStatus;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brows);
        setUp();
        ///

        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1).withName(R.string.action_brows).withSelectable(false);

        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.action_favorite).withSelectable(false);

        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withIdentifier(4).withName(R.string.action_settings).withSelectable(false);

        PrimaryDrawerItem item4 = new PrimaryDrawerItem()
                .withIdentifier(3).withName(R.string.action_about).withSelectable(false);


        final Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        item1
                        , item2
                        , item3
                        , item4)
                .withCloseOnClick(true)
                .build();

        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                drawer.closeDrawer();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction;

                switch ((int) drawerItem.getIdentifier()) {
                    case 1:
                        //brows

                        FragmentBrows brows = (FragmentBrows) fm.findFragmentByTag(FragmentBrows.TAG);
                        if (brows == null) {
                            brows = new FragmentBrows();
                        }
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container_fragment_brows, brows);
                        transaction.commit();


                        return true;
                    case 2:
                        //favorite
                        FragmentFavorite favorite = (FragmentFavorite) fm.findFragmentByTag(FragmentBrows.TAG);
                        if (favorite == null) {
                            favorite = new FragmentFavorite();
                        }
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.container_fragment_brows, favorite);
                        transaction.commit();


                        return true;

                    case 4:
                        //setting
                        Toast.makeText(ActivityBrows.this, "setting", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityBrows.this, ActivitySettings.class));
                        return true;
                    case 3:
                        //about

                        return true;


                }
                return false;


            }
        });

        ///


        if (savedInstanceState == null) {
            FragmentBrows fragmentBrows = new FragmentBrows();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_fragment_brows, fragmentBrows).commit();

            if (findViewById(R.id.container_fragment_details) != null) {
                mFragmentDetails = new FragmentDetails();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_fragment_details, mFragmentDetails).commit();

            }


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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        prefWifi = sh.getBoolean(getString(R.string.pref_key_over_wifi), true);


        if (refreshStatus)
            checkConnection();
        refreshStatus = false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(R.string.app_name);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetworkReceiver != null) {
            this.unregisterReceiver(mNetworkReceiver);
        }
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

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void setTitle(int resId) {
        mToolbar.setTitle(resId);
    }

    @Override
    public void onCall(Movie movie) {
        if (mFragmentDetails != null) {
            mFragmentDetails.onReceive(movie);
        } else {
            FragmentDetails fragmentDetails = new FragmentDetails();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Movie.KEY_EXTRA, movie);
            fragmentDetails.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_fragment_brows, fragmentDetails).addToBackStack(null).commit();

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



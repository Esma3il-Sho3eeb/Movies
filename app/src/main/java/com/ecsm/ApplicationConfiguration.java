package com.ecsm;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.ecsm.android.movie.data.Favorite;
import com.ecsm.android.movie.data.Genre;
import com.ecsm.android.movie.data.Movie;
import com.ecsm.android.movie.data.MovieGenre;
import com.ecsm.android.movie.data.MovieProductionCompany;
import com.ecsm.android.movie.data.MovieProductionCountry;
import com.ecsm.android.movie.data.MovieSpokenLanguage;
import com.ecsm.android.movie.data.Popular;
import com.ecsm.android.movie.data.ProductionCompany;
import com.ecsm.android.movie.data.ProductionCountry;
import com.ecsm.android.movie.data.Review;
import com.ecsm.android.movie.data.SpokenLanguage;
import com.ecsm.android.movie.data.TopRated;
import com.ecsm.android.movie.data.Video;


public class ApplicationConfiguration extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration dbConfiguration = new Configuration.Builder(this)
                .setDatabaseName("movie.db")
                .addModelClass(Favorite.class)
                .addModelClass(Genre.class)
                .addModelClass(Movie.class)
                .addModelClass(MovieGenre.class)
                .addModelClass(MovieProductionCompany.class)
                .addModelClass(MovieProductionCountry.class)
                .addModelClass(MovieSpokenLanguage.class)
                .addModelClass(Popular.class)
                .addModelClass(ProductionCompany.class)
                .addModelClass(ProductionCountry.class)
                .addModelClass(Review.class)
                .addModelClass(SpokenLanguage.class)
                .addModelClass(TopRated.class)
                .addModelClass(Video.class)
                .create();
        ActiveAndroid.initialize(dbConfiguration);
    }
}

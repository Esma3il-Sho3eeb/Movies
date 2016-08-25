package com.ecsm.android.movie.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "MovieSpokenLanguage")
public class MovieSpokenLanguage extends Model {

    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public SpokenLanguage getSpokenLanguage() {
        return mSpokenLanguage;
    }

    public void setSpokenLanguage(SpokenLanguage spokenLanguage) {
        mSpokenLanguage = spokenLanguage;
    }

    @Column(name = "SpokenLanguage", onDelete = Column.ForeignKeyAction.CASCADE)
    private SpokenLanguage mSpokenLanguage;

    public MovieSpokenLanguage() {
    }

    public List<SpokenLanguage> spokenLanguages() {
        return getMany(SpokenLanguage.class, "MovieSpokenLanguage");
    }

    public List<Movie> movies() {
        return getMany(Movie.class, "MovieSpokenLanguage");
    }
}

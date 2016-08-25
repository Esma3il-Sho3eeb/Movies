package com.ecsm.android.movie.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Popular")
public class Popular extends Model {

    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    public Popular() {
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public List<Movie> movies() {
        return getMany(Movie.class, "Popular");
    }
}

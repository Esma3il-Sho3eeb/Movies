package com.ecsm.android.movie.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "MovieGenre")
public class MovieGenre extends Model {

    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public Genre getGenre() {
        return mGenre;
    }

    public void setGenre(Genre genre) {
        mGenre = genre;
    }

    @Column(name = "genre", onDelete = Column.ForeignKeyAction.CASCADE)
    private Genre mGenre;

    public MovieGenre() {
    }

    public List<Genre> genres() {
        return getMany(Genre.class, "MovieGenre");
    }

    public List<Movie> movies() {
        return getMany(Movie.class, "MovieGenre");
    }
}

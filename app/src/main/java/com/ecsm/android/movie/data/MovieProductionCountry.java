package com.ecsm.android.movie.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "MovieProductionCountry")
public class MovieProductionCountry extends Model {

    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    @Column(name = "MovieProductionCountry", onDelete = Column.ForeignKeyAction.CASCADE)
    private ProductionCountry mProductionCountry;

    public MovieProductionCountry() {
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public ProductionCountry getProductionCountry() {
        return mProductionCountry;
    }

    public void setProductionCountry(ProductionCountry productionCountry) {
        mProductionCountry = productionCountry;
    }

    public List<ProductionCountry> productionCountries() {
        return getMany(ProductionCountry.class, "MovieProductionCountry");
    }

    public List<Movie> movies() {
        return getMany(Movie.class, "MovieProductionCountry");
    }
}

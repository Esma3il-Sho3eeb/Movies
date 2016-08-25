package com.ecsm.android.movie.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "MovieProductionCompany")
public class MovieProductionCompany extends Model {

    @Column(name = "movie", onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    @Column(name = "productionCompany", onDelete = Column.ForeignKeyAction.CASCADE)
    private ProductionCompany mProductionCompany;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public ProductionCompany getProductionCompany() {
        return mProductionCompany;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        mProductionCompany = productionCompany;
    }

    public MovieProductionCompany() {
    }

    public List<ProductionCompany> productionCompanies() {
        return getMany(ProductionCompany.class, "MovieProductionCompany");
    }

    public List<Movie> movies() {
        return getMany(Movie.class, "MovieProductionCompany");
    }
}

package com.ecsm.android.movie.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "genre")
public class Genre extends Model {

    @Column(unique = true, notNull = true, onUniqueConflict = Column.ConflictAction.IGNORE, name = "genreId")
    @SerializedName("id")
    @Expose
    private Integer genreId;

    @Column(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return The id
     */
    public Integer getGenreId() {
        return genreId;
    }

    /**
     * @param id The id
     */
    public void setGenreId(Integer id) {
        genreId = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

}

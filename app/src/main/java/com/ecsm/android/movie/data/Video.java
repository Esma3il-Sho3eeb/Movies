package com.ecsm.android.movie.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "video")
public class Video extends Model {

    @Column(name = "videoId", notNull = true, unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    @SerializedName("id")
    @Expose
    private String videoId;

    @Column(name = "iso_639_1")
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    @Column(name = "key")
    @SerializedName("key")
    @Expose
    private String key;

    @Column(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @Column(name = "site")
    @SerializedName("site")
    @Expose
    private String site;

    @Column(name = "size")
    @SerializedName("size")
    @Expose
    private Integer size;

    @Column(name = "type")
    @SerializedName("type")
    @Expose
    private String type;

    @Column(name = "movie",onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    /**
     * @return The Video id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param id The Video Id
     */
    public void setVideoId(String id) {
        videoId = id;
    }

    /**
     * @return The iso6391
     */
    public String getIso6391() {
        return iso6391;
    }

    /**
     * @param iso6391 The iso_639_1
     */
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    /**
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    public void setKey(String key) {
        this.key = key;
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

    /**
     * @return The site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

}

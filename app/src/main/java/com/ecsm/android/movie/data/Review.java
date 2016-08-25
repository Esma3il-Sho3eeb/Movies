package com.ecsm.android.movie.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "review")
public class Review extends Model {


    @Column(name = "reviewId", unique = true, notNull = true, onUniqueConflict = Column.ConflictAction.IGNORE)
    @SerializedName("id")
    @Expose
    private String reviewId;

    @Column(name = "author")
    @SerializedName("author")
    @Expose
    private String author;

    @Column(name = "content")
    @SerializedName("content")
    @Expose
    private String content;

    @Column(name = "url")
    @SerializedName("url")
    @Expose
    private String url;

    @Column(name = "movie" ,onDelete = Column.ForeignKeyAction.CASCADE)
    private Movie mMovie;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    /**
     * @return The id
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * @param id The id
     */
    public void setReviewId(String id) {
        reviewId = id;
    }

    /**
     * @return The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}

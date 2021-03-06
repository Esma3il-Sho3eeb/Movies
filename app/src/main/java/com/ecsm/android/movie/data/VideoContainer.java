
package com.ecsm.android.movie.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoContainer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Video> videos = new ArrayList<>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setVideos(List<Video> results) {
        this.videos = results;
    }

}

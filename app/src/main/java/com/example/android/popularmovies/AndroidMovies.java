package com.example.android.popularmovies;

import java.io.Serializable;

public class AndroidMovies implements Serializable {
    private String image;
    private String title;
    private String release_date;
    private String vote_average;
    private String plot_synopsis;

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public void setVoteAverage(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setPlotSynopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }
}
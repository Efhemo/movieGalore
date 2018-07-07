package com.example.efhemo.platingapp.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "top_rated", indices = {@Index(value = {"poster_path", "title"}, unique = true)})
public class TopRatedModel implements GenericListItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int ident;
    private double vote_average;

    private String title;
    private double popularity;
    //@Index(value = {"poster_path"},unique = true)
    private String poster_path;

    private int voteCount;
    private String original_language;
    private String backdrop_path;
    private boolean videoBoolen;

    private String overview;
    private String release_date;

    @Ignore //do the insert
    public TopRatedModel(String title, double popularity, String poster_path, double
            vote_average,
                        int ident, int voteCount, boolean videoBoolen,
                        String original_language, String backdrop_path,
                        String overview, String release_date) {
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.vote_average = vote_average;

        this.ident = ident;
        this.voteCount = voteCount;
        this.videoBoolen = videoBoolen;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    //
    public TopRatedModel(int id, double vote_average, String title, double popularity,
                        String poster_path,
                        int ident, int voteCount, boolean videoBoolen,
                        String original_language, String backdrop_path,
                        String overview, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;

        this.ident = ident;
        this.voteCount = voteCount;
        this.videoBoolen = videoBoolen;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getIdent() {
        return ident;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean isVideoBoolen() {
        return videoBoolen;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }



    public int getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getVote_average() {
        return vote_average;
    }
}
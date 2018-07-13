package com.example.efhemo.platingapp.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "nowplaying", indices = {@Index(value = {"poster_path", "title"}, unique = true)})

public class NowPlaying implements GenericListItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int ident;
    private double vote_average;

    private String title;
    //@Index(value = {"poster_path"},unique = true)
    private String poster_path;
    private String backdrop_path;

    private String overview;
    private String release_date;



    @Ignore //do the insert
    public NowPlaying(String title, String poster_path, double
            vote_average,
                         int ident, String backdrop_path,
                         String overview, String release_date) {
        this.title = title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.ident = ident;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }


    public NowPlaying(int id,String title, String poster_path, double
            vote_average, int ident, String backdrop_path,
                         String overview, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.ident = ident;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }



    public int getIdent() {
        return ident;
    }


    public String getBackdrop_path() {
        return backdrop_path;
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

    public String getPoster_path() {
        return poster_path;
    }

    public double getVote_average() {
        return vote_average;
    }

}

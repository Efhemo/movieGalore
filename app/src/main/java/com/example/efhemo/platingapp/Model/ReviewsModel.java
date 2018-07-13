package com.example.efhemo.platingapp.Model;

public class ReviewsModel {

    private String author;
    private String reviews;

    public ReviewsModel(String author, String reviews) {
        this.author = author;
        this.reviews = reviews;
    }

    public String getAuthor() {
        return author;
    }

    public String getReviews() {
        return reviews;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }
}

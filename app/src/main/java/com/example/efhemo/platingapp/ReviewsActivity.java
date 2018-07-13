package com.example.efhemo.platingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.efhemo.platingapp.Database.AppExecutors;
import com.example.efhemo.platingapp.Model.ReviewsModel;
import com.example.efhemo.platingapp.Utilities.ExtractJson;
import com.example.efhemo.platingapp.Utilities.NetworkUtils;
import com.example.efhemo.platingapp.ViewAdapter.ReviewsAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {


    public static final String REVIEWS = "reviews";
    public static final String LOG_TAG = ReviewsActivity.class.getSimpleName();
    private List<ReviewsModel> videoList;
    private ReviewsAdapter reviewsAdapter;
    private String getVideoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Toolbar toolbar = findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String videoId = getIntent().getStringExtra(DetailActivity.INTENT_EXTR);
        if(videoId != null){
            getVideoId = videoId;
        }
        RecyclerView recyclerViewRe = findViewById(R.id.reviews_rc);
        recyclerViewRe.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerViewRe.setHasFixedSize(true);

        reviewsAdapter = new ReviewsAdapter(this);
        recyclerViewRe.setAdapter(reviewsAdapter);

        extractJsonAnotherThread(getVideoId);
    }



    void extractJsonAnotherThread(final String videoId){
        AppExecutors.getsInstance().getDiskIO()
                .execute(new Runnable() {
                    String networkResponse;

                    @Override
                    public void run() {
                        URL url = NetworkUtils.buildUrlVideos(videoId, REVIEWS);
                        Log.d(LOG_TAG, "url query is: "+ url.toString());
                        try {
                            networkResponse =  NetworkUtils.getResponseFromHttpUrl(url);
                            videoList = ExtractJson.jsonResponseExtractedReview(networkResponse);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.w(LOG_TAG, "we got result which is "+ networkResponse);
                                    reviewsAdapter.setTaskReview(videoList);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.efhemo.platingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.efhemo.platingapp.Database.AppExecutors;
import com.example.efhemo.platingapp.Model.ReviewsModel;
import com.example.efhemo.platingapp.Model.VidModel;
import com.example.efhemo.platingapp.Utilities.ExtractJson;
import com.example.efhemo.platingapp.Utilities.NetworkUtils;
import com.example.efhemo.platingapp.ViewAdapter.MovieAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;

    Bundle bundle;
    public static final String SAVEINSTANCE_KEY = "instance_key";
    Intent intent;
    String intentTitle;

    private final static String LOG_TAG = DetailActivity.class.getSimpleName();

    /*private static final String STATIC_MOVIE_URL =
            "https://api.themoviedb.org/3/movie";*/

    /*themoviebd image url: https://image.tmdb.org/t/p/w500/...*/

    private List<ReviewsModel> videoListRev;

    public static final String INTENT_EXTR = "extra";
    public static final String REVIEWS = "reviews";
    public static final String VIDEOS = "videos";
    /*private static final String PARAMS_API = "api_key";
    private static final String API = "95b230b9dc5ca4b835cdb00a1aef6270";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String LANGUAGE = "en-US";*/
    MovieAdapter movieAdapter;
    private List<VidModel> videoList;
    private int identification;
    private TextView textTitle;
    private TextView textPopular;
    private ImageView imageViewheadr;
    private ImageView imageViewPoster;
    private ExpandableTextView textViewDescript;
    private String backdrop;
    private String poster;
    private FloatingActionButton floatingActionButton;
    private String releaseDate;
    private ArrayList<String> integerArrayList;
    private TextView release;
    private TextView genresList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();

        if (savedInstanceState != null && savedInstanceState.containsKey(SAVEINSTANCE_KEY)) {
            bundle = savedInstanceState.getBundle(SAVEINSTANCE_KEY);
        }

        initView();

        RecyclerView recyclerViewVD = (RecyclerView) findViewById(R.id.video_rc_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false);
        recyclerViewVD.setLayoutManager(linearLayoutManager);
        recyclerViewVD.setHasFixedSize(true);
        recyclerViewVD.setSaveEnabled(true);

        movieAdapter = new MovieAdapter(this);
        recyclerViewVD.setAdapter(movieAdapter);

        intent = getIntent();
        bundle = intent.getBundleExtra(PopularFragment.INTENT_EXTRAS);

        if (bundle != null) {

            intentTitle = PopularFragment.TITLE;
            // populate the UI and get some other value
            textTitle.setText(bundle.getString(PopularFragment.TITLE));
            textPopular.setText(String.valueOf(bundle.getDouble(PopularFragment.VOTEAVERAGE)));
            //textViewDescript.setText(bundle.getString(PopularFragment.DESCRIPTION));
            // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
            textViewDescript.setText(bundle.getString(PopularFragment.DESCRIPTION));

            identification = bundle.getInt(PopularFragment.INDENTIFICATION);
            backdrop = bundle.getString("BACKDROP");
            poster = bundle.getString("POSTER");
            releaseDate = bundle.getString("RELEASEDATE");

            release.setText(releaseDate);



            Log.d(LOG_TAG, "the video id is "+String.valueOf(identification));

            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setTitle(bundle.getString(PopularFragment.TITLE));


        }

        //loadNetworkResult(String.valueOf(identification));
        extractJsonAnotherThread(String.valueOf(identification));
        // Set the action bar back button to look like an up button
    }

    private void initView() {

        // sample code snippet to set the text content on the ExpandableTextView
        textViewDescript = (ExpandableTextView) findViewById(R.id.expand_text_view);
        textTitle = findViewById(R.id.movie_title);
        textPopular = findViewById(R.id.popular_fig);
        imageViewheadr = findViewById(R.id.header_view);
        imageViewPoster = findViewById(R.id.poster_details);
        release = findViewById(R.id.release_date);
        //genresList = findViewById(R.id.movie_genres);


        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intentRev = new Intent(DetailActivity.this, ReviewsActivity.class);
                intentRev.putExtra(INTENT_EXTR, String.valueOf(identification));
                startActivity(intentRev);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+backdrop)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewheadr);

        Glide.with(this).load("https://image.tmdb.org/t/p/w200/"+poster)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewPoster);
    }

    //The intent data sent should survive on rotation changes TODO
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(SAVEINSTANCE_KEY, bundle);
        super.onSaveInstanceState(outState);
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

    void extractJsonAnotherThread(final String videoId){
        AppExecutors.getsInstance().getDiskIO()
                .execute(new Runnable() {
                    String networkResponse;

                    @Override
                    public void run() {
                        URL url = NetworkUtils.buildUrlVideos(videoId, VIDEOS );
                        Log.d(LOG_TAG, "url query is: "+ url.toString());
                        try {
                            networkResponse =  NetworkUtils.getResponseFromHttpUrl(url);
                            videoList = ExtractJson.jsonResponseExtractedVideo(networkResponse);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.w(LOG_TAG, "we got result which is "+ networkResponse);
                                    movieAdapter.setTasks(videoList);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

}

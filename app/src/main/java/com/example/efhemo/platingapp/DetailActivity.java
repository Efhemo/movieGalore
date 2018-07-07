package com.example.efhemo.platingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class DetailActivity extends AppCompatActivity {

    Bundle bundle;
    public static final String SAVEINSTANCE_KEY = "instance_key";
    Intent intent;
    String intentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Toolbar toolbar = findViewById(R.id.detail_toolbar);

        ActionBar actionBar = this.getSupportActionBar();



        if (savedInstanceState != null && savedInstanceState.containsKey(SAVEINSTANCE_KEY)) {
            bundle = savedInstanceState.getBundle(SAVEINSTANCE_KEY);
        }

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView textViewDescript = (ExpandableTextView)
                findViewById(R.id.expand_text_view);



        TextView textTitle = findViewById(R.id.movie_title);
        TextView textPopular = findViewById(R.id.popular_fig);
        //TextView textViewDescript = findViewById(R.id.expandable_text);

        intent = getIntent();
        bundle = intent.getBundleExtra(PopularFragment.INTENT_EXTRAS);

        if (bundle != null) {

            intentTitle = PopularFragment.TITLE;
            // populate the UI and get some other value
            textTitle.setText(bundle.getString(PopularFragment.TITLE));
            textPopular.setText(String.valueOf(bundle.getDouble(PopularFragment.POPULARITY)));
            //textViewDescript.setText(bundle.getString(PopularFragment.DESCRIPTION));
            // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
            textViewDescript.setText(bundle.getString(PopularFragment.DESCRIPTION));

        }

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(intentTitle);
        }


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
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

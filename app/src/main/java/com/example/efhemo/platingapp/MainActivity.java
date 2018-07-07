package com.example.efhemo.platingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.efhemo.platingapp.Helper.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity {


    //private static final String API_KEY = "95b230b9dc5ca4b835cdb00a1aef6270";
    FragmentTransaction fragmentTransaction;

    private Toolbar toolbar;
    BottomNavigationView navigation;
    public static Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        // load the store fragment by default


        if(savedInstanceState !=null) {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState,"myFragment" );
            switchFragment(fragment);
        }
        else {
            fragment = new PopularFragment();
            switchFragment(fragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myFragment", fragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.popular_movie:
                    //toolbar.setTitle("Popular");
                    fragment = new PopularFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.top_rated_movie:
                    //toolbar.setTitle("Top Rated");
                    fragment = new TopRatedFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.upcoming:
                    //toolbar.setTitle("Upcoming");
                    fragment = new UpcomingFragment();
                    switchFragment(fragment);
                    return true;
                case R.id.latest:
                    //toolbar.setTitle("Latest");
                    fragment = new LatestFragment();
                    switchFragment(fragment);
                    return true;
            }

            return true;
        }
    };

    private void switchFragment(Fragment fragment){
        fragmentTransaction  = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //If rotation happens, saved the current fragment
        savedInstanceState.putInt("SelectedItemId", navigation.getSelectedItemId());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int selectedItemId = savedInstanceState.getInt("SelectedItemId");
        navigation.setSelectedItemId(selectedItemId);
    }*/

}



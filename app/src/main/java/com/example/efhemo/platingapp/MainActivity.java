package com.example.efhemo.platingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private static int countFrag = 1;


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
            replaceFragment(fragment);
        }
        else {
            fragment = new PopularFragment();
            replaceFragment(fragment);
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
                    replaceFragment(fragment);
                    break;
                case R.id.top_rated_movie:
                    //toolbar.setTitle("Top Rated");
                    fragment = new TopRatedFragment();
                    replaceFragment(fragment);
                    break;
                case R.id.upcoming:
                    //toolbar.setTitle("Upcoming");
                    fragment = new UpcomingFragment();
                    replaceFragment(fragment);
                    break;
                case R.id.latest:
                    //toolbar.setTitle("Latest");
                    fragment = new NowPlayingFragment();
                    replaceFragment(fragment);
                    break;
            }

            return true;
        }


    };

    private void switchFragment(Fragment fragment){
        fragmentTransaction  = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);//replace id with this Fragment
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if(!fragmentPopped){
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void replaceFragment1(Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentManager.BackStackEntry backStackEntry = manager.getBackStackEntryAt(fragment.getId());

        if(backStackEntry != null){
            manager.popBackStackImmediate(backStateName, 0);

        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(backStateName);
        ft.commit();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}



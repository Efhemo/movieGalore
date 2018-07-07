package com.example.efhemo.platingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.efhemo.platingapp.ViewAdapter.MovieAdapter;
import com.example.efhemo.platingapp.Utilities.NetworkUtils;

import java.net.URL;

public class UpcomingFragment extends Fragment {

    private static final String LOG_TAG = UpcomingFragment.class.getSimpleName();

    private static final String CATEGORY = "upcoming";
    MovieAdapter movieAdapter;
    //ArrayList<String> listMe;
    RecyclerView recyclerView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_activity_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_movie);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recyclerView.setHasFixedSize(true);

        //getResult();


        return view;
    }


    void getResult(){
        new Thread(new Runnable() {
            String networkResponse;
            @Override
            public void run() {
                URL url = NetworkUtils.buildUrl(CATEGORY);
                //Log.d(LOG_TAG, "url query is: "+ url.toString());
                /*try {
                    networkResponse =  NetworkUtils.getResponseFromHttpUrl(url);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<PopularEntry> listMe =  ExtractJson.jsonResponseExtracted(networkResponse);
                            //Log.d("Popular ResulJson ", arraylist.toString());
                            movieAdapter = new MovieAdapter(getActivity(), listMe);
                            recyclerView.setAdapter(movieAdapter);
                            movieAdapter.notifyDataSetChanged();
                            //listMe.notifyAll();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();

                }*/


            }


        }).start();
    }

}

package com.example.efhemo.platingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.efhemo.platingapp.Database.AppExecutors;
import com.example.efhemo.platingapp.Model.GenericListItem;
import com.example.efhemo.platingapp.Model.NowPlaying;
import com.example.efhemo.platingapp.Utilities.ExtractJson;
import com.example.efhemo.platingapp.Utilities.NetworkUtils;
import com.example.efhemo.platingapp.ViewAdapter.AlternateAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NowPlayingFragment extends Fragment implements AlternateAdapter.OnOneClickItem {

    private static final String LOG_TAG = NowPlayingFragment.class.getSimpleName();

    private static final String CATEGORY = "now_playing";
    //ArrayList<String> listMe;
    RecyclerView recyclerView;
    AlternateAdapter alternateAdapter;
    LiveData<List<NowPlaying>> nowplayingEntryList;
    private static final int TOTAL_CELLS_ROW = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.movie_activity_recyclerview, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_movie);
        setUpOrientation();

        recyclerView.setHasFixedSize(true);
        recyclerView.setSaveEnabled(true);
        //loadNetworkResult();

        alternateAdapter = new AlternateAdapter(getContext(), this);
        recyclerView.setAdapter(alternateAdapter);

        //best practices: use services for loading network data to database
        extractJsonAnotherThread(); //Network and jsonwork

        retrieveTask(); //LiveData doing worker thread, and observing changes to inform the observer


        return view;
    }


    void setUpOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    int mod = position % 5;
                    if(mod == 4){
                        return 4; // span to 4 columns
                    }else {
                        return 1;
                    }
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);

        }else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), TOTAL_CELLS_ROW);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    int mod = position % 3;
                    if(mod == 2){
                        return 2; // span to two columns
                    }else {
                        return 1;
                    }
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);

        }
    }

    //get data from database ONCE (viewmode) and observe changes (Livedata)
    private void retrieveTask() {

        //VIEWMODEL works in the worker thread and SURVIVE configuration changes (Rotation)
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        nowplayingEntryList = mainViewModel.getTaskNowplaying(); //the Observer is connected to thr data

        //LiveData OBSERVES DATA CHANGES, help us not to be re-quering the daabase
        //(use viewmodel so that new livedata object is not call again due to rotation changes)
        nowplayingEntryList.observe(this, new Observer<List<NowPlaying>>() { //observe the observer
            @Override
            public void onChanged(@Nullable List<NowPlaying> nowplaingEnt) { // Called when the data is changed

                List<GenericListItem> output = new ArrayList<>();
                if (nowplaingEnt != null) {
                    output.addAll(nowplaingEnt);
                }
                alternateAdapter.setTasks(output);
            }
        });
    }


    void extractJsonAnotherThread(){
        AppExecutors.getsInstance().getDiskIO()
                .execute(new Runnable() {
                    String networkResponse;

                    @Override
                    public void run() {
                        URL url = NetworkUtils.buildUrl(CATEGORY);
                        //Log.d(LOG_TAG, "url query is: "+ url.toString());
                        try {
                            networkResponse =  NetworkUtils.getResponseFromHttpUrl(url);
                            ExtractJson.jsonResponseExtractedNowplaying(getActivity(), networkResponse);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    @Override
    public void onOneClick(int position,
                           int ident, double voteAverage,
                           String backdrop, String poster, String title, String descript, String releaseDate) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(PopularFragment.INDENTIFICATION, ident);
        bundle.putDouble(PopularFragment.VOTEAVERAGE, voteAverage);
        bundle.putString(PopularFragment.TITLE, title);
        bundle.putString(PopularFragment.DESCRIPTION, descript);
        bundle.putString("BACKDROP",backdrop);
        bundle.putString("POSTER", poster);
        bundle.putString("RELEASEDATE", releaseDate);
        intent.putExtra(PopularFragment.INTENT_EXTRAS, bundle);
        startActivity(intent);
    }
}

package com.example.efhemo.platingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.efhemo.platingapp.Database.AppExecutors;
import com.example.efhemo.platingapp.Model.GenericListItem;
import com.example.efhemo.platingapp.Model.TopRatedModel;
import com.example.efhemo.platingapp.Utilities.ExtractJson;
import com.example.efhemo.platingapp.Utilities.NetworkUtils;
import com.example.efhemo.platingapp.ViewAdapter.AlternateAdapter;
import com.example.efhemo.platingapp.ViewAdapter.MovieAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TopRatedFragment extends android.support.v4.app.Fragment
        implements AlternateAdapter.OnOneClickItem {

    private static final String LOG_TAG = TopRatedFragment.class.getSimpleName();

    private static final String CATEGORY = "top_rated";

    MovieAdapter adapter;
    private AlternateAdapter alternateAdapter;

    //LiveData is a DATA HOLDER class that can be observed within a given lifecycle
    LiveData<List<TopRatedModel>> topRatedEntryList;

    private static final String saveState = "stateKey";
    private static final int TOTAL_CELLS_ROW = 2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_activity_recyclerview, container, false);


        //setRetainInstance(true); //This reason i dont want to use retainState is because i
        // want to handle Another view for landscape and big screen
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_movie);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), TOTAL_CELLS_ROW);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                int mod = position % 3;
                if(mod == 2){
                    return 2;
                }else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setSaveEnabled(true);
        //loadNetworkResult();

        //adapter = new MovieAdapter(getContext());
        alternateAdapter = new AlternateAdapter(getContext(), this );
        recyclerView.setAdapter(alternateAdapter);


        extractJsonAnotherThread(); //Network and jsonwork + insert data
        retrieveTask(); //LiveData doing worker thread, and observing changes to inform the observer


        return view;
    }


    //get data from database ONCE (viewmode) and observe changes (Livedata)
    private void retrieveTask() {

        //VIEWMODEL works in the worker thread and SURVIVE configuration changes (Rotation)
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        topRatedEntryList = mainViewModel.getTaskTopRated(); //the Observer is connected to thr data

        //LiveData OBSERVES DATA CHANGES, help us not to be re-quering the daabase
        //(use viewmodel so that new livedata object is not call again due to rotation changes)
        topRatedEntryList.observe(this, new Observer<List<TopRatedModel>>() { //observe the observer
            @Override
            public void onChanged(@Nullable List<TopRatedModel> topRatedEnt) { // Called when the data is changed

                List<GenericListItem> output = new ArrayList<>();
                if (topRatedEnt != null) {
                    output.addAll(topRatedEnt);
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
                            ExtractJson.jsonResponseExtractedTop(getActivity(), networkResponse);

                            //TODO: error inserting into topRatedmodel table
                            /*mDb = AppDatabase.getsInstance(getContext());
                            mDb.taskDao().insertTopRatedTaskList(topRatedEntr);*/
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }

    @Override
    public void onOneClick(int position, int ident, double popularityRes, String title, String descript) {

    }
}

package com.example.efhemo.platingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Utilities.ExtractJson;
import com.example.efhemo.platingapp.Utilities.NetworkUtils;
import com.example.efhemo.platingapp.ViewAdapter.AlternateAdapter;
import com.example.efhemo.platingapp.ViewAdapter.MovieAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment implements AlternateAdapter.OnOneClickItem{

    private static final String LOG_TAG = PopularFragment.class.getSimpleName();

    private static final String CATEGORY = "popular";
    public static final String INDENTIFICATION = "INDENTIFICATION";
    public static final String POPULARITY = "popularity";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String INTENT_EXTRAS = "IntentExtras";
    MovieAdapter movieAdapter;
    AlternateAdapter alternateAdapter;
    RecyclerView recyclerView;

    LiveData<List<PopularEntry>> popularEntryList;

    public static final String saveState = "stateKey";

    private static final int TOTAL_CELLS_ROW = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.movie_activity_recyclerview, container, false);


        //setRetainInstance(true); //This reason i dont want to use retainState is because i
        // want to handle Another view for landscape and big screen
        recyclerView = view.findViewById(R.id.recyclerview_movie);
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setSaveEnabled(true);
        //loadNetworkResult();

        alternateAdapter = new AlternateAdapter(getContext(), this);
        recyclerView.setAdapter(alternateAdapter);

        extractJsonAnotherThread(); //Network and jsonwork
        retrieveTask(); //LiveData doing worker thread, and observing changes to inform the observer


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState !=null){
            recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(saveState));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(saveState, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    //get data from database
    private void retrieveTask() {

        //VIEWMODEL works in the worker thread/ OBSERVES DATA CHANGES
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        popularEntryList = mainViewModel.getTask();

        popularEntryList.observe(this, new Observer<List<PopularEntry>>() {
            @Override
            public void onChanged(@Nullable List<PopularEntry> popularEntries) {

                List<GenericListItem> output = new ArrayList<>();
                if (popularEntries != null) {
                    output.addAll(popularEntries);
                }
                alternateAdapter.setTasks(output);
                //movieAdapter.setTasks(output);
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
                            ExtractJson.jsonResponseExtracted(getActivity(), networkResponse);

                            /*mDb = AppDatabase.getsInstance(getContext());
                            mDb.taskDao().insertPopularTaskList(popularEntr);*/
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });
    }




    void loadNetworkResult(){
        new Thread(new Runnable() {
            String networkResponse;
            @Override
            public void run() {
                URL url = NetworkUtils.buildUrl(CATEGORY);
                //Log.d(LOG_TAG, "url query is: "+ url.toString());
                try {
                    networkResponse =  NetworkUtils.getResponseFromHttpUrl(url);
                    // ExtractJson.jsonResponseExtracted(getContext(), networkResponse);

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }


    @Override
    public void onOneClick(int position, int ident, double popularityRes, String title, String descript) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(INDENTIFICATION, ident);
        bundle.putDouble(POPULARITY, popularityRes);
        bundle.putString(TITLE, title);
        bundle.putString(DESCRIPTION, descript);
        intent.putExtra(INTENT_EXTRAS, bundle);
        startActivity(intent);


    }
}

package com.example.efhemo.platingapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.efhemo.platingapp.Database.AppDatabase;
import com.example.efhemo.platingapp.Model.NowPlaying;
import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Model.TopRatedModel;
import com.example.efhemo.platingapp.Model.UpcomingModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {



    private LiveData<List<PopularEntry>> task;

    private LiveData<List<TopRatedModel>> taskTopRated;

    private LiveData<List<UpcomingModel>> taskUpcoming;

    private LiveData<List<NowPlaying>> taskNowplaying;


    public MainViewModel(@NonNull Application application) {
        super(application);

        /*LiveData works outside of the main Thread and therefore we dont need the Executors*/
        AppDatabase mDb= AppDatabase.getsInstance(this.getApplication());

        //we want it to survive through rotation changes when accessing data
        task = mDb.taskDao().loadPopularTask();
        taskTopRated = mDb.taskDao().loadTopRatedTask();
        taskUpcoming = mDb.taskDao().loadUpComingTask();
        taskNowplaying = mDb.taskDao().loadNowPlayingTask();
    }

    public LiveData<List<PopularEntry>> getTask(){
        return task;
    }

    public LiveData<List<TopRatedModel>> getTaskTopRated(){
        return taskTopRated;
    }

    public LiveData<List<UpcomingModel>> getTaskUpcoming() {
        return taskUpcoming;
    }

    public LiveData<List<NowPlaying>> getTaskNowplaying() {
        return taskNowplaying;
    }
}

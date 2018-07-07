package com.example.efhemo.platingapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.efhemo.platingapp.Database.AppDatabase;
import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Model.TopRatedModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {



    private LiveData<List<PopularEntry>> task;

    private LiveData<List<TopRatedModel>> taskTopRated;


    public MainViewModel(@NonNull Application application) {
        super(application);

        /*LiveData works outside of the main Thread and therefore we dont need the Executors*/
        AppDatabase mDb= AppDatabase.getsInstance(this.getApplication());
        task = mDb.taskDao().loadPopularTask(); //we want it to survive through rotation changes when accessing data
        taskTopRated = mDb.taskDao().loadTopRatedTask();
    }

    public LiveData<List<PopularEntry>> getTask(){
        return task;
    }

    public LiveData<List<TopRatedModel>> getTaskTopRated(){
        return taskTopRated;
    }
}

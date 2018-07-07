package com.example.efhemo.platingapp.Database;

/*class that we help us access read and write to the database (Also, delete and update)*/


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Model.TopRatedModel;

import java.util.List;

@Dao //access or write to db
public interface TaskDao {

    @Query("SELECT * FROM popular ORDER BY id DESC")
    LiveData<List<PopularEntry>> loadPopularTask();  //this means when i call loadpopu;arTask,
    //do this Query and return Live<List<p..>> i.e Appdabase/taskDao/loadTask(which table)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPopularTask(PopularEntry popularEntry);

    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPopularTaskList(List<PopularEntry> popularEntryList);*/

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatepopularTask(PopularEntry popularEntry);

    @Delete
    void deletePopularTask(PopularEntry popularEntry);



    @Query("SELECT * FROM top_rated ORDER BY id DESC")
    LiveData<List<TopRatedModel>> loadTopRatedTask();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTopRatedTaskList(TopRatedModel topRatedEntryList);


}

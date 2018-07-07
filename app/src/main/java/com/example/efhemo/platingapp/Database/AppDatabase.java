package com.example.efhemo.platingapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Model.TopRatedModel;

@Database(entities = {PopularEntry.class, TopRatedModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "moviedb";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK){   //restrict instantiation of object to one (this pattern)
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract TaskDao taskDao();
}

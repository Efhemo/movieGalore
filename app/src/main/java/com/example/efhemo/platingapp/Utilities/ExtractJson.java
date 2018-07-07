package com.example.efhemo.platingapp.Utilities;

import android.app.Activity;

import com.example.efhemo.platingapp.Database.AppDatabase;
import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Model.TopRatedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtractJson {

    private static final String LOG_TAG = ExtractJson.class.getSimpleName();

    private static final String jsonObjectresult = "results";

    private static final String identification = "id";
    private static final String vote_count = "vote_count";
    private static final String videoBoolen = "video";
    private static final String vote_average = "vote_average";
    private static final String title = "title";
    private static final String popularity = "popularity";
    private static final String poster_path = "poster_path";
    private static final String original_language = "original_language";
    private static final String backdrop_path = "backdrop_path";
    private static final String overview = "overview";
    private static final String release_date = "release_date";





    public static void jsonResponseExtracted(Activity context, String netWorkResponse){

        //List arrayList = new ArrayList<PopularEntry>();
        AppDatabase mDb;

        try{
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);

                int ident = jsonObjectResult.getInt(identification);
                int voteCount = jsonObjectResult.getInt(vote_count);
                boolean videoBool = jsonObjectResult.getBoolean(videoBoolen);
                Double voteAverage = jsonObjectResult.getDouble(vote_average);
                String titleMovie = jsonObjectResult.getString(title);
                Double popularityMovie = jsonObjectResult.getDouble(popularity);
                String posterPath = jsonObjectResult.getString(poster_path);
                String language = jsonObjectResult.getString(original_language);
                String backdropPath = jsonObjectResult.getString(backdrop_path);
                String overviewMovie = jsonObjectResult.getString(overview);
                String releaseDate = jsonObjectResult.getString(release_date);


                /*String itemResult = Integer.toString(voteCount) + " " +
                        String.valueOf(videoBool) + " " +
                        Double.toString(voteAverage) + " " +
                        titleMovie + " " +
                        Double.toString(popularityMovie) + " " +
                        posterPath + " " +
                        language + " " +
                        backdropPath + " " +
                        overviewMovie + " " +
                        releaseDate;*/
                PopularEntry popularEntryData = new PopularEntry(titleMovie, popularityMovie,
                        posterPath, voteAverage,
                        ident, voteCount, videoBool, language,
                        backdropPath, overviewMovie, releaseDate);

                /*arrayList.add(popularEntryData);*/

                mDb = AppDatabase.getsInstance(context);
                mDb.taskDao().insertPopularTask(popularEntryData);

                //we will query from the database
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public static void jsonResponseExtractedTop(Activity context, String netWorkResponse){

        //List arrayList = new ArrayList<TopRatedModel>();
        AppDatabase mDb;

        try{
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);

                int ident = jsonObjectResult.getInt(identification);
                int voteCount = jsonObjectResult.getInt(vote_count);
                boolean videoBool = jsonObjectResult.getBoolean(videoBoolen);
                Double voteAverage = jsonObjectResult.getDouble(vote_average);
                String titleMovie = jsonObjectResult.getString(title);
                Double popularityMovie = jsonObjectResult.getDouble(popularity);
                String posterPath = jsonObjectResult.getString(poster_path);
                String language = jsonObjectResult.getString(original_language);
                String backdropPath = jsonObjectResult.getString(backdrop_path);
                String overviewMovie = jsonObjectResult.getString(overview);
                String releaseDate = jsonObjectResult.getString(release_date);


                /*String itemResult = Integer.toString(voteCount) + " " +
                        String.valueOf(videoBool) + " " +
                        Double.toString(voteAverage) + " " +
                        titleMovie + " " +
                        Double.toString(popularityMovie) + " " +
                        posterPath + " " +
                        language + " " +
                        backdropPath + " " +
                        overviewMovie + " " +
                        releaseDate;*/
                TopRatedModel topRatedEntryData = new TopRatedModel(titleMovie, popularityMovie,
                        posterPath, voteAverage,
                        ident, voteCount, videoBool, language,
                        backdropPath, overviewMovie, releaseDate);

                /*arrayList.add(popularEntryData);*/

                mDb = AppDatabase.getsInstance(context);
                mDb.taskDao().insertTopRatedTaskList(topRatedEntryData);

                //we will query from the database
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

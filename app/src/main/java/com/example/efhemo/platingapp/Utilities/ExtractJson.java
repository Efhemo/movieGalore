package com.example.efhemo.platingapp.Utilities;

import android.app.Activity;
import android.util.Log;

import com.example.efhemo.platingapp.Database.AppDatabase;
import com.example.efhemo.platingapp.Model.NowPlaying;
import com.example.efhemo.platingapp.Model.PopularEntry;
import com.example.efhemo.platingapp.Model.ReviewsModel;
import com.example.efhemo.platingapp.Model.TopRatedModel;
import com.example.efhemo.platingapp.Model.UpcomingModel;
import com.example.efhemo.platingapp.Model.VidModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExtractJson {

    private static final String LOG_TAG = ExtractJson.class.getSimpleName();

    private static final String jsonObjectresult = "results";

    private static final String identification = "id";
    private static final String vote_average = "vote_average";
    private static final String title = "title";
    private static final String poster_path = "poster_path";
    private static final String backdrop_path = "backdrop_path";
    private static final String overview = "overview";
    private static final String release_date = "release_date";


    public static List<VidModel> jsonResponseExtractedVideo(String netWorkResponse) {

        List<VidModel> vidModelList = new ArrayList<VidModel>();
        try {
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);


                String key = jsonObjectResult.getString("key");
                String name = jsonObjectResult.getString("name");

                Log.w(LOG_TAG, "video result is or are " + key + " " + name);
                VidModel vidModel = new VidModel(key, name);

                vidModelList.add(vidModel);

            }

            return vidModelList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<ReviewsModel> jsonResponseExtractedReview(String netWorkResponse) {

        List<ReviewsModel> revModelList = new ArrayList<ReviewsModel>();
        try {
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);


                String authorsName = jsonObjectResult.getString("author");
                String authorsRev = jsonObjectResult.getString("content");

                Log.w(LOG_TAG, "review result is or are " + authorsName + " " + authorsRev);
                ReviewsModel revModel = new ReviewsModel(authorsName, authorsRev);

                revModelList.add(revModel);

            }

            return revModelList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static void jsonResponseExtracted(Activity context, String netWorkResponse) {

        //List arrayList = new ArrayList<PopularEntry>();
        AppDatabase mDb;

        try {
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);

                int ident = jsonObjectResult.getInt(identification);
                Double voteAverage = jsonObjectResult.getDouble(vote_average);
                String titleMovie = jsonObjectResult.getString(title);
                String posterPath = jsonObjectResult.getString(poster_path);
                String backdropPath = jsonObjectResult.getString(backdrop_path);
                String overviewMovie = jsonObjectResult.getString(overview);
                String releaseDate = jsonObjectResult.getString(release_date);

                PopularEntry popularEntryData = new PopularEntry(titleMovie,
                        posterPath, voteAverage,
                        ident,
                        backdropPath, overviewMovie, releaseDate);

                /*arrayList.add(popularEntryData);*/

                mDb = AppDatabase.getsInstance(context);
                mDb.taskDao().insertPopularTask(popularEntryData);

                //we will query from the database
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void jsonResponseExtractedTop(Activity context, String netWorkResponse) {

        //List arrayList = new ArrayList<TopRatedModel>();
        AppDatabase mDb;

        try {
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);

                int ident = jsonObjectResult.getInt(identification);
                Double voteAverage = jsonObjectResult.getDouble(vote_average);
                String titleMovie = jsonObjectResult.getString(title);
                String posterPath = jsonObjectResult.getString(poster_path);
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
                TopRatedModel topRatedEntryData = new TopRatedModel(titleMovie,
                        posterPath, voteAverage,
                        ident,
                        backdropPath, overviewMovie, releaseDate);
                /*arrayList.add(popularEntryData);*/

                mDb = AppDatabase.getsInstance(context);
                mDb.taskDao().insertTopRatedTaskList(topRatedEntryData);

                //we will query from the database
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void jsonResponseExtractedNowplaying(Activity context, String netWorkResponse) {

        //List arrayList = new ArrayList<TopRatedModel>();
        AppDatabase mDb;

        try {
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);

                int ident = jsonObjectResult.getInt(identification);
                Double voteAverage = jsonObjectResult.getDouble(vote_average);
                String titleMovie = jsonObjectResult.getString(title);
                String posterPath = jsonObjectResult.getString(poster_path);
                String backdropPath = jsonObjectResult.getString(backdrop_path);
                String overviewMovie = jsonObjectResult.getString(overview);
                String releaseDate = jsonObjectResult.getString(release_date);

                NowPlaying nowPlayingEntryData = new NowPlaying(titleMovie,
                        posterPath, voteAverage,
                        ident,
                        backdropPath, overviewMovie, releaseDate);

                //arrayList.add(popularEntryData);

                mDb = AppDatabase.getsInstance(context);
                mDb.taskDao().insertNowPlayingTaskList(nowPlayingEntryData);

                //we will query from the database
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void jsonResponseExtractedUpcoming(Activity context, String netWorkResponse) {

        //List arrayList = new ArrayList<TopRatedModel>();
        AppDatabase mDb;

        try {
            JSONObject jsonObject = new JSONObject(netWorkResponse);
            JSONArray jsonArray = jsonObject.getJSONArray(jsonObjectresult);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectResult = jsonArray.getJSONObject(i);

                int ident = jsonObjectResult.getInt(identification);
                Double voteAverage = jsonObjectResult.getDouble(vote_average);
                String titleMovie = jsonObjectResult.getString(title);
                String posterPath = jsonObjectResult.getString(poster_path);
                String backdropPath = jsonObjectResult.getString(backdrop_path);
                String overviewMovie = jsonObjectResult.getString(overview);
                String releaseDate = jsonObjectResult.getString(release_date);

                UpcomingModel upcomingEntryData = new UpcomingModel(titleMovie,
                        posterPath, voteAverage,
                        ident,
                        backdropPath, overviewMovie, releaseDate);
                //*arrayList.add(popularEntryData);*//*

                mDb = AppDatabase.getsInstance(context);
                mDb.taskDao().insertUpcomingTaskList(upcomingEntryData);

            }
            }catch(JSONException e){
                e.printStackTrace();
            }


        }



}

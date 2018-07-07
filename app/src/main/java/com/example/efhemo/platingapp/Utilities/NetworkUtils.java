package com.example.efhemo.platingapp.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String STATIC_MOVIE_URL =
            "https://api.themoviedb.org/3/movie";

    /*themoviebd image url: https://image.tmdb.org/t/p/w500/...*/

    private static final String PARAMS_API = "api_key";
    private static final String API = "95b230b9dc5ca4b835cdb00a1aef6270";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String LANGUAGE = "en-US";
    private static final String PARAMS_PAGE = "page";
    private static final int PAGE = 1;




    private static final String MOVIE_BASE_URL = STATIC_MOVIE_URL;

    public static URL buildUrl(String caterory) {
        Uri movieQueryUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(caterory)
                .appendQueryParameter(PARAMS_API, API)
                .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(PAGE))
                .build();

        try {
            URL movieQueryUrl = new URL(movieQueryUri.toString());
            Log.v(TAG, "URL: " + movieQueryUrl);
            return movieQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}

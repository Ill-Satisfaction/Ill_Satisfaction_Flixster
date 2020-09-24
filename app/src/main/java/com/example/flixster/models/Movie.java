package com.example.flixster.models;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private static final double POPULARITY_THRESHHOLD = 7.3;

    String posterPath;
    String backdropPath;
    String title;
    String overview;
    double rating;

    public Movie (JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
    }

    public static List<Movie> fromJsonArray (JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i<movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath(Context context) {
        // TODO update hard coding here
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation== Configuration.ORIENTATION_LANDSCAPE || rating>POPULARITY_THRESHHOLD) {
            return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
        }
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating; }
}

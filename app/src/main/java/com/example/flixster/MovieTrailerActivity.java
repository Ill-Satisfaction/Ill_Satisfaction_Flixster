package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
        final Integer MOVIE_ID = getIntent().getIntExtra("id", 0);
        final String API_URL = String.format("https://api.themoviedb.org/3/movie/"+MOVIE_ID+"/videos?api_key="+API_KEY+"&language=en-US");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MovieTrailerActivity", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject firstResult = results.getJSONObject(0);
                    Log.i("MovieTrailerActivity", "Results: "+results.toString());
                    final String youtubeId = firstResult.getString("key");

                    // resolve player view from layout
                    YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
                    // initialize with API key
                    playerView.initialize("AIzaSyBa9uHFGvm0EhM44MCqxQiXegb6rx5GUwo", new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            // do any work here to cue video, play video, &c.
                            youTubePlayer.cueVideo(youtubeId);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            // log the error
                            Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                        }
                    });
                } catch (JSONException e) {
                    Log.e("MovieTrailerActivity", "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MovieTrailerActivity", "onFailure");
                final String videoId = "";
            }
        });
    }
}
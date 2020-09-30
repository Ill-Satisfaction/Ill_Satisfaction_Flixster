package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    Context context;

    //view objects
    TextView tvTitle;
    TextView tvOverview;
    ImageView ivBackground;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        context = this;
        // resolve view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        // unwrap movie passed through intent, using simple name as key (as previously set up--not a general technique)
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));
        // set title, overview, image, ratingbar
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Glide.with(this).load(movie.getBackdropPath(this)).into(ivBackground);
        float voteAverage = (float) movie.getVoteAverage();
        rbVoteAverage.setRating(voteAverage = voteAverage>0 ? voteAverage/2.0f : voteAverage);
        // set onclickListener
        ivBackground.setOnClickListener(new BackgroundClickListener());
    }

    private class BackgroundClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i("MovieDetailsActivity", "image clicked");

            if(movie.getId()!=null) {
                Intent intent = new Intent(context, MovieTrailerActivity.class);
                intent.putExtra("id", movie.getId());
                context.startActivity(intent);
            }
        }
    }
}
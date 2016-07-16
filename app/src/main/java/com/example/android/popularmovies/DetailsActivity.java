package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView title = (TextView) findViewById(R.id.title);
        ImageView moviePoster = (ImageView) findViewById(R.id.MoviePoster);
        TextView overview = (TextView) findViewById(R.id.Overview);
        TextView releaseDate = (TextView) findViewById(R.id.releaseData);
        TextView voteAverage = (TextView) findViewById(R.id.voteAverage);

        AndroidMovies movie = (AndroidMovies) getIntent().getSerializableExtra("MyMovie");

        title.setText(movie.getTitle());
        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w185//" + movie.getImage()).into(moviePoster);
        overview.setText(movie.getPlot_synopsis());
        releaseDate.setText(movie.getRelease_date());
        voteAverage.setText(movie.getVote_average());
    }
}

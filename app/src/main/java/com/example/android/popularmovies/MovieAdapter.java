package com.example.android.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<AndroidMovies> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Activity context, List<AndroidMovies> androidMovies) {
        super(context, 0, androidMovies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AndroidMovies androidMovies = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }



        ImageView iconView = (ImageView) convertView.findViewById(R.id.androidMovieImage);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185//" + androidMovies.getImage()).into(iconView);

        return convertView;
    }
}
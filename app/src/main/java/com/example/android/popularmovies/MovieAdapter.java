package com.example.android.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<AndroidMovies> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private static String[] androidMoviesFinal;
    private static Activity context;

    public MovieAdapter(Activity context, List<AndroidMovies> androidMovies) {
        super(context, 0, androidMovies);
        ArrayList<AndroidMovies> androidMovie = new ArrayList<AndroidMovies>(androidMovies);
        String[] androidM = new String[androidMovie.size()];
        androidM = androidMovie.toArray(androidM);
        this.androidMoviesFinal = androidM;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //AndroidMovies androidMovies = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.androidMovieImage);
        Picasso.with(context).load(this.androidMoviesFinal[position]).into(iconView);

        return convertView;
    }
}
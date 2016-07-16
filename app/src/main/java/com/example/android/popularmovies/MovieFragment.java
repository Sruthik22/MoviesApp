package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieFragment extends Fragment {

        private MovieAdapter movieAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            setHasOptionsMenu(true);

            callFetchWeatherTask("top_rated");

            return rootView;
        }

    public void callFetchWeatherTask(String type) {
        FetchWeatherTask weatherTask = new FetchWeatherTask();
        weatherTask.execute(type);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_rating) {
            callFetchWeatherTask("top_rated");
            return true;
        }

        else if (id == R.id.action_popular) {
            callFetchWeatherTask("popular");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, AndroidMovies[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private AndroidMovies[] getMovieDataFromJson(String json)
                throws JSONException {

            List<AndroidMovies> ListOfMovies = new ArrayList<AndroidMovies>();


            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i=0; i < results.length(); i++) {
                AndroidMovies movie = new AndroidMovies();
                movie.setImage(jsonObject.getString("poster_path"));
                movie.setTitle(jsonObject.getString("original_title"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setVoteAverage(jsonObject.getString("vote_average"));
                movie.setPlotSynopsis(jsonObject.getString("overview"));
                ListOfMovies.add(movie);
            }

            AndroidMovies[] movieArray = ListOfMovies.toArray(new AndroidMovies[0]);
            return movieArray;

        }

        @Override
        protected AndroidMovies[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                final String FORECAST_BASE_URL = "https://api.themoviedb.org/3/movie/";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendPath(params[0])
                        .appendQueryParameter(APPID_PARAM, "08cad5f78a9c82e7729dc841d27b45f2")
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast string: " + forecastJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(AndroidMovies[] strings) {
            // Initialize the adapter with the results
            movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(strings));
            // Set the adapter for the GridView
            GridView gridView = (GridView) getView().findViewById(R.id.movies_grid);
            gridView.setAdapter(movieAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    AndroidMovies myMovie = movieAdapter.getItem(position);
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("myMovie", myMovie);
                    startActivity(intent);
                }
            });
        }
    }
}


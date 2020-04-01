package com.gyumin.udacitymovieapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.gyumin.udacitymovieapp.data.Movie;
import com.gyumin.udacitymovieapp.data.Movies;
import com.gyumin.udacitymovieapp.R;
import com.gyumin.udacitymovieapp.adapter.MovieAdapter;
import com.gyumin.udacitymovieapp.util.Constants;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();

        sendRequest(Constants.POPULAR_BASE_URL);

        recyclerView = findViewById(R.id.movie_rv);
        int columnNum = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnNum));
        movieAdapter = new MovieAdapter(this, movies);
        movieAdapter.setMovieClickListener(this);
        recyclerView.setAdapter(movieAdapter);

    }

    public void sendRequest(String baseUrl) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                baseUrl + Constants.API_KEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Movies result = gson.fromJson(response, Movies.class);
                        movies.addAll(result.results);
                        movieAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }
        );
        request.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onMovieClick(View view, int position) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.MOVIE_DATA, movies.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_popular :
                movieAdapter.clearData();
                sendRequest(Constants.POPULAR_BASE_URL);
                return true;
            case R.id.action_rate :
                movieAdapter.clearData();
                sendRequest(Constants.TOP_RATE_BASE_URL);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

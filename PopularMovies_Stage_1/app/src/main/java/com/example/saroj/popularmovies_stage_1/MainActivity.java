package com.example.saroj.popularmovies_stage_1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.saroj.popularmovies_stage_1.Adapter.MoviesAdapter;
import com.example.saroj.popularmovies_stage_1.Model.Movie;
import com.example.saroj.popularmovies_stage_1.Model.MovieResponse;
import com.example.saroj.popularmovies_stage_1.Rest.MovieApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private MoviesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Movie> mMoviesList;
    private boolean mTwoPane=false;
    private final String API_KEY = "Enter API KEY HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = new ArrayList<Movie>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        //mAdapter = new MoviesAdapter(mMoviesList,  R.layout.item_list, getApplicationContext(), this);
        connectAndGetApiData();

    }


    public void connectAndGetApiData(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                //mAdapter.clear();
                mMoviesList.addAll(response.body().getResults());
                //Log.d(TAG, "Number of movies received: " + mMoviesList.size());
                mRecyclerView.setAdapter(new MoviesAdapter(mMoviesList,  R.layout.item_list, getApplicationContext()));


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "No Internet Connectivity. Please, Check you Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }


}

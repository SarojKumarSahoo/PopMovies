package com.example.saroj.moviesapp.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.saroj.moviesapp.Activity.DetailsActivity;
import com.example.saroj.moviesapp.Activity.MainActivity;
import com.example.saroj.moviesapp.Adapter.MoviesAdapter;
import com.example.saroj.moviesapp.BuildConfig;
import com.example.saroj.moviesapp.Model.Movie;
import com.example.saroj.moviesapp.Model.MovieResponse;
import com.example.saroj.moviesapp.R;
import com.example.saroj.moviesapp.rest.MovieApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainFragment extends Fragment implements MoviesAdapter.ListItemClickListener {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView mRecyclerView;
    private List<Movie> mMoviesList;
    private boolean mTwoPane;
    private final static String API_KEY = BuildConfig.APIKey;
    private MoviesAdapter mAdapter;
    private SharedPreferences mSharedPrefs;
    private int mColumnCount = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mMoviesList = new ArrayList<Movie>();


        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mColumnCount = 1;
        }
        else {
            mColumnCount = 2;
        }


        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), mColumnCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MoviesAdapter(mMoviesList,  R.layout.fragment_movie_item, getActivity(), this);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        String choice = mSharedPrefs.getString(getResources().getString(R.string.pref_sortOrder), "1");
        Call<MovieResponse> call = null;
        if(choice.equals("2")){
            call = movieApiService.getTopRatedMovies(API_KEY);
        }else{
            call = movieApiService.getPopularMovies(API_KEY);
        }

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                mMoviesList = response.body().getResults();
                mRecyclerView.swapAdapter(new MoviesAdapter(mMoviesList,  R.layout.fragment_movie_item, getActivity(), MainFragment.this), false);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Toast.makeText(getActivity(), "No Internet Connectivity. Please, Check you Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        mTwoPane = ((MainActivity) getActivity()).isTablet();
        if(!mTwoPane) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("movie", mMoviesList.get(clickedItemIndex));
            startActivity(intent);
        }else{
            ((MainActivity)getActivity()).replaceFragment(mMoviesList.get(clickedItemIndex));
        }



    }
}

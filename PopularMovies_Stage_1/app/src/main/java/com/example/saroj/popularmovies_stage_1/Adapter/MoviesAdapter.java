package com.example.saroj.popularmovies_stage_1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.saroj.popularmovies_stage_1.Model.Movie;
import com.example.saroj.popularmovies_stage_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Saroj on 4/6/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";



    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView movieImage;

        public MovieViewHolder(View v) {
            super(v);
            movieImage = (ImageView) v.findViewById(R.id.poster);
        }


    }

    public void add(Movie movieItem){
        movies.add(movieItem);
    }
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        String image_url = IMAGE_URL_BASE_PATH + movies.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}

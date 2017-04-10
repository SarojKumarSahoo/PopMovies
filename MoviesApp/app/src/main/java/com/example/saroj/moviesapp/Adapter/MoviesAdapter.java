package com.example.saroj.moviesapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saroj.moviesapp.R;
import com.example.saroj.moviesapp.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context, ListItemClickListener listener) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        mOnClickListener = listener;
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView movieImage;
        TextView mTextView;

        public MovieViewHolder(View v) {
            super(v);
            movieImage = (ImageView) v.findViewById(R.id.poster);
            mTextView = (TextView) v.findViewById(R.id.title);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
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
        holder.mTextView.setText(movies.get(position).getTitle().toString());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}

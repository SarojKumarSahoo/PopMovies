package com.example.saroj.moviesapp.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saroj.moviesapp.Model.Movie;
import com.example.saroj.moviesapp.R;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {
    TextView titleView,releaseDateView,voteAvgView,synopsisView;
    ImageView movieImgView;
    ImageView movieBackDropView;


    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";
    public DetailsFragment() {
    }

    public static Fragment newInstance(Movie movieItem) {
        Fragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie",movieItem);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(" ");
        titleView = (TextView) rootView.findViewById(R.id.title_content);
        releaseDateView = (TextView) rootView.findViewById(R.id.release_date_content);
        voteAvgView = (TextView) rootView.findViewById(R.id.vote_average_content);
        synopsisView = (TextView) rootView.findViewById(R.id.overview_content);
        movieImgView = (ImageView) rootView.findViewById(R.id.poster);

        Movie movieItem = getArguments().getParcelable("movie");
        titleView.setText(movieItem.getTitle());
        releaseDateView.setText(movieItem.getReleaseDate());
        voteAvgView.setText(movieItem.getVoteAverage()+"/10");
        synopsisView.setText(movieItem.getOverview());
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {

            String imgUrl = movieItem.getPosterPath();
            Picasso.with(getActivity())
                    .load(IMAGE_URL_BASE_PATH + imgUrl)
                    .into(movieImgView);
        }else{
            String backDropUrl = movieItem.getBackdropPath();
            Picasso.with(getActivity())
                    .load(IMAGE_URL_BASE_PATH + backDropUrl)
                    .into(movieImgView);
        }



        return rootView;
    }
}

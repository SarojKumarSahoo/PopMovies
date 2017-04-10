package com.example.saroj.moviesapp.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.saroj.moviesapp.Fragment.DetailsFragment;
import com.example.saroj.moviesapp.Model.Movie;
import com.example.saroj.moviesapp.R;


public class DetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Movie movieItem = getIntent().getParcelableExtra("movie");
        Fragment detailsFragment = DetailsFragment.newInstance(movieItem);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.details_fragment_container,detailsFragment);
        ft.commit();

    }
}

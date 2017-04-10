package com.example.saroj.moviesapp.Activity;

import android.content.Intent;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saroj.moviesapp.BuildConfig;
import com.example.saroj.moviesapp.Fragment.DetailsFragment;
import com.example.saroj.moviesapp.Fragment.MainFragment;
import com.example.saroj.moviesapp.Model.Movie;
import com.example.saroj.moviesapp.R;

public class MainActivity extends AppCompatActivity {

    public Boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        PreferenceManager.setDefaultValues(this, R.xml.settings_screen, false);

        if(findViewById(R.id.container)!= null){
            mTwoPane = true;
        }
        else{
            mTwoPane = false;
        }
    }

    public boolean isTablet() {
        return mTwoPane;
    }

    public void replaceFragment(Movie movieItem) {
        Bundle args = new Bundle();
        args.putParcelable("movie", movieItem);
        DetailsFragment detailFragment = new DetailsFragment();
        detailFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

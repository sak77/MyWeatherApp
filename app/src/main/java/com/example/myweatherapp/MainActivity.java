package com.example.myweatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myweatherapp.ui.MainFragment;

/**
 * Volvo test assignment to display weather info from an API.
 * I am using MVVM with retrofit, rxjava and livedata.
 * Used databinding to display weather details page.
 *
 * MainActivity: The only activity in this app. It
 * performs the following functions -
 * 1. load mainfragment into the fragment container layout.
 * 2. implements onBackStackChangedListener to listen for changes to the fragmentmanager back stack
 * 3. if no. of items in backstack is more than one .i.e. if there are more than one
 * fragments in the backstack, it will display the arrow button in the toolbar.
 * 4. Also if no. of items in backstack is greater than 0. It will update a flag in MainFragment which
 * tells mainfragment that there is already one instance of its type in the backstack.
 * 4. Overrides onSupportNavigateUp to handle back arrow click behavior (is there a better way to do this?)
 */
public class MainActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMainFragment();
    }

    private void loadMainFragment() {
        MainFragment mainFragment = MainFragment.newInstance("", "");
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackStackChanged() {
        //Display back button if back stack has one ore more items
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(
                        getSupportFragmentManager().getBackStackEntryCount() > 0);
        if (getSupportFragmentManager().getBackStackEntryCount()> 0) {
            MainFragment.MAIN_EXISTS = true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Pop back stack when user presses nav button
        getSupportFragmentManager().popBackStack();
        return true;
    }
}

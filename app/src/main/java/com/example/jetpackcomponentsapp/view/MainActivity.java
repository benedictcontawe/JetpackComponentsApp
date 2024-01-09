package com.example.jetpackcomponentsapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jetpackcomponentsapp.MainViewModel;
import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.view.fragment.AddFragment;
import com.example.jetpackcomponentsapp.view.fragment.MainFragment;
import com.example.jetpackcomponentsapp.view.fragment.UpdateFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            callMainFragment();
            viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //viewModel.setItems();
    }

    private void callMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow();
    }

    public void callAddFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AddFragment.newInstance())
                .addToBackStack(AddFragment.TAG).commit();
    }

    public void  callUpdateFragment() {
        UpdateFragment
                .newInstance()
                .show(getSupportFragmentManager().beginTransaction(),UpdateFragment.TAG);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
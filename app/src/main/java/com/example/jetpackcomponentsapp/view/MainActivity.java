package com.example.jetpackcomponentsapp.view;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
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
        //getOnBackPressedDispatcher().addCallback(this, getHandleOnBackPressed());
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
    /*
    private OnBackPressedCallback getHandleOnBackPressed() {
        return new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish();
                } else getSupportFragmentManager().popBackStackImmediate();
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (!getOnBackPressedDispatcher().hasEnabledCallbacks() && getSupportFragmentManager().getBackStackEntryCount() == 0)
            super.onBackPressed();
        else
            getSupportFragmentManager().popBackStack();
    }
    */
}
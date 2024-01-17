package com.example.jetpackcomponentsapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jetpackcomponentsapp.view.model.ObjectViewModel;
import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.view.fragment.AddFragment;
import com.example.jetpackcomponentsapp.view.fragment.ObjectFragment;
import com.example.jetpackcomponentsapp.view.fragment.UpdateFragment;

public class ObjectActivity extends AppCompatActivity {

    public static final String TAG = ObjectActivity.class.getSimpleName();
    public static Intent newIntent(Context context) {
        return new Intent(context, ObjectActivity.class).setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
    }
    private ObjectViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
        if (savedInstanceState == null) {
            callMainFragment();
            viewModel = new ViewModelProvider(this).get(ObjectViewModel.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchItems();
    }

    private void callMainFragment() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, ObjectFragment.newInstance())
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
}
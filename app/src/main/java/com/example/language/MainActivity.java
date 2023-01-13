package com.example.language;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderGetKt;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.language.databinding.MainBinder;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, ViewTreeObserver.OnScrollChangedListener {

    private MainBinder binder = null;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binder.setLifecycleOwner(this);
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binder.setViewModel(viewModel);
        binder.scrollView.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binder.scrollView.scrollTo(0,0);
        binder.scrollView.getViewTreeObserver().addOnScrollChangedListener(this);
        binder.viewShadowTop.setVisibility(View.INVISIBLE);
        binder.viewShadowBottom.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binder.scrollView.getViewTreeObserver().removeOnScrollChangedListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //if (view == binder.scrollView)
        return binder.chckSwitch.isChecked();
    }

    @Override
    public void onScrollChanged() {
        viewModel.setTopDetector(binder.scrollView.getScrollY());
        viewModel.setBottomDetector(
            binder.scrollView.getChildAt(binder.scrollView.getChildCount() - 1).getBottom() -
            (binder.scrollView.getHeight() + binder.scrollView.getScrollY())
        );
        if(viewModel.getTopDetector() <= 0) {
            Log.d(MainActivity.class.getSimpleName(),"Scroll View top reached");
            binder.viewShadowTop.setVisibility(View.INVISIBLE);
        } else if(viewModel.getBottomDetector() <= 0 ) {
            Log.d(MainActivity.class.getSimpleName(),"Scroll View bottom reached");
            binder.viewShadowBottom.setVisibility(View.INVISIBLE);
        } else {
            binder.viewShadowTop.setVisibility(View.VISIBLE);
            binder.viewShadowBottom.setVisibility(View.VISIBLE);
        }
    }
}
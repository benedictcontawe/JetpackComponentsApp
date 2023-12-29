package com.example.jetpackcomponentsapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.jetpackcomponentsapp.MainViewModel;
import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    static private final String TAG = MainActivity.class.getSimpleName();
    static private MainBinder dataBinding;
    static private IndicatorAdapter indicatorAdapter;
    static private LinearLayoutManager indicatorLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //region For View Binding
        //viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(viewBinding.getRoot());
        //endregion
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            dataBinding.setViewModel(new ViewModelProvider(this).get(MainViewModel.class));
            dataBinding.setLifecycleOwner(this);
            setViewPager();
            setIndicator();
        }
    }

    private void setViewPager() {
        //region For View Binding
        //viewBinding.viewPagerTwo.setAdapter(CustomViewPagerAdapter(dataBinding.getViewModel()?.getItems()));
        //endregion
        dataBinding.viewPagerTwo.setAdapter(new CustomViewPagerAdapter(dataBinding.getViewModel().getItems()));
        //binding.viewPagerTwo.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    }

    private void setIndicator() {
        indicatorAdapter = new IndicatorAdapter(dataBinding.getViewModel().getItemCount());
        indicatorLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        dataBinding.indicatorRecyclerView.setOnTouchListener(this);
        dataBinding.indicatorRecyclerView.setAdapter(indicatorAdapter);
        dataBinding.indicatorRecyclerView.setLayoutManager(indicatorLinearLayoutManager);
        dataBinding.indicatorRecyclerView.setHasFixedSize(true);
    }

    private ViewPager2.OnPageChangeCallback getOnPageChangeCallback() {
        return new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicatorAdapter.setSelected(position);
                //binding.indicatorRecyclerView.scrollToPosition(position);
                //binding.indicatorRecyclerView.getLayoutManager().scrollToPosition(position);
                indicatorLinearLayoutManager.scrollToPositionWithOffset(position, getResources().getDimensionPixelSize(R.dimen.indicator_extra_scroll));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                switch (state) {
                    case ViewPager2.SCROLL_STATE_IDLE:
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2.SCROLL_STATE_IDLE");
                        break;
                    case ViewPager2.SCROLL_STATE_DRAGGING:
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2 . SCROLL_STATE_DRAGGING");
                        break;
                    case ViewPager2.SCROLL_STATE_SETTLING:
                        Log.d(TAG,"onPageScrollStateChanged($state) : ViewPager2.SCROLL_STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        };
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view == dataBinding.indicatorRecyclerView) return true;
        else return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBinding.viewPagerTwo.registerOnPageChangeCallback(getOnPageChangeCallback());
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataBinding.viewPagerTwo.unregisterOnPageChangeCallback(getOnPageChangeCallback());
    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }
    */
}
package com.example.jetpackcomponentsapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jetpackcomponentsapp.databinding.ObjectBinder;
import com.example.jetpackcomponentsapp.view.model.ObjectViewModel;
import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.view.CustomListeners;
import com.example.jetpackcomponentsapp.view.activity.ObjectActivity;
import com.example.jetpackcomponentsapp.view.adapter.CustomAdapter;

import java.util.List;

public class ObjectFragment extends Fragment implements CustomListeners, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = ObjectFragment.class.getSimpleName();
    public static ObjectFragment newInstance() {
        return new ObjectFragment();
    }

    private ObjectBinder binding;
    private ObjectViewModel viewModel;
    private CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_object,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ObjectViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setSwipeRefresh();
        setRecyclerView();
        setFloatingActionButton();
    }

    private void setSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        viewModel.observeLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                //binding.swipeRefreshLayout.isRefreshing();
                binding.swipeRefreshLayout.setRefreshing(isLoading);
            }
        });
    }

    private void setRecyclerView() {
        adapter = new CustomAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<CustomModel>>() {
            @Override
            public void onChanged(List<CustomModel> list) {
                adapter.setItems(list);
                adapter.notifyDataSetChanged();
                if (binding.swipeRefreshLayout.isRefreshing())
                    binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        binding.recyclerView.setHasFixedSize(true);
    }

    private void setFloatingActionButton() {
        binding.floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdd();
            }
        });

        binding.floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteAll();
            }
        });
    }

    @Override
    public void onRefresh() {
        binding.recyclerView.removeAllViews();
        binding.getViewModel().fetchItems();
    }

    private void onAdd() {
        ( (ObjectActivity) requireActivity() ).callAddFragment();
    }

    @Override
    public void onUpdate(CustomModel item, int position) {
        viewModel.setUpdate(item);
        ( (ObjectActivity) requireActivity() ).callUpdateFragment();
    }

    @Override
    public void onDelete(CustomModel item, int position) {
        viewModel.deleteItem(item);
    }
}
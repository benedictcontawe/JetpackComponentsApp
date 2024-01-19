package com.example.jetpackcomponentsapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.jetpackcomponentsapp.view.model.ObjectViewModel;
import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.databinding.UpdateBinder;
import com.example.jetpackcomponentsapp.model.CustomModel;

public class UpdateFragment extends DialogFragment {

    public static final String TAG = UpdateFragment.class.getSimpleName();
    public static UpdateFragment newInstance() {
        return new UpdateFragment();
    }

    private UpdateBinder binding;
    private ActivityResultLauncher<Intent> startForResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragment);
        onActivityResult();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update,container,false);
        binding.setViewModel(new ViewModelProvider(requireActivity()).get(ObjectViewModel.class));
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.getViewModel().observeUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                Glide.with(requireContext())
                    .asBitmap()
                    .fallback(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.animation_loading)
                    .priority(Priority.NORMAL)
                    .load(uri)
                    .into(binding.imageView);
            }
        });
        binding.getViewModel().getUpdate().observe(getViewLifecycleOwner(), new Observer<CustomModel>() {
            @Override
            public void onChanged(CustomModel item) {
                binding.editText.setText(item.name);
                binding.editText.requestFocus();
                binding.editText.selectAll();
                showSoftKeyboard(binding.editText);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.getViewModel().updateItem();
                hideSoftKeyboard();
                dismiss();
            }
        });
    }

    private void showSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void  onActivityResult() {
        startForResult = registerForActivityResult ( new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult activityResult) {
                binding.getViewModel().setUri(activityResult);
            }
        } );
    }
}
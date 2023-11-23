package com.kelvin.traveling.features.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.traveling.R;
import com.kelvin.traveling.databinding.FragmentHHomeBinding;
import com.kelvin.traveling.features.home.activity.HomeActivity;

public class HomeFragment extends Fragment {

    FragmentHHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof HomeActivity homeActivity) {
            homeActivity.setToolbarVisibility(true);
            homeActivity.setToolbarBackButton();
        }
    }
}
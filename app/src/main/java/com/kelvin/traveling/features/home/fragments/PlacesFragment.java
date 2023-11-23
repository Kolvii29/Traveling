package com.kelvin.traveling.features.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kelvin.traveling.databinding.FragmentPlacesBinding;

public class PlacesFragment extends Fragment {

    private FragmentPlacesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlacesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
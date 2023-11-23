package com.kelvin.traveling.features.home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kelvin.traveling.database.RepositoryCars;
import com.kelvin.traveling.databinding.FragmentCarBinding;
import com.kelvin.traveling.features.home.adapter.CarAdapter;
import com.kelvin.traveling.features.home.domain.Car;

public class CarFragment extends Fragment {

    private FragmentCarBinding binding;
    private CarAdapter carAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCarBinding.inflate(getLayoutInflater());

        CarAdapter adapter = new CarAdapter(RepositoryCars.getListCar(), new CarAdapter.OnCarItem() {

            @Override
            public void onStartClick(Car itemSelected) {
                itemSelected.setFavorite(!itemSelected.isFavorite());
                if (carAdapter != null){
                    carAdapter.notifyDataSetChanged();
                    Toast.makeText(requireActivity(), "Coche favorito: " + itemSelected.getNameCar(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCarClick(Car itemSelected) {
                Toast.makeText(requireActivity(), "Coche seleccionado: " + itemSelected.getNameCar(), Toast.LENGTH_SHORT).show();
            }

        });
        binding.mainCarsRv.setHasFixedSize(true);
        binding.mainCarsRv.setAdapter(adapter);

        return binding.getRoot();
    }
}
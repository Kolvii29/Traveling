package com.kelvin.traveling.features.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kelvin.traveling.features.home.fragments.CameraFragment;
import com.kelvin.traveling.features.home.fragments.CarFragment;
import com.kelvin.traveling.features.home.fragments.HomeFragment;
import com.kelvin.traveling.features.home.fragments.PlacesFragment;
import com.kelvin.traveling.features.home.fragments.ProfileFragment;

public class PAdapterHome extends FragmentStateAdapter {

    public PAdapterHome(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            default -> new HomeFragment();
            case 1 -> new CarFragment();
            case 2 -> new PlacesFragment();
            case 3 -> new ProfileFragment();
            case 4 -> new CameraFragment();
        };
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

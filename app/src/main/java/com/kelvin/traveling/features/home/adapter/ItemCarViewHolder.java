package com.kelvin.traveling.features.home.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.traveling.databinding.ItemListCarBinding;
import com.kelvin.traveling.features.home.domain.Car;

public class ItemCarViewHolder extends RecyclerView.ViewHolder {

    private final ItemListCarBinding binding;
    private final CarAdapter.OnCarItem listener;

    public ItemCarViewHolder(@NonNull ItemListCarBinding binding, CarAdapter.OnCarItem listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(Car itemCar) {
        binding.itemlayoutListcarName.setText(itemCar.getNameCar());
        binding.itemlayoutListcarBackground.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), itemCar.getFeatureCar().colorRes()));
        binding.itemlayoutListcarImg.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), itemCar.getFeatureCar().imgCar()));
        binding.itemlayoutListcarPrice.setText(itemCar.getPriceCar());

        binding.itemlayoutListcarFavorite.setImageResource(itemCar.getStarDrawable());
        binding.itemlayoutListcarFavorite.setOnClickListener(v -> {
            listener.onStartClick(itemCar);
            binding.itemlayoutListcarFavorite.setImageResource(itemCar.getStarDrawable());
        });
    }
}

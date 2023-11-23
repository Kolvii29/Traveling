package com.kelvin.traveling.features.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelvin.traveling.databinding.ItemListCarBinding;
import com.kelvin.traveling.features.home.domain.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<ItemCarViewHolder> {

    private final List<Car> items;
    private OnCarItem listener;

    public CarAdapter(List<Car> items, OnCarItem listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemCarViewHolder(ItemListCarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCarViewHolder holder, int position) {
        holder.bind(items.get(position));
        holder.itemView.setOnClickListener(v -> listener.onCarClick(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnCarItem {
        void onStartClick(Car itemSelected);
        void onCarClick(Car itemSelected);
    }
}

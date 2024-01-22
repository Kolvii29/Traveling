package com.kelvin.traveling.features.home.domain;

import androidx.annotation.DrawableRes;

import com.kelvin.traveling.R;

import java.util.Locale;

public class Car {

    private final String nameCar;
    private final float priceCar;
    private final FeatureCar featureCar;
    private boolean isFavorite;

    public Car(String nameCar, float priceCar, FeatureCar featureCar, boolean isFavorite) {
        this.nameCar = nameCar;
        this.priceCar = priceCar;
        this.featureCar = featureCar;
        this.isFavorite = isFavorite;
    }


    public String getPriceCar() {
        return String.format(Locale.getDefault(), "$%.2f/day", priceCar);
    }

    public FeatureCar getFeatureCar() {
        return featureCar;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    @DrawableRes
    public int getStarDrawable() {
        return isFavorite ? R.drawable.ic_star : R.drawable.ic_star_border;
    }

    public String getNameCar() {
        return nameCar;
    }
}

package com.kelvin.traveling.features.home.domain;

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

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getNameCar() {
        return nameCar;
    }
}

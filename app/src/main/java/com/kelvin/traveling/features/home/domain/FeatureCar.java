package com.kelvin.traveling.features.home.domain;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

public record FeatureCar(@ColorRes int colorRes, @DrawableRes int imgCar) {
    public FeatureCar(int colorRes, int imgCar) {
        this.colorRes = colorRes;
        this.imgCar = imgCar;
    }
}

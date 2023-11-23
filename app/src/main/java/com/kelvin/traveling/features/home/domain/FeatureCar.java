package com.kelvin.traveling.features.home.domain;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

public class FeatureCar {
    private final @ColorRes int colorRes;
    private final @DrawableRes int imgCar;

    public FeatureCar(int colorRes, int imgCar) {
        this.colorRes = colorRes;
        this.imgCar = imgCar;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }

    @DrawableRes
    public int getImgCar() {
        return imgCar;
    }
}

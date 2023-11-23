package com.kelvin.traveling.core.transformers;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class Pager2_VerticalFlipTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setTranslationY(-position*page.getWidth());
        page.setCameraDistance(20000);

        if (position < 0.5 && position > -0.5){
            page.setVisibility(View.VISIBLE);
        }
        else {
            page.setVisibility(View.INVISIBLE);
        }

        if (position < -1){

            page.setAlpha(0);
        }
        else if (position <= 0 ){
            page.setAlpha(1);
            page.setRotationX(180*(1-Math.abs(position)+1));
        }
        else if (position <= 1){
            page.setAlpha(1);
            page.setRotationX(-180*(1-Math.abs(position)+1));
        }
        else {

            page.setAlpha(0);
        }
    }
}
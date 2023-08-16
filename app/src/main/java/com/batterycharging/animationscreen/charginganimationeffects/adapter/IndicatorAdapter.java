package com.batterycharging.animationscreen.charginganimationeffects.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.databinding.IntroScreen1Binding;
import com.batterycharging.animationscreen.charginganimationeffects.databinding.IntroScreen2Binding;


public class IndicatorAdapter extends PagerAdapter {
    int size;

    public IndicatorAdapter(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (position == 0) {
            IntroScreen1Binding binding = IntroScreen1Binding.inflate(LayoutInflater.from(container.getContext()), container, false);
            container.addView(binding.getRoot());
            return binding.getRoot();
        } else {
            IntroScreen2Binding binding = IntroScreen2Binding.inflate(LayoutInflater.from(container.getContext()), container, false);
            container.addView(binding.getRoot());
            return binding.getRoot();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.IndicatorAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.databinding.ActivityOnBoardingBinding;
import com.batterycharging.animationscreen.charginganimationeffects.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {
    ActivityOnBoardingBinding binding;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.small.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        prefManager = new PrefManager(this);

        setUpUi();
        addIndicator(0);
    }

    private void setUpUi() {

        binding.viewPager.setAdapter(new IndicatorAdapter(2));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                addIndicator(position);
                if (position < 2) {
                    binding.txtNext.setVisibility(View.VISIBLE);
                    binding.llIndicator.setVisibility(View.VISIBLE);
                    binding.txtNext.setText("Next");
                } else {
                    binding.llIndicator.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.txtNext.setOnClickListener(v -> {
            AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                int current = getItem(+1);
                if (current < 2) {
                    binding.viewPager.setCurrentItem(current);
                } else {
                    Intent intent = new Intent(OnBoardingActivity.this, PrivacyPolicyActivity.class);
                    startActivity(intent);
                }
            });
        });
    }


    private int getItem(int i) {
        return binding.viewPager.getCurrentItem() + i;
    }

    private void addIndicator(int position) {
        List<ImageView> pages = new ArrayList<>();
        pages.clear();
        pages.add(binding.textView2);
        pages.add(binding.textView3);

        for (int i = 0; i < pages.size(); i++) {
//            pages.get(i).setText(Html.fromHtml("&#8226;"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                pages.get(i).setBackground(getResources().getDrawable(R.drawable.filled));
            }
            if (i == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    pages.get(i).setBackground(getResources().getDrawable(R.drawable.filled));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    pages.get(i).setBackground(getResources().getDrawable(R.drawable.outline));
                }
            }
        }
    }
}
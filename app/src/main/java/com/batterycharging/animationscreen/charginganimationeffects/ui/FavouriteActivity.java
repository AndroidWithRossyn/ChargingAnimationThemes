package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.fragment.LiveChargeFragment;
import com.batterycharging.animationscreen.charginganimationeffects.fragment.LiveThemeFavFragment;
import com.batterycharging.animationscreen.charginganimationeffects.fragment.ThemeFavFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    ImageView back_btn;
    ViewPager viewpager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        back_btn = findViewById(R.id.back_btn);
        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        FavouriteActivity.super.onBackPressed();
                    }
                });
            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ThemeFavFragment(), getResources().getString(R.string.wallpaper));
        adapter.addFragment(new LiveThemeFavFragment(), getResources().getString(R.string.live_wallpaper));
        adapter.addFragment(new LiveChargeFragment(), getResources().getString(R.string.live_charger));
        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                FavouriteActivity.super.onBackPressed();
            }
        });
    }
}

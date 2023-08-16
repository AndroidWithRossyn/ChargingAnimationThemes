package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.bumptech.glide.Glide;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.service.GIFWallpaperService;

import pl.droidsonroids.gif.GifImageView;

public class CategoryShowVideoActivity extends AppCompatActivity {
    private ImageView back;
    private GifImageView gifImageView;
    private TextView setWallpaper;
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show_video);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        gifImageView = findViewById(R.id.gifimageView);
        setWallpaper = findViewById(R.id.set_wallpaper);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        CategoryShowVideoActivity.super.onBackPressed();
                    }
                });
            }
        });

        imageUrl = getIntent().getStringExtra("imageUrl1");


        Glide.with(CategoryShowVideoActivity.this).asGif().load(imageUrl).into(gifImageView);

        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("LiveWallpaper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("live_wallpaper1", imageUrl);
                editor.apply();
                Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(CategoryShowVideoActivity.this, GIFWallpaperService.class));
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                CategoryShowVideoActivity.super.onBackPressed();
            }
        });
    }
}

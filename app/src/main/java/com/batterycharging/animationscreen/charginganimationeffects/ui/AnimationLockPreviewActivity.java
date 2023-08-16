package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.bumptech.glide.Glide;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.utils.SharedPreferencesUtil;

import pl.droidsonroids.gif.GifImageView;

public class AnimationLockPreviewActivity extends AppCompatActivity {


    LinearLayout btnApply;
    LinearLayout btnCancel;
    int gifSelected;
    ImageView back;
    SharedPreferencesUtil sharedPreferencesUtil;
    private String imageUrl;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_animation_lock_preview);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        this.gifSelected = getIntent().getIntExtra("animationName", 0);
        this.sharedPreferencesUtil = new SharedPreferencesUtil(this);
        this.btnCancel = (LinearLayout) findViewById(R.id.btnCancel);
        this.btnApply = (LinearLayout) findViewById(R.id.btnApply);
        this.back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        AnimationLockPreviewActivity.super.onBackPressed();
                    }
                });
            }
        });

        GifImageView gifImageView = (GifImageView) findViewById(R.id.ivPreview);
        this.imageUrl = getIntent().getStringExtra("imageUrlCharge");
        Glide.with(AnimationLockPreviewActivity.this)
                .asGif()
                .load(imageUrl)
                .into(gifImageView);



        this.btnApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    AnimationLockPreviewActivity.this.sharedPreferencesUtil.isVideo(false);
                    AnimationLockPreviewActivity.this.sharedPreferencesUtil.setAnimationName(AnimationLockPreviewActivity.this.gifSelected);
                    SharedPreferences.Editor editor = getSharedPreferences("MyOtherChargePreferences", Context.MODE_PRIVATE).edit();
                    editor.putString("imageUrlChargeOther", AnimationLockPreviewActivity.this.imageUrl);
                    editor.apply();

                    AnimationLockPreviewActivity.this.finish();
                });
            }
        });
        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AnimationLockPreviewActivity.this.finish();
            }
        });


    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                AnimationLockPreviewActivity.super.onBackPressed();
            }
        });
    }
}

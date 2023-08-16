package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;
import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.MyApplication.PrivacyLink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                PrivacyPolicyActivity.super.onBackPressed();
            }
        });
    }
    

    @Override
    protected void onResume() {
        super.onResume();

        binding.btnPrivacy.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(PrivacyLink));
            startActivity(intent);
        });
        binding.btnTmc.setOnClickListener(view -> {
            startActivity(new Intent(PrivacyPolicyActivity.this,TermsConditionsActivity.class));
        });

        binding.btnAgree.setOnClickListener(view -> {
            startActivity(new Intent(PrivacyPolicyActivity.this,DashboardActivity.class));
        });

    }

}

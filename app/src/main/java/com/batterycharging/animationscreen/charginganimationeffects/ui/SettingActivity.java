package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.utils.SharedPreferencesUtil;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String[] dateFormats = {"EEEE, MMMM dd, yyyy", "EEE, MMMM dd, yyyy", "EEEE, dd MMMM", "dd MMMM yyyy"};
    ImageView ivAnalog;
    ImageView ivDigital;
    ImageView ivPos0;
    ImageView ivPos1,back;
    ImageView ivPos2;
    ImageView ivPos3;
    LinearLayout llAnalog;
    LinearLayout llDigital;
    LinearLayout llPos0;
    LinearLayout llPos1;
    LinearLayout llPos2;
    LinearLayout llPos3;
    SharedPreferencesUtil sharedPreferencesUtil;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_setting);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.large).findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        this.sharedPreferencesUtil = new SharedPreferencesUtil(this);
        this.llAnalog = (LinearLayout) findViewById(R.id.llAnalog);
        this.llDigital = (LinearLayout) findViewById(R.id.llDigital);
        this.ivAnalog = (ImageView) findViewById(R.id.ivAnalog);
        this.ivDigital = (ImageView) findViewById(R.id.ivDigital);
        this.ivPos0 = (ImageView) findViewById(R.id.ivPos0);
        this.ivPos1 = (ImageView) findViewById(R.id.ivPos1);
        this.ivPos2 = (ImageView) findViewById(R.id.ivPos2);
        this.ivPos3 = (ImageView) findViewById(R.id.ivPos3);
        this.llPos0 = (LinearLayout) findViewById(R.id.llPos0);
        this.llPos1 = (LinearLayout) findViewById(R.id.llPos1);
        this.llPos2 = (LinearLayout) findViewById(R.id.llPos2);
        this.llPos3 = (LinearLayout) findViewById(R.id.llPos3);
        this.back = (ImageView) findViewById(R.id.back);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        spinner.setOnItemSelectedListener(this);
        ArrayList arrayList = new ArrayList();
        arrayList.add("10 Second");
        arrayList.add("30 Second");
        arrayList.add("1 Minute");
        arrayList.add("No Timeout");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, arrayList);
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(arrayAdapter);
        if (this.sharedPreferencesUtil.getScreenTimeOut() != null) {
            spinner.setSelection(arrayAdapter.getPosition(this.sharedPreferencesUtil.getScreenTimeOut()));
        }
        setSelection(this.sharedPreferencesUtil.getDateStyle());
        if (this.sharedPreferencesUtil.getClockStyle() == 1) {
            this.ivAnalog.setImageResource(R.drawable.ic_round);
            this.ivDigital.setImageResource(R.drawable.ic_done_round);
        } else {
            this.ivAnalog.setImageResource(R.drawable.ic_done_round);
            this.ivDigital.setImageResource(R.drawable.ic_round);
        }
        this.llAnalog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.ivAnalog.setImageResource(R.drawable.ic_done_round);
                SettingActivity.this.ivDigital.setImageResource(R.drawable.ic_round);
                SettingActivity.this.sharedPreferencesUtil.setClockStyle(0);
            }
        });
        this.llDigital.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.ivAnalog.setImageResource(R.drawable.ic_round);
                SettingActivity.this.ivDigital.setImageResource(R.drawable.ic_done_round);
                SettingActivity.this.sharedPreferencesUtil.setClockStyle(1);
            }
        });
        this.llPos0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.setSelection(0);
                SettingActivity.this.sharedPreferencesUtil.setDateStyle(0);
            }
        });
        this.llPos1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.setSelection(1);
                SettingActivity.this.sharedPreferencesUtil.setDateStyle(1);
            }
        });
        this.llPos2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.setSelection(2);
                SettingActivity.this.sharedPreferencesUtil.setDateStyle(2);
            }
        });
        this.llPos3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SettingActivity.this.setSelection(3);
                SettingActivity.this.sharedPreferencesUtil.setDateStyle(3);
            }
        });
        this.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        SettingActivity.super.onBackPressed();
                        finish();
                    }
                });
            }
        });

    }


    public void setSelection(int i) {
        if (i == 0) {
            this.ivPos0.setImageResource(R.drawable.ic_done_round);
            this.ivPos1.setImageResource(R.drawable.ic_round);
            this.ivPos2.setImageResource(R.drawable.ic_round);
            this.ivPos3.setImageResource(R.drawable.ic_round);
        } else if (i == 1) {
            this.ivPos0.setImageResource(R.drawable.ic_round);
            this.ivPos1.setImageResource(R.drawable.ic_done_round);
            this.ivPos2.setImageResource(R.drawable.ic_round);
            this.ivPos3.setImageResource(R.drawable.ic_round);
        } else if (i == 2) {
            this.ivPos0.setImageResource(R.drawable.ic_round);
            this.ivPos1.setImageResource(R.drawable.ic_round);
            this.ivPos2.setImageResource(R.drawable.ic_done_round);
            this.ivPos3.setImageResource(R.drawable.ic_round);
        } else if (i == 3) {
            this.ivPos0.setImageResource(R.drawable.ic_round);
            this.ivPos1.setImageResource(R.drawable.ic_round);
            this.ivPos2.setImageResource(R.drawable.ic_round);
            this.ivPos3.setImageResource(R.drawable.ic_done_round);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(-1);
        this.sharedPreferencesUtil.setScreenTimeout(adapterView.getItemAtPosition(i).toString());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                SettingActivity.super.onBackPressed();
                finish();
            }
        });

    }
}

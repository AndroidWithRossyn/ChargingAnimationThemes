package com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses;


import static com.adsmodule.api.AdsModule.Retrofit.APICallHandler.callAppCountApi;
import static com.adsmodule.api.AdsModule.Utils.Constants.MAIN_BASE_URL;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.adsmodule.api.AdsModule.PreferencesManager.AppPreferences;
import com.adsmodule.api.AdsModule.Retrofit.AdsDataRequestModel;
import com.adsmodule.api.AdsModule.Utils.ConnectionDetector;
import com.adsmodule.api.AdsModule.Utils.Global;

public class MyApplication extends Application {

    private static MyApplication app;
    private static ConnectionDetector cd;
    static AppPreferences preferences;
    public static final String PrivacyLink = "https://neelbrave.blogspot.com/p/privacy-policy.html";

    public static AppPreferences getPreferences() {
        if (preferences == null)
            preferences = new AppPreferences(app);
        return preferences;
    }
    public static Context context1;


    public static ConnectionDetector getConnectionStatus(){
        if (cd == null){
            cd=new ConnectionDetector(app);
        }
        return cd;
    }



    public static synchronized MyApplication getInstance() {
        return app;
    }





    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context1 = getApplicationContext();
        AppPreferences preferences = new AppPreferences(app);
        if (preferences.isFirstRun()) {
            callAppCountApi(MAIN_BASE_URL, new AdsDataRequestModel(app.getPackageName(), Global.getDeviceId(app)), () -> {
                preferences.setFirstRun(false);
            });
        }

        new AppOpenAds(app);
        createNotificationChannel();

    }
    public static final String CHANNEL_ID = "SMART_ALARM_SERVICE_CHANNEL";
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Charging Animation & Themes", NotificationManager.IMPORTANCE_HIGH));
        }
    }
    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }





}
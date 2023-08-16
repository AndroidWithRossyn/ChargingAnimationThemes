package com.batterycharging.animationscreen.charginganimationeffects.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.MyApplication;
import com.batterycharging.animationscreen.charginganimationeffects.ui.BatteryChargingAnimationActivity;
import com.batterycharging.animationscreen.charginganimationeffects.ui.DashboardActivity;

public class BatteryService extends Service {
    public final IBinder iBinder = new LocalBinder();
    PowerConnectionReceiver receiver;

    public int onStartCommand(Intent intent, int i, int i2) {
        return Service.START_STICKY;
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public BatteryService getService() {
            return BatteryService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.iBinder;
    }

    public void onCreate() {
        super.onCreate();
        try {
            showNotification(getApplicationContext());
            this.receiver = new PowerConnectionReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
            registerReceiver(this.receiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        try {
            unregisterReceiver(this.receiver);
        } catch (Exception unused) {
        }
        super.onDestroy();
    }

    public Notification showNotification(Context context) {
        int i = 33554432;
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, DashboardActivity.class), Build.VERSION.SDK_INT >= 31 ? 33554432 : 1073741824);
        Intent intent = new Intent(this, BatteryChargingAnimationActivity.class);
        intent.setFlags(67108864);
        if (Build.VERSION.SDK_INT < 31) {
            i = 1073741824;
        }
        PendingIntent activity2 = PendingIntent.getActivity(this, 0, intent, i);
        if (Build.VERSION.SDK_INT >= 26) {
            startForeground(2, new NotificationCompat.Builder((Context) this, MyApplication.CHANNEL_ID).setContentTitle("Charging Battery Animation").setPriority(1).setContentText("Service Started").setSmallIcon((int) R.drawable.ic_battery__info_page_icon).setFullScreenIntent(activity2, true).setContentIntent(activity).setCategory(NotificationCompat.CATEGORY_ALARM).build());
        } else {
            ((NotificationManager) getSystemService("notification")).notify(2, new NotificationCompat.Builder(this).setContentTitle("Charging Battery Animation").setPriority(1).setContentText("Service Started").setSmallIcon((int) R.drawable.ic_battery__info_page_icon).setContentIntent(activity).setCategory(NotificationCompat.CATEGORY_ALARM).setFullScreenIntent(activity2, true).build());
        }


        if (Build.VERSION.SDK_INT >= 26) {
            return new NotificationCompat.Builder((Context) this, MyApplication.CHANNEL_ID)
                    .setContentTitle("Charging Battery Animation")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentText("Service Started")
                    .setSmallIcon((int) R.drawable.ic_battery__info_page_icon)
                    .setFullScreenIntent(activity2, true)
                    .setContentIntent(activity)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .build();
        } else {
            return new NotificationCompat.Builder(this)
                    .setContentTitle("Charging Battery Animation")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentText("Service Started")
                    .setSmallIcon((int) R.drawable.ic_battery__info_page_icon)
                    .setContentIntent(activity)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setFullScreenIntent(activity2, true)
                    .build();
        }
    }

    public class PowerConnectionReceiver extends BroadcastReceiver {
        public PowerConnectionReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                Intent intent2 = new Intent(context, BatteryChargingAnimationActivity.class);
                intent2.setFlags(67108864);
                intent2.addFlags(268435456);
                context.startActivity(intent2);
                return;
            }
            intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED");
        }
    }
}

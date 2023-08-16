package com.batterycharging.animationscreen.charginganimationeffects.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;

public class BatteryFragment extends Fragment {
    IntentFilter intentfilter;
    TextView temprature_degree,voltage_degree,battery_degree,technology_degree,capacitor_degree,charge_type_degree;
    float batteryTemp,fullVoltage;
    int batteryVol,deviceHealth;
    private BatteryManager batteryManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battery, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        temprature_degree = view.findViewById(R.id.temprature_degree);
        voltage_degree = view.findViewById(R.id.voltage_degree);
        battery_degree = view.findViewById(R.id.battery_degree);
        capacitor_degree = view.findViewById(R.id.capacitor_degree);
        technology_degree = view.findViewById(R.id.technology_degree);
        charge_type_degree = view.findViewById(R.id.charge_type_degree);

        //Battery Capacity
        if (requireActivity() != null) {
            batteryManager = (BatteryManager) requireActivity().getSystemService(Context.BATTERY_SERVICE);
        }
        long cu =  batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        capacitor_degree.setText(String.valueOf(cu) + " mA");

        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        requireActivity().registerReceiver(broadcastreceiver, intentfilter);
        return view;
    }


    private final BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //temprature
            batteryTemp = (float) (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
            temprature_degree.setText(batteryTemp + " " + (char) 0x00B0 + "C");

            //voltage
            batteryVol = (int)(intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0));
            fullVoltage = (float) (batteryVol * 0.001);
            voltage_degree.setText(fullVoltage+" V");

            //battery health
            deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            if(deviceHealth == BatteryManager.BATTERY_HEALTH_COLD){
                battery_degree.setText("Cold");
            }
            if(deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD){
                battery_degree.setText("Dead");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD){
                battery_degree.setText("Good");
            }
            if(deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                battery_degree.setText("OverHeat");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                battery_degree.setText("Over voltage");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN){
                battery_degree.setText("Unknown");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                battery_degree.setText("Unspecified Failure");
            }

            //Technology
            boolean isPresent = intent.getBooleanExtra("present", false);
            String technology = intent.getStringExtra("technology");
            int status = intent.getIntExtra("status", 0);
            int scale = intent.getIntExtra("scale", -1);
            int rawlevel = intent.getIntExtra("level", -1);
            int level = 0;

            Bundle bundle = intent.getExtras();

            Log.i("BatteryLevel", bundle.toString());

            if (isPresent) {
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }

                String info = "";
                info += (technology + "\n\n");

                setBatteryLevelText(info + "\n\n");
            } else {
                setBatteryLevelText("Battery not present!!!");
            }

            //Charge Type
            if (isPresent) {
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }

                String info1 = "";
                info1 += (getStatusString(status) + "\n\n");

                setBatteryLevelText1(info1 + "\n\n");
            } else {
                setBatteryLevelText1("Battery not present!!!");
            }


        }
    };
    private String getStatusString(int status) {
        String statusString = "Unknown";

        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusString = "Charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusString = "Discharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusString = "Full";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusString = "Not Charging";
                break;
        }

        return statusString;
    }

    private void setBatteryLevelText1(String text) {
        charge_type_degree.setText(text);
    }

    private void setBatteryLevelText(String text) {
        technology_degree.setText(text);
    }


}
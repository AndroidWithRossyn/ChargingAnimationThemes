package com.batterycharging.animationscreen.charginganimationeffects.fragment;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.PointerIconCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.LinearLiveChargeAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.databinding.FragmentHomeBinding;
import com.batterycharging.animationscreen.charginganimationeffects.databinding.ShowDialogBinding;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import com.batterycharging.animationscreen.charginganimationeffects.service.BatteryService;
import com.batterycharging.animationscreen.charginganimationeffects.ui.AllCategoriesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private static final int REQUEST_OVERLAY_PERMISSION = 123;
    FragmentHomeBinding binding;
    private LinearLiveChargeAdapter newAdapter, popularAdapter, facesAdapter, batteryAdapter, abstractAdapter, funnyAdapter;
    private List<Wallpaper> categoryIdentifier, newList, popularList, facesList, batteryList, abstractList, funnyList;

    public static boolean isMyServiceRunning(Class<?> cls, Context context) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), binding.large1, 1, null);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), binding.large2, 1, null);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), binding.large3, 1, null);

        Map<String, Object> defaultValues = new HashMap<>();
        defaultValues.put("wallpaper_parameter", "new");
        binding.newSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    categoryIdentifier.add(new Wallpaper("New"));
                    Intent intent = new Intent(getActivity(), AllCategoriesActivity.class);
                    intent.putParcelableArrayListExtra("categoryIdentifier", (ArrayList<? extends Parcelable>) categoryIdentifier);
                    startActivity(intent);
                });


            }
        });
        binding.popularSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    categoryIdentifier.add(new Wallpaper("Popular"));
                    Intent intent = new Intent(getActivity(), AllCategoriesActivity.class);
                    intent.putParcelableArrayListExtra("categoryIdentifier", (ArrayList<? extends Parcelable>) categoryIdentifier);
                    startActivity(intent);
                });

            }
        });
        binding.facesSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    categoryIdentifier.add(new Wallpaper("Face"));
                    Intent intent = new Intent(getActivity(), AllCategoriesActivity.class);
                    intent.putParcelableArrayListExtra("categoryIdentifier", (ArrayList<? extends Parcelable>) categoryIdentifier);
                    startActivity(intent);
                });

            }
        });
        binding.batterySee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    categoryIdentifier.add(new Wallpaper("Battery"));
                    Intent intent = new Intent(getActivity(), AllCategoriesActivity.class);
                    intent.putParcelableArrayListExtra("categoryIdentifier", (ArrayList<? extends Parcelable>) categoryIdentifier);
                    startActivity(intent);
                });

            }
        });
        binding.abstractSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    categoryIdentifier.add(new Wallpaper("Abstract"));
                    Intent intent = new Intent(getActivity(), AllCategoriesActivity.class);
                    intent.putParcelableArrayListExtra("categoryIdentifier", (ArrayList<? extends Parcelable>) categoryIdentifier);
                    startActivity(intent);
                });

            }
        });
        binding.funnySee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    categoryIdentifier.add(new Wallpaper("Funny"));
                    Intent intent = new Intent(getActivity(), AllCategoriesActivity.class);
                    intent.putParcelableArrayListExtra("categoryIdentifier", (ArrayList<? extends Parcelable>) categoryIdentifier);
                    startActivity(intent);
                });

            }
        });

        binding.newRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.newRecycler.setHasFixedSize(true);
        binding.newRecycler.setNestedScrollingEnabled(false);


        binding.popularRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.popularRecycler.setHasFixedSize(true);
        binding.popularRecycler.setNestedScrollingEnabled(false);


        binding.facesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.facesRecycler.setHasFixedSize(true);
        binding.facesRecycler.setNestedScrollingEnabled(false);


        binding.batteryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.batteryRecycler.setHasFixedSize(true);
        binding.batteryRecycler.setNestedScrollingEnabled(false);


        binding.abstractRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.abstractRecycler.setHasFixedSize(true);
        binding.abstractRecycler.setNestedScrollingEnabled(false);


        binding.funnyRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.funnyRecycler.setHasFixedSize(true);
        binding.funnyRecycler.setNestedScrollingEnabled(false);

        categoryIdentifier = new ArrayList<>();
        newList = new ArrayList<>();
        popularList = new ArrayList<>();
        facesList = new ArrayList<>();
        batteryList = new ArrayList<>();
        abstractList = new ArrayList<>();
        funnyList = new ArrayList<>();


        newAdapter = new LinearLiveChargeAdapter(getActivity(), (ArrayList<Wallpaper>) newList);
        popularAdapter = new LinearLiveChargeAdapter(requireActivity(), (ArrayList<Wallpaper>) popularList);
        facesAdapter = new LinearLiveChargeAdapter(requireActivity(), (ArrayList<Wallpaper>) facesList);
        batteryAdapter = new LinearLiveChargeAdapter(requireActivity(), (ArrayList<Wallpaper>) batteryList);
        abstractAdapter = new LinearLiveChargeAdapter(requireActivity(), (ArrayList<Wallpaper>) abstractList);
        funnyAdapter = new LinearLiveChargeAdapter(requireActivity(), (ArrayList<Wallpaper>) funnyList);

        binding.newRecycler.setAdapter(newAdapter);
        binding.popularRecycler.setAdapter(popularAdapter);
        binding.facesRecycler.setAdapter(facesAdapter);
        binding.batteryRecycler.setAdapter(batteryAdapter);
        binding.abstractRecycler.setAdapter(abstractAdapter);
        binding.funnyRecycler.setAdapter(funnyAdapter);
        try {
            InputStream inputStream = requireActivity().getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");

            processCategory(categoriesObject, "New", (ArrayList<Wallpaper>) newList);
            processCategory(categoriesObject, "Popular", (ArrayList<Wallpaper>) popularList);
            processCategory(categoriesObject, "Face", (ArrayList<Wallpaper>) facesList);
            processCategory(categoriesObject, "Battery", (ArrayList<Wallpaper>) batteryList);
            processCategory(categoriesObject, "Abstract", (ArrayList<Wallpaper>) abstractList);
            processCategory(categoriesObject, "Funny", (ArrayList<Wallpaper>) funnyList);

            newAdapter.notifyDataSetChanged();
            popularAdapter.notifyDataSetChanged();
            facesAdapter.notifyDataSetChanged();
            batteryAdapter.notifyDataSetChanged();
            abstractAdapter.notifyDataSetChanged();
            funnyAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if (isMyServiceRunning(BatteryService.class, requireActivity())) {
            binding.serviceSwitch.setChecked(true);
        } else {
            binding.serviceSwitch.setChecked(true);
            Intent intent = new Intent(requireActivity(), BatteryService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                requireActivity().startForegroundService(intent);
            } else {
                requireActivity().startService(intent);
            }
        }
        binding.serviceSwitch.setOnCheckedChangeListener((compoundButton, z) -> {
            if (z) {
                if (!HomeFragment.isMyServiceRunning(BatteryService.class, requireActivity())) {
                    Intent intent = new Intent(requireActivity(), BatteryService.class);
                    if (Build.VERSION.SDK_INT >= 26) {
                        requireActivity().startForegroundService(intent);
                    } else {
                        requireActivity().startService(intent);
                    }
                }
            } else if (HomeFragment.isMyServiceRunning(BatteryService.class, requireActivity())) {
                requireActivity().stopService(new Intent(requireActivity(), BatteryService.class));
            }
        });
//        if (!Settings.canDrawOverlays(requireActivity())) {
        showDialog();
//        }

        return binding.getRoot();
    }

    private void processCategory(JSONObject categoriesObject, String categoryName, ArrayList<Wallpaper> categoryList) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONObject categoryObject = categoriesObject.getJSONObject(categoryName);
            JSONArray urlsArray = categoryObject.getJSONArray("urls");
            categoryList.clear();
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Wallpaper wallpaper = new Wallpaper();
                wallpaper.setUrl(url);
                categoryList.add(wallpaper);
            }
        }
    }

    public void showDialog() {
        if (!Settings.canDrawOverlays(requireContext())) {
            // Show the dialog only if the overlay permission is not granted
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            ShowDialogBinding bind = ShowDialogBinding.inflate(LayoutInflater.from(requireActivity()));
            builder.setView(bind.getRoot());
            Dialog dialog = builder.create();
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            bind.btnOk.setOnClickListener(view -> {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + requireActivity().getPackageName()));
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
                dialog.dismiss();
//                new Handler().postDelayed(this::requestOverlayPermission, 2000);
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        HomeFragment fragment = (HomeFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.container);
//        if (fragment != null) {
//            fragment.handleOverlayPermissionResult(requestCode, resultCode);
//        }

    }
}

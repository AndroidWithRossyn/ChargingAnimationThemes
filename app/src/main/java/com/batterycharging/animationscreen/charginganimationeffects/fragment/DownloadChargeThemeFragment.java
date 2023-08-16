package com.batterycharging.animationscreen.charginganimationeffects.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.FavouritesLiveAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.LiveChargeMainAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DownloadChargeThemeFragment extends Fragment {
    private RecyclerView download_charge_recycler;
    private FavouritesLiveAdapter downloadLiveWallpaperAdapter;
    private ArrayList<Wallpaper> downloadDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_download_charge_theme, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        download_charge_recycler = view.findViewById(R.id.download_charge_recycler);
        downloadDataList = loadDownloadDataList();
        downloadLiveWallpaperAdapter = new FavouritesLiveAdapter(getContext(), downloadDataList);
        download_charge_recycler.setAdapter(downloadLiveWallpaperAdapter);

        return view;
    }

    private ArrayList<Wallpaper> loadDownloadDataList() {
        ArrayList<Wallpaper> downloadDataList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(LiveChargeMainAdapter.DOWNLOADS_PREF_NAME_LIVE_CHARGE, Context.MODE_PRIVATE);
        Set<String> downloadSet = sharedPreferences.getStringSet(LiveChargeMainAdapter.DOWNLOADS_PREF_NAME_LIVE_CHARGE, new HashSet<>());
        for (String imageUrl : downloadSet) {
            Wallpaper wallpaper = new Wallpaper(imageUrl);
            downloadDataList.add(wallpaper);
        }

        return downloadDataList;
    }
}
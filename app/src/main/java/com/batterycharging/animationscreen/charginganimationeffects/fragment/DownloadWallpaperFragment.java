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
import com.batterycharging.animationscreen.charginganimationeffects.adapter.FavouritesAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.WallpaperAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DownloadWallpaperFragment extends Fragment {

    private RecyclerView download_wallpaper_recycler;
    private FavouritesAdapter downloadWallpaperAdapter;
    private ArrayList<Wallpaper> downloadDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_wallpaper, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        download_wallpaper_recycler = view.findViewById(R.id.download_wallpaper_recycler);
        downloadDataList = loadDownloadDataList();
        downloadWallpaperAdapter = new FavouritesAdapter(getContext(), downloadDataList);
        download_wallpaper_recycler.setAdapter(downloadWallpaperAdapter);

        return view;
    }

    private ArrayList<Wallpaper> loadDownloadDataList() {
        ArrayList<Wallpaper> downloadDataList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(WallpaperAdapter.DOWNLOADS_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> downloadSet = sharedPreferences.getStringSet(WallpaperAdapter.DOWNLOADS_PREF_NAME, new HashSet<>());
        for (String imageUrl : downloadSet) {
            Wallpaper wallpaper = new Wallpaper(imageUrl);
            downloadDataList.add(wallpaper);
        }

        return downloadDataList;
    }
}
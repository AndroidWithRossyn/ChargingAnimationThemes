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
import com.batterycharging.animationscreen.charginganimationeffects.adapter.LiveWallpaperAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LiveThemeFavFragment extends Fragment {
    private RecyclerView favorite_live_recyclerview;
    private FavouritesLiveAdapter favouritesAdapter;
    private ArrayList<Wallpaper> favoriteDataList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_live_theme_fav, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        favorite_live_recyclerview=view.findViewById(R.id.favorite_live_recyclerview);
        favoriteDataList = loadFavoriteDataList();
        favouritesAdapter = new FavouritesLiveAdapter(getContext(), favoriteDataList);
        favorite_live_recyclerview.setAdapter(favouritesAdapter);

        return view;
    }

    private ArrayList<Wallpaper> loadFavoriteDataList() {
        ArrayList<Wallpaper> favoriteList = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(LiveWallpaperAdapter.FAVORITES_PREF_NAME_LIVE, Context.MODE_PRIVATE);
        Set<String> favoritesSet = sharedPreferences.getStringSet(LiveWallpaperAdapter.FAVORITES_PREF_NAME_LIVE, new HashSet<>());
        for (String imageUrl : favoritesSet) {
            Wallpaper wallpaper = new Wallpaper(imageUrl);
            favoriteList.add(wallpaper);
        }

        return favoriteList;
    }
}
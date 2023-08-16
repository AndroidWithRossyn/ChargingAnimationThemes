package com.batterycharging.animationscreen.charginganimationeffects.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.LiveWallpaperAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class LivethemeWallpaperFragment extends Fragment {

    private RecyclerView wallpaperRecyclerView;
    private LiveWallpaperAdapter livewallpaperAdapter;
    private List<Wallpaper> wallpaperList;

    public LivethemeWallpaperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_livetheme_wallpaper, container, false);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(), view.findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        wallpaperRecyclerView = view.findViewById(R.id.live_wallpaper_recycler);
        wallpaperList = new ArrayList<>();
        livewallpaperAdapter = new LiveWallpaperAdapter(requireActivity(), (ArrayList<Wallpaper>) wallpaperList);

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 3);
        wallpaperRecyclerView.setLayoutManager(layoutManager);
        wallpaperRecyclerView.setHasFixedSize(true);
        wallpaperRecyclerView.setNestedScrollingEnabled(false);
        wallpaperRecyclerView.setAdapter(livewallpaperAdapter);

        fetchWallpapers();

        return view;
    }
    private void fetchWallpapers() {
        try {
            InputStream inputStream = requireActivity().getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");
            processCategory(categoriesObject, "LiveWallpaper");

            livewallpaperAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void processCategory(JSONObject categoriesObject, String categoryName) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONArray urlsArray = categoriesObject.getJSONObject(categoryName).getJSONArray("urls");
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Wallpaper wallpaper = new Wallpaper();
                wallpaper.setUrl(url);
                wallpaperList.add(wallpaper);
            }
        }
    }
}

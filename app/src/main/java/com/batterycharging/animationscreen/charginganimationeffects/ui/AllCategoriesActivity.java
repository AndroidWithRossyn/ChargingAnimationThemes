package com.batterycharging.animationscreen.charginganimationeffects.ui;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.adapter.LiveChargeMainAdapter;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AllCategoriesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static final String TAG = "AllCategoriesActivity";
    private Timer fetchTimer;
    LiveChargeMainAdapter newAdapter = null;
    LiveChargeMainAdapter popularAdapter = null;
    LiveChargeMainAdapter facesAdapter = null;
    LiveChargeMainAdapter batteryAdapter = null;
    LiveChargeMainAdapter abstractAdapter = null;
    LiveChargeMainAdapter funnyAdapter = null;

    private ArrayList<Wallpaper> newList = new ArrayList<>();
    private ArrayList<Wallpaper> popularList = new ArrayList<>();
    private ArrayList<Wallpaper> faceslist = new ArrayList<>();
    private ArrayList<Wallpaper> batteryList = new ArrayList<>();
    private ArrayList<Wallpaper> abstractList = new ArrayList<>();
    private ArrayList<Wallpaper> funnyList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        recyclerView = findViewById(R.id.neon_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            InputStream inputStream = getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");

            // Process each category
            processCategory(categoriesObject, "New", newList);
            processCategory(categoriesObject, "Popular", popularList);
            processCategory(categoriesObject, "Face", faceslist);
            processCategory(categoriesObject, "Battery", batteryList);
            processCategory(categoriesObject, "Abstract", abstractList);
            processCategory(categoriesObject, "Funny", funnyList);

            // Notify adapter of data changes
            ArrayList<Wallpaper> categoryIdentifier = getIntent().getParcelableArrayListExtra("categoryIdentifier");
            if (categoryIdentifier != null) {
                for (Wallpaper image : categoryIdentifier) {
                    String category1 = image.getUrl();

                    switch (category1) {
                        case "New":
                            newAdapter = new LiveChargeMainAdapter(AllCategoriesActivity.this, newList);
                            recyclerView.setAdapter(newAdapter);
                            break;
                        case "Popular":
                            popularAdapter = new LiveChargeMainAdapter(AllCategoriesActivity.this, popularList);
                            recyclerView.setAdapter(popularAdapter);
                            break;
                        case "Face":
                            facesAdapter = new LiveChargeMainAdapter(AllCategoriesActivity.this, faceslist);
                            recyclerView.setAdapter(facesAdapter);
                            break;
                        case "Battery":
                            batteryAdapter = new LiveChargeMainAdapter(AllCategoriesActivity.this, batteryList);
                            recyclerView.setAdapter(batteryAdapter);
                            break;
                        case "Abstract":
                            abstractAdapter = new LiveChargeMainAdapter(AllCategoriesActivity.this, abstractList);
                            recyclerView.setAdapter(abstractAdapter);
                            break;
                        case "Funny":
                            funnyAdapter = new LiveChargeMainAdapter(AllCategoriesActivity.this, funnyList);
                            recyclerView.setAdapter(funnyAdapter);
                            break;
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        fetchTimer = new Timer();
        fetchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 0, 1000);
    }



    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                AllCategoriesActivity.super.onBackPressed();
            }
        });
    }



    private void processCategory(JSONObject categoriesObject, String categoryName, ArrayList<Wallpaper> categoryList) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONObject categoryObject = categoriesObject.getJSONObject(categoryName);
            JSONArray urlsArray = categoryObject.getJSONArray("urls");
            categoryList.clear();
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Wallpaper images = new Wallpaper();
                images.setUrl(url);
                categoryList.add(images);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (fetchTimer != null) {
            fetchTimer.cancel();
        }
    }
}

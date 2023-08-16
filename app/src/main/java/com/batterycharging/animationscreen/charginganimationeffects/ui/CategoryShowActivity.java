package com.batterycharging.animationscreen.charginganimationeffects.ui;
import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class CategoryShowActivity extends AppCompatActivity {
    ImageView imageView,back;
    Bitmap photoBitmap;
    TextView setWallpaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        Intent intent = getIntent();

        imageView = findViewById(R.id.imageView);
        setWallpaper = findViewById(R.id.set_wallpaper);
        back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        CategoryShowActivity.super.onBackPressed();
                    }
                });
            }
        });
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Picasso.get()
                .load(imageUrl)
                .into(imageView);

        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String[] options = {"Home screen", "Lock screen", "Both"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(CategoryShowActivity.this);
                    builder.setTitle("SET WALLPAPER AS");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new SetWallpaperTask().execute(which + 1);
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    new SetWallpaperTask().execute(0);
                    finish();
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class SetWallpaperTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (integers[0] == 1) {
                    try {
                        manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (integers[0] == 2) {
                    try {
                        manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_LOCK);//For Lock screen
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (integers[0] == 0) {
                    try {
                        manager.setBitmap(photoBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}

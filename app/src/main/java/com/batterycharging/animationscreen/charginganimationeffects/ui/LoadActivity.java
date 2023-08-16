package com.batterycharging.animationscreen.charginganimationeffects.ui;
import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import java.io.IOException;

public class LoadActivity extends AppCompatActivity {
    private ImageView imageView,back;
    private TextView setWallpaperTextView;
    private Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), findViewById(R.id.small).findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        imageView = findViewById(R.id.imageView);
        back = findViewById(R.id.back);
        setWallpaperTextView = findViewById(R.id.set_wallpaper);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        LoadActivity.super.onBackPressed();
                    }
                });
            }
        });
        String imageUriString = getIntent().getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageUriString);

        Glide.with(this)
                .load(imageUri)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(
                            GlideException e,
                            Object model,
                            Target<Drawable> target,
                            boolean isFirstResource
                    ) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(
                            Drawable resource,
                            Object model,
                            Target<Drawable> target,
                            DataSource dataSource,
                            boolean isFirstResource
                    ) {
                        // Extract the Bitmap from the final Drawable after the transition is completed
                        photoBitmap = ((BitmapDrawable) resource).getBitmap();
                        return false;
                    }
                })
                .into(imageView);

        setWallpaperTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photoBitmap == null) {
                    Toast.makeText(LoadActivity.this, "Image not loaded yet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String[] options = {"Home screen", "Lock screen", "Both"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoadActivity.this);
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

    private class SetWallpaperTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(photoBitmap, screenWidth, screenHeight, true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (integers[0] == 1) {
                    try {
                        manager.setBitmap(resizedBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (integers[0] == 2) {
                    try {
                        manager.setBitmap(resizedBitmap, null, true, WallpaperManager.FLAG_LOCK);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        manager.setBitmap(resizedBitmap, null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (integers[0] == 0) {
                    try {
                        manager.setBitmap(resizedBitmap);
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

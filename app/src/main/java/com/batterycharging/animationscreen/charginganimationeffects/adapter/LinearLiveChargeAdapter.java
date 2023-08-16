package com.batterycharging.animationscreen.charginganimationeffects.adapter;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import com.batterycharging.animationscreen.charginganimationeffects.ui.AnimationLockPreviewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import pl.droidsonroids.gif.GifImageView;

public class LinearLiveChargeAdapter extends RecyclerView.Adapter<LinearLiveChargeAdapter.ViewHolder> {
    public static final String TAG = "LinearLiveChargeAdapter";
    private Context context;
    private ArrayList<Wallpaper> arrayList;
    private Set<String> favoritesSet,downloadset;
    private SharedPreferences sharedPreferences,sharedPreferences1;
    public static final String FAVORITES_PREF_NAME_LIVE_CHARGE = "my_favorites_theme_charge";
    public static final String DOWNLOADS_PREF_NAME_LIVE_CHARGE = "my_downloads_theme_charge";


    public LinearLiveChargeAdapter(Context context, ArrayList<Wallpaper> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sharedPreferences = context.getSharedPreferences(FAVORITES_PREF_NAME_LIVE_CHARGE, Context.MODE_PRIVATE);
        sharedPreferences1 = context.getSharedPreferences(DOWNLOADS_PREF_NAME_LIVE_CHARGE, Context.MODE_PRIVATE);
        favoritesSet = sharedPreferences.getStringSet(FAVORITES_PREF_NAME_LIVE_CHARGE, new HashSet<>());
        downloadset = sharedPreferences1.getStringSet(DOWNLOADS_PREF_NAME_LIVE_CHARGE, new HashSet<>());
    }

    @NonNull
    @Override
    public LinearLiveChargeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item_linear_video, parent, false);
        return new LinearLiveChargeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearLiveChargeAdapter.ViewHolder holder, int position) {
        Wallpaper wallpaper = arrayList.get(position);
        holder.progressBar.setVisibility(View.VISIBLE); // Show progress bar
        Glide.with(context)
                .asGif()
                .load(wallpaper.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE); // Hide progress bar on load failure
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE); // Hide progress bar on successful load
                        return false;
                    }
                })
                .into(holder.gifimageView);
        holder.gifimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(context, AnimationLockPreviewActivity.class);
                    intent.putExtra("imageUrlCharge", wallpaper.getUrl());
                    intent.putExtra("position", position);
                    intent.putStringArrayListExtra("favoritesSet", new ArrayList<>(favoritesSet));
                    intent.putStringArrayListExtra("downloadsSet", new ArrayList<>(downloadset));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });

            }
        });
        if (favoritesSet.contains(wallpaper.getUrl())) {
            holder.favourites.setColorFilter(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.favourites.setColorFilter(ContextCompat.getColor(context, R.color.gray));
        }

        // Set the downloads icon based on the item's selection status
        if (downloadset.contains(wallpaper.getUrl())) {
            holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.blue));
        } else {
            holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }


        holder.favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritesSet.contains(wallpaper.getUrl())) {
                    favoritesSet.remove(wallpaper.getUrl());
                    holder.favourites.setColorFilter(ContextCompat.getColor(context, R.color.gray));
                } else {
                    favoritesSet.add(wallpaper.getUrl());
                    holder.favourites.setColorFilter(ContextCompat.getColor(context, R.color.red));
                }
                // Save the updated favorites set
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(FAVORITES_PREF_NAME_LIVE_CHARGE, favoritesSet);
                editor.apply();
            }
        });
        holder.downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadset.contains(wallpaper.getUrl())) {
                    downloadset.remove(wallpaper.getUrl());
                    holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.black));
                } else {
                    downloadset.add(wallpaper.getUrl());
                    holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.blue));
                }
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putStringSet(DOWNLOADS_PREF_NAME_LIVE_CHARGE, downloadset);
                editor.apply();
                downloadImageToGallery(wallpaper.getUrl());
            }
        });
    }
    private void downloadImageToGallery(String imageUrl) {
        Glide.with(context)
                .asGif()
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        saveGifToGallery(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveGifToGallery(GifDrawable gifDrawable) {
        ByteBuffer byteBuffer = gifDrawable.getBuffer();
        if (byteBuffer == null) {

            return;
        }

        // Convert ByteBuffer to byte array
        byte[] gifBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(gifBytes);

        // Get the directory to save the GIF
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Live Wallpaper");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Toast.makeText(context, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String fileName = System.currentTimeMillis() + ".gif";
        File gifFile = new File(directory, fileName);

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(gifFile);

            outputStream.write(gifBytes);
            Toast.makeText(context, "GIF saved successfully", Toast.LENGTH_SHORT).show();

            MediaScannerConnection.scanFile(context, new String[]{gifFile.getAbsolutePath()}, new String[]{"image/gif"}, null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to save GIF", Toast.LENGTH_SHORT).show();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size()> 5 ? 5 : arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        GifImageView gifimageView;
        ImageView favourites,downloads;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            gifimageView = itemView.findViewById(R.id.gifimageView);
            favourites = itemView.findViewById(R.id.favourites);
            downloads = itemView.findViewById(R.id.downloads);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}

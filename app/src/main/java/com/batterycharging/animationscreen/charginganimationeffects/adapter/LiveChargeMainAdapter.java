package com.batterycharging.animationscreen.charginganimationeffects.adapter;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.bumptech.glide.request.target.Target;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import com.batterycharging.animationscreen.charginganimationeffects.ui.AnimationLockPreviewActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import pl.droidsonroids.gif.GifImageView;

public class LiveChargeMainAdapter extends RecyclerView.Adapter<LiveChargeMainAdapter.ViewHolder> {
    public static final String TAG = "LiveChargeMainAdapter";
    private Context context;
    private ArrayList<Wallpaper> arrayList;
    private Set<String> favoritesSet,downloadset;
    private SharedPreferences sharedPreferences,sharedPreferences1;
    public static final String FAVORITES_PREF_NAME_LIVE_CHARGE = "my_favorites_theme_charge";
    public static final String DOWNLOADS_PREF_NAME_LIVE_CHARGE = "my_downloads_theme_charge";


    public LiveChargeMainAdapter(Context context, ArrayList<Wallpaper> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sharedPreferences = context.getSharedPreferences(FAVORITES_PREF_NAME_LIVE_CHARGE, Context.MODE_PRIVATE);
        sharedPreferences1 = context.getSharedPreferences(DOWNLOADS_PREF_NAME_LIVE_CHARGE, Context.MODE_PRIVATE);
        favoritesSet = sharedPreferences.getStringSet(FAVORITES_PREF_NAME_LIVE_CHARGE, new HashSet<>());
        downloadset = sharedPreferences1.getStringSet(DOWNLOADS_PREF_NAME_LIVE_CHARGE, new HashSet<>());
    }

    @NonNull
    @Override
    public LiveChargeMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_wallpaper_item, parent, false);
        return new LiveChargeMainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveChargeMainAdapter.ViewHolder holder, int position) {
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
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));

        String fileName = "Image_" + System.currentTimeMillis() + ".gif";
        request.setTitle(fileName);
        request.setDescription("Downloading GIF");

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);


        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == id) {
                    Uri downloadUri = downloadManager.getUriForDownloadedFile(downloadId);
                    if (downloadUri != null) {
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        mediaScanIntent.setData(downloadUri);
                        context.sendBroadcast(mediaScanIntent);
                        Toast.makeText(context, "GIF saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to save GIF", Toast.LENGTH_SHORT).show();
                    }
                }

                context.unregisterReceiver(this);
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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

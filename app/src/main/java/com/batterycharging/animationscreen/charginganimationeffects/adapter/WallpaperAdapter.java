package com.batterycharging.animationscreen.charginganimationeffects.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import com.batterycharging.animationscreen.charginganimationeffects.ui.CategoryShowActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Wallpaper> arrayList;
    private Set<String> favoritesSet, downloadsSet;
    private SharedPreferences sharedPreferences, sharedPreferences1;
    public static final String FAVORITES_PREF_NAME = "my_favorites_theme";
    public static final String DOWNLOADS_PREF_NAME = "my_downloads_theme";
    public void setDownloadsSet(Set<String> downloadsSet) {
        this.downloadsSet = downloadsSet;
        notifyDataSetChanged();
    }
    public WallpaperAdapter(Context context, ArrayList<Wallpaper> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sharedPreferences = context.getSharedPreferences(FAVORITES_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences1 = context.getSharedPreferences(DOWNLOADS_PREF_NAME, Context.MODE_PRIVATE);
        favoritesSet = sharedPreferences.getStringSet(FAVORITES_PREF_NAME, new HashSet<>());
        downloadsSet = sharedPreferences1.getStringSet(DOWNLOADS_PREF_NAME, new HashSet<>());
    }

    @NonNull
    @Override
    public WallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
        return new WallpaperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.ViewHolder holder, int position) {
        Wallpaper wallpaper = arrayList.get(position);

        // Reset the visibility of views
        holder.imageView.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(wallpaper.getUrl())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, "Failed to load image", Toast.LENGTH_SHORT).show();
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        if (favoritesSet.contains(wallpaper.getUrl())) {
            holder.favourites.setColorFilter(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.favourites.setColorFilter(ContextCompat.getColor(context, R.color.gray));
        }
        if (downloadsSet.contains(wallpaper.getUrl())) {
            holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.blue));
        } else {
            holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryShowActivity.class);
                intent.putExtra("imageUrl", wallpaper.getUrl());
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("favoritesSet", new ArrayList<>(favoritesSet));
                intent.putStringArrayListExtra("downloadsSet", new ArrayList<>(downloadsSet));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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
                editor.putStringSet(FAVORITES_PREF_NAME, favoritesSet);
                editor.apply();
            }
        });

        holder.downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadsSet.contains(wallpaper.getUrl())) {
                    downloadsSet.remove(wallpaper.getUrl());
                    holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.black));
                } else {
                    downloadsSet.add(wallpaper.getUrl());
                    holder.downloads.setColorFilter(ContextCompat.getColor(context, R.color.blue));
                }
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putStringSet(DOWNLOADS_PREF_NAME, downloadsSet);
                editor.apply();
                downloadImageToGallery(wallpaper.getUrl());
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        ImageView favourites;
        ImageView downloads;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            favourites = itemView.findViewById(R.id.favourites);
            downloads = itemView.findViewById(R.id.downloads);
        }
    }


    private void downloadImageToGallery(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String fileName = "Image_" + System.currentTimeMillis() + ".jpg";
                        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File imageFile = new File(directory, fileName);

                        try {
                            FileOutputStream outputStream = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();

                            // Add the image to the gallery
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri contentUri = Uri.fromFile(imageFile);
                            mediaScanIntent.setData(contentUri);
                            context.sendBroadcast(mediaScanIntent);

                            Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // Not needed
                    }
                });
    }


}

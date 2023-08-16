package com.batterycharging.animationscreen.charginganimationeffects.adapter;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import com.batterycharging.animationscreen.charginganimationeffects.ui.CategoryShowActivity;

import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private ArrayList<Wallpaper> favoriteDataList;
    private Context context;

    public FavouritesAdapter(Context context, ArrayList<Wallpaper> favoriteDataList) {
        this.context = context;
        this.favoriteDataList = favoriteDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wallpaper wallpaper = favoriteDataList.get(position);

        holder.imageView.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.rl.setVisibility(View.GONE);

        // Load the image using Glide
        Glide.with(context)
                .load(wallpaper.getUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageView.setImageDrawable(resource);
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(context, CategoryShowActivity.class);
                    intent.putExtra("imageUrl", wallpaper.getUrl());
                    intent.putExtra("position", position);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        RelativeLayout rl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            rl = itemView.findViewById(R.id.rl);
        }
    }
}

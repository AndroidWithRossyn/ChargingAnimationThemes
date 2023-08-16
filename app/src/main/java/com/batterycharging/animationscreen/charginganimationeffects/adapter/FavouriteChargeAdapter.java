package com.batterycharging.animationscreen.charginganimationeffects.adapter;

import static com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.batterycharging.animationscreen.charginganimationeffects.R;
import com.batterycharging.animationscreen.charginganimationeffects.model.Wallpaper;
import com.batterycharging.animationscreen.charginganimationeffects.ui.AnimationLockPreviewActivity;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class FavouriteChargeAdapter extends RecyclerView.Adapter<FavouriteChargeAdapter.ViewHolder> {
    private ArrayList<Wallpaper> favoriteDataList;
    private Context context;

    public FavouriteChargeAdapter(Context context, ArrayList<Wallpaper> favoriteDataList) {
        this.context = context;
        this.favoriteDataList = favoriteDataList;
    }

    @NonNull
    @Override
    public FavouriteChargeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_wallpaper_item, parent, false);
        return new FavouriteChargeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteChargeAdapter.ViewHolder holder, int position) {
        Wallpaper wallpaper = favoriteDataList.get(position);

        holder.gifImageView.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.relativeLayout.setVisibility(View.GONE);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .asGif()
                .load(wallpaper.getUrl())
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.gifImageView);

        holder.progressBar.setVisibility(View.GONE);
        holder.gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    Intent intent = new Intent(context, AnimationLockPreviewActivity.class);
                    intent.putExtra("imageUrlCharge", wallpaper.getUrl());
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
        GifImageView gifImageView;
        RelativeLayout relativeLayout;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gifImageView = itemView.findViewById(R.id.gifimageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            relativeLayout = itemView.findViewById(R.id.rl);
        }
    }
}

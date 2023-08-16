package com.batterycharging.animationscreen.charginganimationeffects.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.adsmodule.api.AdsModule.AdUtils
import com.adsmodule.api.AdsModule.Utils.Constants
import com.adsmodule.api.AdsModule.Utils.Global
import com.batterycharging.animationscreen.charginganimationeffects.BuildConfig
import com.batterycharging.animationscreen.charginganimationeffects.R
import com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.AppOpenAds.activity
import com.batterycharging.animationscreen.charginganimationeffects.SingletonClasses.MyApplication
import com.batterycharging.animationscreen.charginganimationeffects.databinding.ActivityDashboardBinding
import com.batterycharging.animationscreen.charginganimationeffects.databinding.DialogExitBinding
import com.batterycharging.animationscreen.charginganimationeffects.fragment.BatteryFragment
import com.batterycharging.animationscreen.charginganimationeffects.fragment.DownloadFragment
import com.batterycharging.animationscreen.charginganimationeffects.fragment.HomeFragment
import com.batterycharging.animationscreen.charginganimationeffects.fragment.WallpaperFragment


class DashboardActivity : BaseActivity() {
    companion object {
        private const val SELECTED_ITEM_ID = "SELECTED_ITEM_ID"
        private const val PICK_IMAGE_REQUEST = 1
        private var selectedImageUri: Uri? = null
        private const val PERMISSION_REQUEST_CODE = 100
        private const val REQUEST_OVERLAY_PERMISSION = 100
    }


    private lateinit var dashboardActivityBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardActivityBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardActivityBinding.root)
        if (Constants.adsResponseModel != null && Constants.adsResponseModel.app_open_ads.adx != null) {
            Global.sout("Ads module not null>>>>>>>>>>>>>>>", "module worked")
        }
        with(dashboardActivityBinding) {
            bottomNavigationView.itemIconTintList = null
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
//                val executorService: ExecutorService = Executors.newSingleThreadExecutor()
//                executorService.execute {
                when (menuItem.itemId) {
                    R.id.homeNav ->

                        loadFragment(HomeFragment())


                    R.id.batteryNav ->
                        AdUtils.showInterstitialAd(
                            Constants.adsResponseModel.interstitial_ads.adx, activity
                        ) { isLoaded: Boolean ->
                            loadFragment(BatteryFragment())
                        }


                    R.id.wallpaperNav ->
                        AdUtils.showInterstitialAd(
                            Constants.adsResponseModel.interstitial_ads.adx, activity
                        ) { isLoaded: Boolean ->
                            loadFragment(WallpaperFragment())
                        }


                    R.id.downloadNav ->
                        AdUtils.showInterstitialAd(
                            Constants.adsResponseModel.interstitial_ads.adx, activity
                        ) { isLoaded: Boolean ->
                            loadFragment(DownloadFragment())
                        }
                }
                true
            }
        }

        dashboardActivityBinding.menuBtn.setOnClickListener {
            dashboardActivityBinding.drawer.openDrawer(Gravity.LEFT)
        }
        dashboardActivityBinding.favouriteBtn.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                startActivity(Intent(this@DashboardActivity, FavouriteActivity::class.java))
            }

        }
        dashboardActivityBinding.homeLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                dashboardActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(HomeFragment(), R.drawable.home_checked)
            }

        }

        dashboardActivityBinding.batteryLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                dashboardActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(BatteryFragment(), R.drawable.battery_checked)
            }


        }

        dashboardActivityBinding.wallpaperLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                dashboardActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(WallpaperFragment(), R.drawable.wallpaper_checked)
            }


        }
        dashboardActivityBinding.galleryBtn.setOnClickListener {
            if (hasReadExternalStoragePermission()) {
                openGallery()
            } else {
                requestReadExternalStoragePermission()
            }
        }


        dashboardActivityBinding.downloadLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                dashboardActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(DownloadFragment(), R.drawable.download_checked)
            }
        }
        dashboardActivityBinding.privacyLl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(MyApplication.PrivacyLink)
            startActivity(intent)
        }
        dashboardActivityBinding.termsLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                startActivity(Intent(this@DashboardActivity, TermsConditionsActivity::class.java))
            }

        }
        dashboardActivityBinding.shareLl.setOnClickListener {
            this.startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Use this Charging Animation & Themes with new and trending themes \n and enjoy new Charging Theme look  \n https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
                )
                type = "text/plain"
            })
        }

        dashboardActivityBinding.bottomNavigationView.selectedItemId =
            savedInstanceState?.getInt(SELECTED_ITEM_ID) ?: R.id.homeNav


        dashboardActivityBinding.bottomNavigationView.setOnItemReselectedListener {
            if (it.itemId != dashboardActivityBinding.bottomNavigationView.selectedItemId) {
                dashboardActivityBinding.bottomNavigationView.selectedItemId = it.itemId
            }
        }
        dashboardActivityBinding.settingsLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, activity
            ) { isLoaded: Boolean ->
                startActivity(Intent(this@DashboardActivity, SettingActivity::class.java))
            }

        }

    }

    private fun loadFragmentWithIcon(fragment: Fragment, iconResourceId: Int) {
        val bundle = Bundle()
        bundle.putInt("iconResourceId", iconResourceId)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        when (fragment) {
            is HomeFragment -> dashboardActivityBinding.bottomNavigationView.selectedItemId =
                R.id.homeNav

            is BatteryFragment -> dashboardActivityBinding.bottomNavigationView.selectedItemId =
                R.id.batteryNav

            is WallpaperFragment -> dashboardActivityBinding.bottomNavigationView.selectedItemId =
                R.id.wallpaperNav

            is DownloadFragment -> dashboardActivityBinding.bottomNavigationView.selectedItemId =
                R.id.downloadNav
        }
    }

    override fun onBackPressed() {
        if (dashboardActivityBinding.drawer.isDrawerOpen(GravityCompat.START)) dashboardActivityBinding.drawer.closeDrawer(
            GravityCompat.START
        ) else {
            openCloseDialog()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            SELECTED_ITEM_ID,
            dashboardActivityBinding.bottomNavigationView.selectedItemId
        )
    }

    private fun hasReadExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        }
    }

    private fun openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // If the permission is not granted, show the overlay permission request dialog.
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${packageName}")
                )
                startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
                return
            }
        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            val nextActivityIntent = Intent(this, LoadActivity::class.java)
            nextActivityIntent.putExtra("imageUri", selectedImageUri.toString())
            startActivity(nextActivityIntent)
        }
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    openGallery()
                } else {
                    Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Proceed with gallery selection as before.
            selectedImageUri = data.data
            val nextActivityIntent = Intent(this, LoadActivity::class.java)
            nextActivityIntent.putExtra("imageUri", selectedImageUri.toString())
            startActivity(nextActivityIntent)
        }

    }
    private fun openCloseDialog() {
        val builder = AlertDialog.Builder(this@DashboardActivity)
        val bind: DialogExitBinding =
            DialogExitBinding.inflate(LayoutInflater.from(this@DashboardActivity))
        builder.setView(bind.getRoot())
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        AdUtils.showNativeAd(
            activity,
            Constants.adsResponseModel.native_ads.adx,
            bind.large.findViewById(com.adsmodule.api.R.id.native_ad1),
            1,
            null
        )
        bind.btnExit.setOnClickListener { v ->
            dialog.dismiss()
            finishAffinity()
            System.exit(0)
        }
        bind.btnBack.setOnClickListener { v -> dialog.dismiss() }
    }


}
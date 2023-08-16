package com.batterycharging.animationscreen.charginganimationeffects.fragment


import android.content.Context

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    val FRAGMENT_TAG = "FRAGMENT_TAG"




    protected fun loadChildFragment(fragment: Fragment, frameId: Int) {
        childFragmentManager
            .beginTransaction()
            .replace(frameId, fragment, FRAGMENT_TAG)
            .commit()
    }


    protected fun isInternetConnected(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }


        return false
    }


}
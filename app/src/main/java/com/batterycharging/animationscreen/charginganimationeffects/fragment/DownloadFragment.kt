package com.batterycharging.animationscreen.charginganimationeffects.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.batterycharging.animationscreen.charginganimationeffects.databinding.FragmentDownloadBinding
import com.batterycharging.animationscreen.charginganimationeffects.ui.DashboardActivity
import com.google.android.material.tabs.TabLayoutMediator

class DownloadFragment : BaseFragment() {

    private lateinit var tabListThemesType: ArrayList<ThemeOptionsModel>
    private lateinit var fragmentDownloadBinding: FragmentDownloadBinding
    private lateinit var myActivity: DashboardActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as DashboardActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDownloadBinding = FragmentDownloadBinding.inflate(inflater, container, false)
        fragmentDownloadBinding.viewPagerThemesOption.isUserInputEnabled = false
        tabListThemesType = arrayListOf()
        tabListThemesType.add(ThemeOptionsModel("Wallpaper"))
        tabListThemesType.add(ThemeOptionsModel("Live Wallpaper"))
        tabListThemesType.add(ThemeOptionsModel("Battery Theme"))
        return fragmentDownloadBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(fragmentDownloadBinding)
        {
            tabListThemesType.forEach {
                val tab = tabLayoutThemesOption.newTab()
                tab.text = it.name
                tabLayoutThemesOption.addTab(tab)

            }

            val adapter = ViewPagerAdapter(tabLayoutThemesOption.tabCount)

            viewPagerThemesOption.adapter = adapter
            viewPagerThemesOption.offscreenPageLimit = 3
            TabLayoutMediator(tabLayoutThemesOption, viewPagerThemesOption) { tab, position ->
                tab.text = tabListThemesType[position].name
            }.attach()
        }

    }

    data class ThemeOptionsModel(val name: String)

    inner class ViewPagerAdapter(private val totalTabs: Int) : FragmentStateAdapter(this) {
        override fun createFragment(position: Int): Fragment {


            return when (position) {
                0 -> DownloadWallpaperFragment()
                1 -> DownloadLiveWallpaperFragment()
                else -> DownloadChargeThemeFragment()
            }
        }

        override fun getItemCount(): Int {
            return totalTabs
        }

    }
}
package com.batterycharging.animationscreen.charginganimationeffects.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.batterycharging.animationscreen.charginganimationeffects.databinding.FragmentWallpaperBinding
import com.batterycharging.animationscreen.charginganimationeffects.ui.DashboardActivity
import com.google.android.material.tabs.TabLayoutMediator

class WallpaperFragment : BaseFragment() {

    private lateinit var tabListThemesType: ArrayList<ThemeOptionsModel>
    private lateinit var fragmentWallpaperBinding: FragmentWallpaperBinding
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
        fragmentWallpaperBinding = FragmentWallpaperBinding.inflate(inflater, container, false)
        fragmentWallpaperBinding.viewPagerThemesOption.isUserInputEnabled = false
        tabListThemesType = arrayListOf()
        tabListThemesType.add(ThemeOptionsModel("Wallpaper"))
        tabListThemesType.add(ThemeOptionsModel("Live Wallpaper"))
        return fragmentWallpaperBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(fragmentWallpaperBinding)
        {
            tabListThemesType.forEach {
                val tab = tabLayoutThemesOption.newTab()
                tab.text = it.name
                tabLayoutThemesOption.addTab(tab)

            }

            val adapter = ViewPagerAdapter(tabLayoutThemesOption.tabCount)

            viewPagerThemesOption.adapter = adapter
            viewPagerThemesOption.offscreenPageLimit = 2
//            viewPagerThemesOption.isUserInputEnabled = false
            TabLayoutMediator(tabLayoutThemesOption, viewPagerThemesOption) { tab, position ->
                tab.text = tabListThemesType[position].name
            }.attach()
        }

    }

    data class ThemeOptionsModel(val name: String)

    inner class ViewPagerAdapter(private val totalTabs: Int) : FragmentStateAdapter(this) {
        override fun createFragment(position: Int): Fragment {


            return when (position) {
                0 -> ThemeWallpaperFragment()
                else -> LivethemeWallpaperFragment()
            }
        }

        override fun getItemCount(): Int {
            return totalTabs
        }

    }
}
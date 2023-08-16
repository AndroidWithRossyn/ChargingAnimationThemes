package com.batterycharging.animationscreen.charginganimationeffects.ui

import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.batterycharging.animationscreen.charginganimationeffects.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }



}




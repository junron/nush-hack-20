package com.example.nav_base_2.controllers

import androidx.fragment.app.Fragment
import com.example.nav_base_2.MainActivity
import com.example.nav_base_2.util.Preferences
import kotlinx.android.synthetic.main.fragment_settings.*

object SettingsController : FragmentController {
    private lateinit var context: Fragment
    var darkMode = true
        private set

    override fun init(context: Fragment) {
        SettingsController.context = context
        with(context) {
            darkMode = Preferences.isDarkMode()
            darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                val prevValue =
                    darkMode
                darkMode = isChecked
                if (prevValue != darkMode) (activity as MainActivity).toggleDarkMode(
                    darkMode
                )
            }
            toolbarSettings.title = "Settings"
        }
    }

    override fun restoreState() {
        with(context) {
            darkModeSwitch.isChecked =
                darkMode
        }
    }
}

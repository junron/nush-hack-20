package com.example.nav_base_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.nav_base_2.util.Navigation
import com.example.nav_base_2.util.Preferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Preferences.init(this)
        setTheme(
            if (Preferences.isDarkMode()) R.style.AppTheme
            else R.style.AppTheme_Light
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Preload events
//        Firebase.firestore

        val navController = nav_host_fragment.findNavController()
        Navigation.init(
            mapOf(
                R.id.nav_main to R.id.mainContent,
                R.id.nav_settings to R.id.settings
            ), navController, bottomAppBarNav
        )
    }

    fun toggleDarkMode(dark: Boolean) {
        println("Dark theme? $dark")
        Preferences.setDarkMode(dark)
        finish()
        startActivity(intent)
    }
}

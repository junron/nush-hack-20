package com.example.shopeepee

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.shopeepee.util.android.Navigation
import com.example.shopeepee.util.android.Preferences
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
        updateTextScale()

//        Preload events
//        Firebase.firestore

        val navController = nav_host_fragment.findNavController()
        Navigation.init(
            mapOf(
                R.id.nav_main to R.id.mainContent,
                R.id.nav_settings to R.id.settings
            ), navController
        )
    }

    fun toggleDarkMode(dark: Boolean) {
        println("Dark theme? $dark")
        Preferences.setDarkMode(dark)
        finish()
        startActivity(intent)
    }

    private fun updateTextScale() {
        val metrics = resources.displayMetrics
        val wm =
            getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity =
            applicationContext.resources.configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(
            applicationContext.resources.configuration,
            metrics
        )
    }

    fun setFontScale(scale: Float) {
        Preferences.setTextScale(scale)
        this.recreate()
    }

    override fun attachBaseContext(newBase: Context) {
        Preferences.init(newBase)
        val config = newBase.resources.configuration
        config.fontScale = Preferences.getTextScale()
        val newContext = newBase.createConfigurationContext(config)
        super.attachBaseContext(newContext)
    }
}

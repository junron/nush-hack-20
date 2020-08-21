package com.example.shopeepee.controllers

import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.shopeepee.MainActivity
import com.example.shopeepee.util.android.Preferences
import kotlinx.android.synthetic.main.fragment_settings.*

object SettingsController : FragmentController {
    private lateinit var context: Fragment
    private var darkMode = true
    private var fontScale = 1F

    override fun init(context: Fragment) {
        SettingsController.context = context
        with(context) {
            darkMode = Preferences.isDarkMode()
            fontScale = Preferences.getTextScale()
            darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                val prevValue =
                    darkMode
                darkMode = isChecked
                if (prevValue != darkMode) (activity as MainActivity).toggleDarkMode(
                    darkMode
                )
            }

            textScale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                var progress: Int = 20
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    this.progress = progress
                    textScaleOutput.text = "Text scaling: (${progress + 80}%)"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    val scale = (progress + 80) / 100F
                    if (scale != fontScale) (activity as MainActivity).setFontScale(scale)
                }

            })

            toolbarSettings.title = "Settings"
        }
    }

    override fun restoreState() {
        with(context) {
            darkModeSwitch.isChecked =
                darkMode
            textScale.progress = ((fontScale * 100) - 80).toInt()
            textScaleOutput.text = "Text scaling: (${(fontScale * 100).toInt()}%)"
        }
    }
}

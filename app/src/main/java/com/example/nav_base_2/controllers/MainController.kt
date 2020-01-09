package com.example.nav_base_2.controllers

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main_content.*

object MainController : FragmentController {
    private lateinit var context: Fragment

    override fun init(context: Fragment) {
        MainController.context = context
        with(context) {
            toolbar.title = "Nav Base 2"
        }
    }

    override fun restoreState() {
    }
}

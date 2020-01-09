package com.example.nav_base_2.controllers

import androidx.fragment.app.Fragment

interface FragmentController {
    fun init(context: Fragment)
    fun restoreState()
}

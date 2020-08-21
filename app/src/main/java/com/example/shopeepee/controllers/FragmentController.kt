package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment

interface FragmentController {
    fun init(context: Fragment)
    fun restoreState()
}

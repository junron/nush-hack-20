package com.example.shopeepee.util.android

import androidx.navigation.NavController

object Navigation {
    private lateinit var navMap: Map<Int, Int>
    private lateinit var navController: NavController

    fun init(
        navMap: Map<Int, Int>,
        navController: NavController
    ) {
        Navigation.navMap = navMap
        Navigation.navController = navController
    }

    fun navigate(id: Int) {
        navController.navigate(id)
    }
}

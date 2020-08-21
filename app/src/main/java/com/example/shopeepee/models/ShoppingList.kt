package com.example.shopeepee.models

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingList(
    val id: String,
    val name: String,
    val items: List<ShoppingItem>,
    val potentialItems: List<PotentialShoppingItem>,
    val confirmedItems: List<PotentialShoppingItem>
)

@Serializable
data class ShoppingItem(
    val id: String,
    val name: String,
    var acquired: Boolean = false
)

@Serializable
data class PotentialShoppingItem(
    val id: String,
//    Dollars
    val price: Double?,
    val nutritionInfo: List<NutritionInfo>
)

@Serializable
data class NutritionInfo(val idk: String)

package com.example.shopeepee.models

data class ShoppingList(
    val id: String,
    val name: String,
    val items: List<ShoppingItem>,
    val potentialItems: List<PotentialShoppingItem>,
    val confirmedItems: List<PotentialShoppingItem>
)

data class ShoppingItem(
    val id: String,
    val name: String
)

data class PotentialShoppingItem(
    val id: String,
//    Dollars
    val price: Double?,
    val nutritionInfo: List<NutritionInfo>
)

data class NutritionInfo(val idk: String)

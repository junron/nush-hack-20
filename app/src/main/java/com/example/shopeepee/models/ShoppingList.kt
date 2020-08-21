package com.example.shopeepee.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class ShoppingList(
    val id: String,
    val name: String,
    val items: List<ShoppingItem>,
    val potentialItems: List<PotentialShoppingItem>,
    val confirmedItems: List<PotentialShoppingItem>
) : Parcelable

@Parcelize
data class ShoppingItem(
    val id: String,
    val name: String,
    var acquired: Boolean = false
) : Parcelable

@Parcelize
data class PotentialShoppingItem(
    val id: String,
//    Dollars
    val price: Double?,
    val nutritionInfo: List<NutritionInfo>
) : Parcelable

@Parcelize
open class NutritionInfo(val name: String, val units: String, val value: Double) : Parcelable

@Parcelize
data class Calories(val a: Double): NutritionInfo("Calories", "kcal", a)

@Parcelize
data class Carbohydrates(val a: Double): NutritionInfo("Carbohydrates", "g", a)

@Parcelize
data class Sugar(val a: Double): NutritionInfo("Sugar", "g", a)


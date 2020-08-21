package com.example.shopeepee.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
data class ShoppingList(
    val id: String,
    val name: String,
    val items: List<ShoppingItem>,
    val potentialItems: List<PotentialShoppingItem>,
    val confirmedItems: List<PotentialShoppingItem>
) : Parcelable

@Parcelize
@Serializable
data class ShoppingItem(
    val id: String,
    val name: String,
    var acquired: Boolean = false
) : Parcelable

@Parcelize
@Serializable
data class PotentialShoppingItem(
    val id: String,
//    Dollars
    val price: Double?,
    val nutritionInfo: List<NutritionInfo>
) : Parcelable

@Parcelize
@Serializable
data class NutritionInfo(val idk: String) : Parcelable


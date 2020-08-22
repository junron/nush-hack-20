package com.example.shopeepee.models

import android.media.Image
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
    var acquired: Boolean = false,
    val nutritionInfo: List<NutritionInfo>?=null
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
data class CompareNutrition(
    val item1: ShoppingItem,
    //val img1: Image, //TODO: fix the bloody images
    val item2: ShoppingItem,
    //val img2: Image,
    val nutritionData1: List<NutritionInfo>,
    val nutritionData2: List<NutritionInfo>
) : Parcelable

@Parcelize
@Serializable
open class NutritionInfo(val name: String, val units: String, val value: Double?) : Parcelable

@Parcelize
data class Calories(val a: Double): NutritionInfo("Calories", "kcal", a)

@Parcelize
data class Carbohydrates(val a: Double): NutritionInfo("Carbohydrates", "g", a)

@Parcelize
data class Sugar(val a: Double): NutritionInfo("Sugar", "g", a)


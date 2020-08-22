package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment
import com.example.shopeepee.adapters.NutritionCompareAdapter
import com.example.shopeepee.adapters.NutritionInfoAdapter
import com.example.shopeepee.adapters.ShoppingListItemDisplayAdapter
import com.example.shopeepee.fragments.NutritionCompareList
import com.example.shopeepee.models.*
import kotlinx.android.synthetic.main.fragment_compare_nutrition.*
import kotlinx.android.synthetic.main.fragment_display_nutrition.*
import kotlinx.android.synthetic.main.fragment_display_shopping_list.*
import kotlinx.android.synthetic.main.nutrition_compare_entry.*

object NutritionInfoController : FragmentController {
    private lateinit var context: Fragment;
    private var list: ShoppingItem? = null;
    override fun init(context: Fragment) {
        NutritionInfoController.context = context;
        with(context) {
            foodname.text= list?.name ?: return
            nutritionCompareRecycler.adapter = list?.nutritionInfo?.let { NutritionInfoAdapter(it) } ?:return
        }
    }

    fun data(items: ShoppingItem) {
        list = items
    }

    override fun restoreState() {
    }
}

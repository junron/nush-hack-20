package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment
import com.example.shopeepee.adapters.NutritionCompareAdapter
import com.example.shopeepee.adapters.ShoppingListItemDisplayAdapter
import com.example.shopeepee.fragments.NutritionCompareList
import com.example.shopeepee.models.CompareNutrition
import com.example.shopeepee.models.NutritionInfo
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.models.ShoppingList
import kotlinx.android.synthetic.main.fragment_compare_nutrition.*
import kotlinx.android.synthetic.main.fragment_display_shopping_list.*
import kotlinx.android.synthetic.main.nutrition_compare_entry.*

object NutritionCompareController : FragmentController {
    private lateinit var context: Fragment;
    private var list: CompareNutrition? = null;
    override fun init(context: Fragment) {
        NutritionCompareController.context = context;
        with(context) {
            item1_name.text = list?.item1?.name ?: return
            item2_name.text = list?.item2?.name ?: return
            val list = list ?: return
            val items = mutableListOf<Pair<NutritionInfo, NutritionInfo>>();
            var data1 = list.nutritionData1.sortedBy { it.name }
            var data2 = list.nutritionData2.sortedBy { it.name }
            val i = 0
            var swap = true
            if (data1.size < data2.size) {
                val temp = data1
                data1 = data2
                data2 = data1
            }
            data1.forEach {
                if (i > data2.size-1) {
                    items.add(Pair(NutritionInfo(it.name, it.units, it.value), NutritionInfo(it.name, it.units, null)))
                } else if (it.name != data2[i].name) {
                    if (it.name > data2[i].name) {
                        items.add(Pair(NutritionInfo(data2[i].name, data2[i].units, null), NutritionInfo(data2[i].name, data2[i].units, data2[i].value)))
                    }
                    items.add(Pair(NutritionInfo(it.name, it.units, it.value), NutritionInfo(it.name, it.units, null)))
                }
            }

            var entries = items
            if (swap) {
                entries = mutableListOf()
                items.forEach {
                    entries.add(Pair(it.second, it.first))
                }
            }
            nutritionCompareRecycler.adapter = NutritionCompareAdapter(entries);
        }
    }

    fun data(items: CompareNutrition) {
        list = items
    }

    override fun restoreState() {
    }
}

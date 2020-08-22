package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListAdapter
import com.example.shopeepee.fragments.MainContent
import com.example.shopeepee.fragments.MainContentDirections
import com.example.shopeepee.models.CompareNutrition
import com.example.shopeepee.models.NutritionInfo
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.util.android.Navigation
import kotlinx.android.synthetic.main.fragment_main_content.*

object MainController : FragmentController {
    private lateinit var context: Fragment

    override fun init(context: Fragment) {
        MainController.context = context
        with(context) {
            toolbar.title = "Smartcart"
            settings.setOnClickListener {
                Navigation.navigate(R.id.settings)
                /*findNavController().navigate(MainContentDirections.showNutritionCompareList(
                    CompareNutrition(
                        ShoppingItem("1","potato"), ShoppingItem("11","carrot"),
                    mutableListOf(
                        NutritionInfo("Total Fat", "g", 0.1),
                        NutritionInfo("Cholesterol", "mg", 0.1),
                        NutritionInfo("Sodium", "mg", 6.0),
                        NutritionInfo("Potassium", "mg", 421.0),
                        NutritionInfo("Carbohydrates", "g", 17.0),
                        NutritionInfo("Protein", "g", 2.0)
                    ),
                    mutableListOf(
                        NutritionInfo("Total Fat", "g", 0.2),
                        NutritionInfo("Sodium", "mg", 69.0),
                        NutritionInfo("Potassium", "mg", 320.0),
                        NutritionInfo("Carbohydrates", "g", 10.0),
                        NutritionInfo("Protein", "g", 0.9)
                    ))
                ))*/
            }
            newList.setOnClickListener {
                Navigation.navigate(R.id.newShoppingList)
            }
            (context as MainContent).viewModel.shoppingLists.observe({ lifecycle }) {
                shoppingListItems.adapter = ShoppingListAdapter(this, it)
            }
        }
    }

    override fun restoreState() {
    }
}

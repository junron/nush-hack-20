package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListAdapter
import com.example.shopeepee.models.PotentialShoppingItem
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.android.Navigation
import kotlinx.android.synthetic.main.fragment_main_content.*

object NewShoppingListController : FragmentController {
    private lateinit var context: Fragment

    override fun init(context: Fragment) {
        //TODO: Firebase
        val shoppingLists = listOf(
            ShoppingList(
                "d9a79784-e3b0-11ea-87d0-0242ac130003",
                "Food",
                listOf(
                    ShoppingItem(
                        "e5c54854-e3b0-11ea-87d0-0242ac130003",
                        "Carrot"
                    )
                ),
                listOf(
                    PotentialShoppingItem(
                        "e5c54854-e3b0-11ea-87d0-0242ac130003",
                        0.5,
                        emptyList()
                    )
                ),
                emptyList()
            )
        )
        NewShoppingListController.context = context
        with(context) {
            toolbar.title = "Shopeepee"
            settings.setOnClickListener {
                Navigation.navigate(R.id.settings)
            }
            shoppingListItems.adapter = ShoppingListAdapter(this, shoppingLists)
        }
    }

    override fun restoreState() {
    }
}

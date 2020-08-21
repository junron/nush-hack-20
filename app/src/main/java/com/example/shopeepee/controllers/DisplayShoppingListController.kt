package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment
import com.example.shopeepee.adapters.ShoppingListItemDisplayAdapter
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.models.ShoppingList
import kotlinx.android.synthetic.main.fragment_display_shopping_list.*

object DisplayShoppingListController : FragmentController {
    private lateinit var context: Fragment;
    private var list: ShoppingList? = null;
    override fun init(context: Fragment) {
        DisplayShoppingListController.context = context;
        with (context) {
            if (list != null) {
                var items = mutableListOf<ShoppingItem>();
                list!!.items.forEach {
                    items.add(ShoppingItem(it.id, it.name, false));
                }
                list!!.confirmedItems.forEach { confirmed->
                    val item = list!!.items.firstOrNull { it.id == confirmed.id };
                    if (item != null) {
                        item.acquired=true;
                    }
                }
                shoppingListRecycler.adapter=ShoppingListItemDisplayAdapter(items);
            }
        }
    }

    fun data(items: ShoppingList) {
        list = items
    }

    override fun restoreState() {
        TODO("Not yet implemented")
    }
}
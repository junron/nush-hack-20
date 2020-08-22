package com.example.shopeepee.controllers

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListItemDisplayAdapter
import com.example.shopeepee.fragments.ScanFragmentDirections
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.android.Navigation
import kotlinx.android.synthetic.main.fragment_display_shopping_list.*

object DisplayShoppingListController : FragmentController {
    private lateinit var context: Fragment;
    private var list: ShoppingList? = null;
    override fun init(context: Fragment) {
        DisplayShoppingListController.context = context;
        with(context) {
            val list = list ?: return
            displayShoppingListToolbar.apply {
                title = list.name
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                navigationIcon?.setTint(Color.WHITE)
                setNavigationOnClickListener {
                    Navigation.navigate(R.id.mainContent)
                }
            }
            val items = mutableListOf<ShoppingItem>();
            list.items.forEach {
                items.add(ShoppingItem(it.id, it.name, false));
            }
            list.confirmedItems.forEach { confirmed ->
                val item = list.items.firstOrNull { it.id == confirmed.id };
                if (item != null) {
                    item.acquired = true;
                }
            }
            scan.setOnClickListener {
                findNavController().navigate(ScanFragmentDirections.scanShoppingList(list.id))
            }
            shoppingListRecycler.adapter = ShoppingListItemDisplayAdapter(items, false);
        }
    }

    fun data(items: ShoppingList) {
        list = items
    }

    override fun restoreState() {
    }
}

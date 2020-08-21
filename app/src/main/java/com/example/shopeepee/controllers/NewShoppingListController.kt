package com.example.shopeepee.controllers

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListItemAdapter
import com.example.shopeepee.util.android.Navigation
import kotlinx.android.synthetic.main.fragment_new_shopping_list.*

object NewShoppingListController : FragmentController {
    private lateinit var context: Fragment

    override fun init(context: Fragment) {
        NewShoppingListController.context = context
        with(context) {
            newShoppingListToolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                navigationIcon?.setTint(Color.WHITE)
                setNavigationOnClickListener {
                    Navigation.navigate(R.id.mainContent)
                }
            }
            itemsRecycler.adapter = ShoppingListItemAdapter(mutableListOf(), true)
        }
    }

    override fun restoreState() {
    }
}

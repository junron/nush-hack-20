package com.example.shopeepee.controllers

import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListItemEditAdapter
import com.example.shopeepee.fragments.NewShoppingList
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.android.Navigation
import com.example.shopeepee.util.android.onTextChange
import com.example.shopeepee.util.uuid
import kotlinx.android.synthetic.main.fragment_new_shopping_list.*

object NewShoppingListController : FragmentController {
    private lateinit var context: Fragment
    private var items = emptyList<ShoppingItem>()

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
            itemsRecycler.adapter = ShoppingListItemEditAdapter(
                mutableListOf(
                    ShoppingItem(uuid(), "")
                )
            )
            classlistName.editText?.onTextChange {
                updateValidity(items)
            }
            classlistDone.setOnClickListener {
                val shoppingList = ShoppingList(
                    uuid(),
                    classlistName.editText?.text.toString(),
                    items,
                    emptyList(),
                    emptyList()
                )
                (context as NewShoppingList).viewModel.createShoppingList(shoppingList)
                Navigation.navigate(R.id.mainContent)
            }
        }
    }

    fun updateValidity(items: List<ShoppingItem>) {
        this.items = items
        val validItems = items.filter { it.name != "" }
        if (validItems.isNotEmpty() and (context.classlistName.editText?.text?.isNotBlank() == true)) {
            context.classlistDone.visibility = View.VISIBLE
        } else {
            context.classlistDone.visibility = View.GONE
        }
    }

    override fun restoreState() {
    }
}

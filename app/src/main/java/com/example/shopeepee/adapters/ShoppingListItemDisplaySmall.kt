package com.example.shopeepee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.shopeepee.R
import com.example.shopeepee.models.ShoppingList
import kotlinx.android.synthetic.main.shopping_list_item_small.view.*

class ShoppingListItemDisplaySmall(
    private val shoppingList: ShoppingList
) : BaseAdapter() {
    val items = shoppingList.items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val potentialItemsCount = shoppingList.potentialItems.count {
            it.id == item.id
        }
        val drawables = listOf(
            R.drawable.ic_baseline_looks_one_24,
            R.drawable.ic_baseline_looks_two_24,
            R.drawable.ic_baseline_looks_3_24,
            R.drawable.ic_baseline_looks_4_24
        )
        return LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item_small, null)
            .apply {
                itemIcon.setImageResource(
                    when {
                        item.acquired -> R.drawable.ic_baseline_done_24
                        potentialItemsCount > 0 -> drawables[potentialItemsCount - 1]
                        else -> 0
                    }
                )
                itemName.text = item.name
            }
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size

}

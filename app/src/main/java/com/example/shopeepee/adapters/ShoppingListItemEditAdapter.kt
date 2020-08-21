package com.example.shopeepee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.shopeepee.R
import com.example.shopeepee.controllers.NewShoppingListController
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.util.android.onTextChange
import com.example.shopeepee.util.uuid
import kotlinx.android.synthetic.main.shopping_item_editable.view.*

class ShoppingListItemEditAdapter(
    val items: MutableList<ShoppingItem>
) : BaseAdapter() {

    private var focusLast = true

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return LayoutInflater.from(parent.context).inflate(R.layout.shopping_item_editable, null)
            .apply {
                if (position == items.lastIndex) {
                    itemDelete.visibility = View.GONE
                    if (focusLast) {
                        itemName.requestFocus()
                        focusLast = false
                    }
                    if (item.name == "") {
                        itemAdd.visibility = View.GONE
                    }
                } else {
                    itemAdd.visibility = View.GONE
                }
                itemNameEdit.setText(item.name)
                itemNumber.text = "" + (position + 1)
                itemNameEdit.onTextChange {
                    items[position] = item.copy(name = it)
                    NewShoppingListController.updateValidity(items)
                }
                itemDelete.setOnClickListener {
                    items -= item
                    NewShoppingListController.updateValidity(items)
                    notifyDataSetChanged()
                }
                itemAdd.setOnClickListener {
                    items += ShoppingItem(uuid(), "")
                    NewShoppingListController.updateValidity(items)
                    focusLast = true
                    notifyDataSetChanged()
                }
            }
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size
}

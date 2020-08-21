package com.example.shopeepee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.shopeepee.R
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.util.android.onTextChange
import kotlinx.android.synthetic.main.shopping_item.view.*

class ShoppingListItemAdapter(
    var items: MutableList<ShoppingItem>,
    private val editable: Boolean
) : BaseAdapter() {
    private val baseUUID = "45c68702-e3b3-11ea-87d0-0242ac130003"

    init {
        if (editable) {
            items.plusAssign(ShoppingItem(baseUUID, "Item name"))
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, null).apply {
            if (item.id == baseUUID) {
                itemNameEdit.setText("")
                itemName.hint = "Add item"
                itemDelete.visibility = View.GONE
            } else {
                itemNameEdit.setText(item.name)
            }
            if (editable) {
                itemNameEdit.onTextChange {
                    items[position] = item.copy(name = it)
                }
                itemDelete.setOnClickListener {
                    items.remove(item)
                    notifyDataSetChanged()
                }
            } else {
                itemName.isFocusable = false
                itemName.isFocusableInTouchMode = false
                itemDelete.visibility = View.GONE
            }
        }
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size
}

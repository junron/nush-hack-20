package com.example.shopeepee.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.shopeepee.R
import com.example.shopeepee.models.ShoppingItem
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ShoppingListItemDisplayAdapter(
    var items: MutableList<ShoppingItem>,
    val clickable: Boolean
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, null).apply {
            item_card.item_name.text=item.name;
            item_card.isClickable = clickable
            if (item.acquired)
                item_card.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#58bf91"));
            else
                item_card.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ababab"));
            item_card.setOnClickListener {
                item.acquired = !item.acquired;
                notifyDataSetChanged()
                if (item.acquired)
                    item_card.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#58bf91"))
                else
                    item_card.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ababab"));
            }
        }
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size

}

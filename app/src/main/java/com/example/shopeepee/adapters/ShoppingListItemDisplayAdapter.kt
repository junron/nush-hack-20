package com.example.shopeepee.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.shopeepee.R
import com.example.shopeepee.models.ShoppingItem
import com.example.shopeepee.util.android.onTextChange
import kotlinx.android.synthetic.main.shopping_item.view.*
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class ShoppingListItemDisplayAdapter(
    var items: MutableList<ShoppingItem>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, null).apply {
            item_card.item_name.text=item.name;
            if (item.acquired)
                item_card.setBackgroundColor(Color.parseColor("#30c277"));
            else
                item_card.setBackgroundColor(Color.parseColor("#30c277"));
            item_card.setOnClickListener {
                item.acquired = !item.acquired;
                notifyDataSetChanged()
                if (item.acquired)
                    item_card.setBackgroundColor(Color.parseColor("#30c277"));
                else
                    item_card.setBackgroundColor(Color.parseColor("#30c277"));
            }
        }
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size

}
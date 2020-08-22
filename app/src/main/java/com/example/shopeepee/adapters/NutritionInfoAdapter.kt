package com.example.shopeepee.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.shopeepee.R
import com.example.shopeepee.fragments.MainContentDirections
import com.example.shopeepee.models.NutritionInfo
import com.example.shopeepee.models.ShoppingList
import kotlinx.android.synthetic.main.nutrition_compare_entry.view.*
import kotlinx.android.synthetic.main.nutrition_info.view.*
import kotlinx.android.synthetic.main.shopping_list.view.*
import kotlinx.android.synthetic.main.shopping_list_item.view.*
import kotlinx.android.synthetic.main.shopping_list_item.view.item_name

class NutritionInfoAdapter(var data: List<NutritionInfo>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return LayoutInflater.from(parent.context).inflate(R.layout.nutrition_info, null).apply {
            item_name.text=item.name
            item_value.text= String.format("%.1d", item.value)
            item_units.text=item.units
        }
    }

    override fun getItem(p0: Int) = data[p0]

    override fun getItemId(p0: Int) = 0L

    override fun getCount() = data.size
}
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
import kotlinx.android.synthetic.main.shopping_list.view.*
import kotlinx.android.synthetic.main.shopping_list_item.view.*

class NutritionCompareAdapter(var data: List<Pair<NutritionInfo, NutritionInfo>>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        return LayoutInflater.from(parent.context).inflate(R.layout.nutrition_compare_entry, null).apply {
            entry_name.text = item.first.name
            if (item.first.value != null) item1_name.text = String.format("%.1f", item.first.value)
            else item1_name.text = "N/A"
            if (item.second.value != null) item2_name.text = String.format("%.1f", item.second.value)
            else item2_name.text = "N/A"
            item1_units.text = item.first.units
            item2_units.text = item.second.units
            if (item.first.value != null && item.second.value != null && item.first.value!! > item.second.value!!) {
                item1_name.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_caret_arrow_up, 0, 0,0);
                item2_name.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_caret_arrow_down, 0, 0,0);
            } else if (item.first.value != null && item.second.value != null && item.first.value!! < item.second.value!!) {
                item1_name.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_caret_arrow_up, 0, 0,0);
                item2_name.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_caret_arrow_down, 0, 0,0);
            }
        }
    }

    override fun getItem(p0: Int) = data[p0]

    override fun getItemId(p0: Int) = 0L

    override fun getCount() = data.size
}
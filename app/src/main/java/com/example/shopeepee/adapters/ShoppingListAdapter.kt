package com.example.shopeepee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.shopeepee.R
import com.example.shopeepee.fragments.MainContentDirections
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.android.Navigation
import kotlinx.android.synthetic.main.shopping_list.view.*

class ShoppingListAdapter(val fragment: Fragment, var data: List<ShoppingList>) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, parent: ViewGroup): View {
        val item = getItem(p0)
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.shopping_list, null)
        with(view) {
            itemName.text = item.name
            itemsComplete.text = "${item.confirmedItems.size}/${item.items.size} items complete"
            setOnClickListener {
                findNavController().navigate(MainContentDirections.showDisplayShoppingList(item))
            }
        }
        return view
    }

    override fun getItem(p0: Int) = data[p0]

    override fun getItemId(p0: Int) = 0L

    override fun getCount() = data.size
}

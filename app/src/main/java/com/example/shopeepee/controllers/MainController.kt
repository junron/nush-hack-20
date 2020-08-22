package com.example.shopeepee.controllers

import androidx.fragment.app.Fragment
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListAdapter
import com.example.shopeepee.fragments.MainContent
import com.example.shopeepee.util.android.Navigation
import kotlinx.android.synthetic.main.fragment_main_content.*

object MainController : FragmentController {
    private lateinit var context: Fragment

    override fun init(context: Fragment) {
        MainController.context = context
        with(context) {
            toolbar.title = "Smartcart"
            settings.setOnClickListener {
                Navigation.navigate(R.id.settings)
            }
            newList.setOnClickListener {
                Navigation.navigate(R.id.newShoppingList)
            }
            (context as MainContent).viewModel.shoppingLists.observe({ lifecycle }) {
                shoppingListItems.adapter = ShoppingListAdapter(this, it)
            }
        }
    }

    override fun restoreState() {
    }
}

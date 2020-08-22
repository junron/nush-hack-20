package com.example.shopeepee.controllers

import android.graphics.Color
import android.view.Gravity
import androidx.fragment.app.Fragment
import com.example.shopeepee.R
import com.example.shopeepee.fragments.ScanFragment
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.viewmodels.ShoppingListsViewModel
import kotlinx.android.synthetic.main.fragment_scan.*

object ScanController : FragmentController {
    private lateinit var context: Fragment
    private lateinit var shoppingList: ShoppingList
    private lateinit var viewModel: ShoppingListsViewModel

    override fun init(context: Fragment) {
        ScanController.context = context
        this.viewModel = (context as ScanFragment).viewModel
        with(context) {
            toolbarMain.apply {
                title = shoppingList.name
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                navigationIcon?.setTint(Color.WHITE)
                peek.setOnClickListener {
                    itemsDrawer.openDrawer(Gravity.RIGHT)
                }
            }

        }
    }

    fun data(shoppingListId: String) {
        shoppingList = viewModel.shoppingLists.value.find { it.id == shoppingListId } ?: return
    }

    override fun restoreState() {
    }
}

package com.example.shopeepee.controllers

import android.graphics.Color
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListItemDisplaySmall
import com.example.shopeepee.fragments.MainContentDirections
import com.example.shopeepee.fragments.ScanFragment
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.android.Navigation
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
                setNavigationOnClickListener {
                    Navigation.navigate(R.id.mainContent)
                    findNavController().navigate(
                        MainContentDirections.showDisplayShoppingList(
                            shoppingList
                        )
                    )
                }
                peek.setOnClickListener {
                    itemsDrawer.openDrawer(Gravity.RIGHT)
                }
                itemsListView.adapter = ShoppingListItemDisplaySmall(shoppingList)
            }

        }
    }

    fun data(shoppingListId: String, viewModel: ShoppingListsViewModel) {
        shoppingList = viewModel.shoppingLists.value.find { it.id == shoppingListId } ?: return
    }

    override fun restoreState() {
    }
}

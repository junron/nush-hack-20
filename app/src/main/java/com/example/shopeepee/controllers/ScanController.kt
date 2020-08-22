package com.example.shopeepee.controllers

import android.graphics.Color
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.shopeepee.R
import com.example.shopeepee.adapters.ShoppingListItemDisplaySmall
import com.example.shopeepee.fragments.MainContentDirections
import com.example.shopeepee.fragments.ScanFragment
import com.example.shopeepee.models.PotentialShoppingItem
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.android.Navigation
import com.example.shopeepee.viewmodels.ShoppingListsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_scan.*

object ScanController : FragmentController {
    private lateinit var context: Fragment
    private lateinit var shoppingList: ShoppingList
    private lateinit var viewModel: ShoppingListsViewModel
    private var currentItem: PotentialShoppingItem? = null
    private var pendingConfirm: Boolean = false

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
                viewModel.shoppingLists.observe({ lifecycle }) {
                    shoppingList = it.find { it.id == shoppingList.id } ?: return@observe
                    itemsListView.adapter = ShoppingListItemDisplaySmall(shoppingList)
                }
            }

        }
    }

    fun data(shoppingListId: String, viewModel: ShoppingListsViewModel) {
        shoppingList = viewModel.shoppingLists.value.find { it.id == shoppingListId } ?: return
    }

    private val scannedResults = MutableList(10) {
        "NONE"
    }

    private val possible = arrayOf(
        "apple",
        "brocolli",
        "can_beans",
        "carrot",
        "noodle",
        "potato",
        "soda"
    )

    fun scanned(res: String) {
        scannedResults += res
        scannedResults.removeAt(0)
        val counts = possible.map {
            scannedResults.count { item -> it == item }
        }
        val max = counts.max() ?: return
        if (max > 4) {
            val idxmax = counts.indexOf(max)
            val objName = possible[idxmax]
            if (currentItem != null) return
            if (pendingConfirm) return
            Snackbar.make(context.itemsDrawer, "Is this $objName?", Snackbar.LENGTH_SHORT)
                .setAction("Yes") {
                    pendingConfirm = false
                    val item = shoppingList.items.find { it.name == objName } ?: return@setAction
                    currentItem = PotentialShoppingItem(
                        item.id,
                        null,
                        emptyList()
                    )
                    val newShoppingList = shoppingList.copy(
                        potentialItems = shoppingList.potentialItems + currentItem!!
                    )
                    updateItem(newShoppingList)
                }
                .show()
            pendingConfirm = true

        }
    }

    fun priceDetected(price: Double) {
        if (currentItem == null) return
        Snackbar.make(context.itemsDrawer, "Price detected: $price", Snackbar.LENGTH_LONG)
            .show()
        currentItem = currentItem!!.copy(price = price)
        val newShoppingList = shoppingList.copy(
            potentialItems = shoppingList.potentialItems.filter { it != currentItem } + currentItem!!
        )
        updateItem(newShoppingList)
//        currentItem = null
    }

    private fun updateItem(shoppingList: ShoppingList) {
        viewModel.setShoppingList(shoppingList)
    }

    override fun restoreState() {
    }
}

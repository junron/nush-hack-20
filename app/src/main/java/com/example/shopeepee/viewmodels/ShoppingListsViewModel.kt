package com.example.shopeepee.viewmodels

import androidx.lifecycle.ViewModel
import com.example.shopeepee.models.SerializedShoppingList
import com.example.shopeepee.models.ShoppingList
import com.example.shopeepee.util.SafeLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShoppingListsViewModel : ViewModel() {
    val shoppingLists: SafeLiveData<List<ShoppingList>> = SafeLiveData(listOf())

    init {
        Firebase.firestore
            .collection("shopping-lists")
            .get()
            .addOnSuccessListener {
                val lists = it.toObjects(SerializedShoppingList::class.java)
                shoppingLists.value = lists.map { it.toShoppingList() }
            }
        Firebase.firestore
            .collection("shopping-lists")
            .addSnapshotListener { snapshot, _ ->
                snapshot ?: return@addSnapshotListener
                val lists = snapshot.toObjects(SerializedShoppingList::class.java)
                shoppingLists.value = lists.map { it.toShoppingList() }
            }
    }

    fun setShoppingList(shoppingList: ShoppingList) {
        val serialized = SerializedShoppingList.fromShoppingList(shoppingList)
        Firebase.firestore.collection("shopping-lists")
            .document(shoppingList.id)
            .set(serialized)
    }
}

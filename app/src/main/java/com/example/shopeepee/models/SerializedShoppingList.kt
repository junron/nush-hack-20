package com.example.shopeepee.models

import com.example.shopeepee.util.uuid
import kotlinx.serialization.json.Json.Default.parse
import kotlinx.serialization.json.Json.Default.stringify

data class SerializedShoppingList(
    val id: String = uuid(),
    val name: String = "",
    val items: List<String> = emptyList(),
    val potentialItems: List<String> = emptyList(),
    val confirmedItems: List<String> = emptyList()
) {
    fun toShoppingList() = ShoppingList(
        id,
        name,
        items.map { parse(ShoppingItem.serializer(), it) },
        potentialItems.map { parse(PotentialShoppingItem.serializer(), it) },
        confirmedItems.map { parse(PotentialShoppingItem.serializer(), it) }
    )

    companion object {
        fun fromShoppingList(shoppingList: ShoppingList) = SerializedShoppingList(
            shoppingList.id,
            shoppingList.name,
            shoppingList.items.map { stringify(ShoppingItem.serializer(), it) },
            shoppingList.potentialItems.map { stringify(PotentialShoppingItem.serializer(), it) },
            shoppingList.confirmedItems.map { stringify(PotentialShoppingItem.serializer(), it) }
        )
    }

}

package com.example.nav_base_2.util

fun <T> MutableList<T>.removeAll() = this.removeAll(this)
fun <T> MutableList<T>.setAll(list: List<T>) {
    removeAll()
    plusAssign(list)
}

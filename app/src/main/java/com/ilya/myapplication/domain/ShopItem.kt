package com.ilya.myapplication.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val used: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}

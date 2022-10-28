package com.ilya.shoplist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    var used: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}

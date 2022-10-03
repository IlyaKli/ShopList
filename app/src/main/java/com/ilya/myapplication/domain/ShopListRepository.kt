package com.ilya.myapplication.domain

interface ShopListRepository {

    fun getShopList(): List<ShopItem>

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun removeShopItem(shopItem: ShopItem)

    fun addShopItem(shopItem: ShopItem)
}
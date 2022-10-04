package com.ilya.myapplication.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun removeShopItem(shopItem: ShopItem)

    fun addShopItem(shopItem: ShopItem)
}
package com.ilya.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem

    suspend fun removeShopItem(shopItem: ShopItem)

    suspend fun addShopItem(shopItem: ShopItem)
}
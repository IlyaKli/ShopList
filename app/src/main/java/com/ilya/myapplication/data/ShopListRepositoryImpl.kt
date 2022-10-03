package com.ilya.myapplication.data

import com.ilya.myapplication.domain.ShopItem
import com.ilya.myapplication.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {



    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    init {
        for (i in 0..10) {
            val shopItem = ShopItem("name $i", i, false)
            shopList.add(shopItem)
        }
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        shopList.remove(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}
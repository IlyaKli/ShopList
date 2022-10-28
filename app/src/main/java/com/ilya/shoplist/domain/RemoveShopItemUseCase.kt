package com.ilya.shoplist.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun removeShopItem(shopItem: ShopItem) {
        shopListRepository.removeShopItem(shopItem)
    }
}
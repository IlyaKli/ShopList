package com.ilya.myapplication.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemId: ShopItem) {
        shopListRepository.getShopItem(shopItemId)
    }
}
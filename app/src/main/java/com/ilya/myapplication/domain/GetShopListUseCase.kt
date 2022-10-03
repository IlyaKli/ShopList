package com.ilya.myapplication.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList() {
        shopListRepository.getShopList()
    }
}
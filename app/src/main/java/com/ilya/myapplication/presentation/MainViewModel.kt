package com.ilya.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.ilya.myapplication.data.ShopListRepositoryImpl
import com.ilya.myapplication.domain.EditShopItemUseCase
import com.ilya.myapplication.domain.GetShopListUseCase
import com.ilya.myapplication.domain.RemoveShopItemUseCase
import com.ilya.myapplication.domain.ShopItem

class MainViewModel : ViewModel(){

    private val shopListRepository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)

    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    private val removeShopItemUseCase = RemoveShopItemUseCase(shopListRepository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun editShopList(shopItem: ShopItem) {
        val newItem = shopItem.copy(used = !shopItem.used)
        editShopItemUseCase.editShopItem(newItem)
    }




}
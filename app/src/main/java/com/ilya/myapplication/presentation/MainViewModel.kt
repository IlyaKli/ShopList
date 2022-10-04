package com.ilya.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilya.myapplication.data.ShopListRepositoryImpl
import com.ilya.myapplication.domain.*

class MainViewModel : ViewModel(){

    val shopListRepository = ShopListRepositoryImpl

    val getShopListUseCase = GetShopListUseCase(shopListRepository)

    val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    val removeShopItemUseCase = RemoveShopItemUseCase(shopListRepository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShoItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun editShopList(shopItem: ShopItem) {
        val newItem = shopItem.copy(used = !shopItem.used)
        editShopItemUseCase.editShopItem(newItem)
    }




}
package com.ilya.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilya.myapplication.data.ShopListRepositoryImpl
import com.ilya.myapplication.domain.*

class MainViewModel : ViewModel(){

    val shopListLD = MutableLiveData<List<ShopItem>>()

    val shopListRepository = ShopListRepositoryImpl

    val getShopListUseCase = GetShopListUseCase(shopListRepository)

    val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    val removeShopItemUseCase = RemoveShopItemUseCase(shopListRepository)

    fun getShopListLD() : LiveData<List<ShopItem>> {
        return shopListLD
    }

    fun getShopList() {
        shopListLD.value = getShopListUseCase.getShopList()
    }

    fun removeShoItem(shopItem: ShopItem) {
        removeShopItemUseCase.removeShopItem(shopItem)
        getShopList()
    }

    fun editShopList(shopItem: ShopItem) {
        val newItem = shopItem.copy(used = !shopItem.used)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }




}
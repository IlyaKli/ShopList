package com.ilya.myapplication.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.myapplication.data.ShopListRepositoryImpl
import com.ilya.myapplication.domain.EditShopItemUseCase
import com.ilya.myapplication.domain.GetShopListUseCase
import com.ilya.myapplication.domain.RemoveShopItemUseCase
import com.ilya.myapplication.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val shopListRepository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(shopListRepository)

    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    private val removeShopItemUseCase = RemoveShopItemUseCase(shopListRepository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun editShopList(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(used = !shopItem.used)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}
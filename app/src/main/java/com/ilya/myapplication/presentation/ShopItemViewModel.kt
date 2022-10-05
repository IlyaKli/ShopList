package com.ilya.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.ilya.myapplication.data.ShopListRepositoryImpl
import com.ilya.myapplication.domain.AddShopItemUseCase
import com.ilya.myapplication.domain.EditShopItemUseCase
import com.ilya.myapplication.domain.GetShopItemUseCase
import com.ilya.myapplication.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val shopListRepository = ShopListRepositoryImpl

    val addShopItemUseCase = AddShopItemUseCase(shopListRepository)

    val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    fun addShopItem(name: String, count: Int) {
        val shopItem = ShopItem(name, count, true)
        addShopItemUseCase.addShopItem(shopItem)
    }

    fun editShopItem(name: String, count: Int, used: Boolean, id: Int) {
        editShopItemUseCase.editShopItem(ShopItem(name, count, used, id))
    }
}
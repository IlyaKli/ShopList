package com.ilya.shoplist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.shoplist.domain.EditShopItemUseCase
import com.ilya.shoplist.domain.GetShopListUseCase
import com.ilya.shoplist.domain.RemoveShopItemUseCase
import com.ilya.shoplist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetShopListUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val removeShopItemUseCase: RemoveShopItemUseCase
) : ViewModel() {

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
package com.ilya.myapplication.presentation

import androidx.lifecycle.ViewModel
import com.ilya.myapplication.data.ShopListRepositoryImpl
import com.ilya.myapplication.domain.AddShopItemUseCase
import com.ilya.myapplication.domain.EditShopItemUseCase
import com.ilya.myapplication.domain.GetShopItemUseCase
import com.ilya.myapplication.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val shopListRepository = ShopListRepositoryImpl

    val getShopItemUseCase = GetShopItemUseCase(shopListRepository)

    val addShopItemUseCase = AddShopItemUseCase(shopListRepository)

    val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

//    fun editShopItem(name: String?, count: String?, id: Int) {
//        val name = parseName(name)
//        val count = parseCount(count)
//        val fieldsValid = validateInput(name, count)
//    }

    fun parseName(inputName: String?) : String {
        return inputName?.trim() ?: ""
    }

    fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            //TODO: show error input name
            result = false
        }
        if (count <= 0) {
            //TODO: show error input count
            result = false
        }
        return result
    }
}
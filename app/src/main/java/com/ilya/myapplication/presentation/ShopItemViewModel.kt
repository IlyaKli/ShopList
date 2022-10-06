package com.ilya.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _isLoad = MutableLiveData<Unit>()
    val isLoad: LiveData<Unit>
        get() = _isLoad

    fun getShopItem(shopItemId: Int) {
        val item: ShopItem = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
        finishWork()
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                addShopItemUseCase.addShopItem(item)
                finishWork()
            }
        }
    }

    fun editShopItem(name: String?, count: String?, id: Int) {
        val name = parseName(name)
        val count = parseCount(count)
        val fieldsValid = validateInput(name, count)
    }

    private fun parseName(inputName: String?) : String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _isLoad.value = Unit
    }
}
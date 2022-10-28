package com.ilya.shoplist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    var used: Boolean
)
package com.ilya.shoplist.presentation

import android.app.Application
import com.ilya.shoplist.data.di.DaggerApplicationComponent

class ShopApplication: Application() {

    val component by lazy { DaggerApplicationComponent.factory().create(this) }
}
package com.ilya.shoplist.data.di

import android.app.Application
import com.ilya.shoplist.data.AppDatabase
import com.ilya.shoplist.data.ShopListDao
import com.ilya.shoplist.data.ShopListRepositoryImpl
import com.ilya.shoplist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}
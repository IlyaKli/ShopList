package com.ilya.shoplist.data.di

import androidx.lifecycle.ViewModel
import com.ilya.shoplist.presentation.MainViewModel
import com.ilya.shoplist.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShoItemViewModel(viewModel: ShopItemViewModel): ViewModel
}
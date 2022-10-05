package com.ilya.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ilya.myapplication.R
import com.ilya.myapplication.domain.ShopItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val shopListAdapter by lazy { ShopListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        viewModel.shopList.observe(this) {
            shopListAdapter.setShoList(it)
        }

        shopListAdapter.shopItemLongClickListener = object : ShopListAdapter.OnShopItemLongClickListener{
            override fun onLongClick(shopItem: ShopItem) {
                viewModel.editShopList(shopItem)
            }
        }
    }

    fun setRecyclerView() {
        shopItemListRecyclerView.adapter = shopListAdapter
        shopItemListRecyclerView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED_VIEW, ShopListAdapter.MAX_POOL_SIZE)
        shopItemListRecyclerView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_VIEW, ShopListAdapter.MAX_POOL_SIZE)
    }
}
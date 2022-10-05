package com.ilya.myapplication.presentation

import android.media.browse.MediaBrowser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ilya.myapplication.R
import com.ilya.myapplication.domain.ShopItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val shopListAdapter by lazy { ShopListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    private fun setOnItemLongClickListener() {
        shopListAdapter.shopItemLongClickListener = {
            viewModel.editShopList(it)
        }
    }

    private fun setOnItemClickListener() {
        shopListAdapter.setOnShopItemClickListener = {
            Log.d("InfShopItem", it.toString())
        }
    }

    private fun setSwipedListener() {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(shopItem)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(shopItemListRecyclerView)
    }

    fun setRecyclerView() {
        shopItemListRecyclerView.adapter = shopListAdapter
        shopItemListRecyclerView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.ENABLED_VIEW,
            ShopListAdapter.MAX_POOL_SIZE)
        shopItemListRecyclerView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.DISABLED_VIEW,
            ShopListAdapter.MAX_POOL_SIZE)

        setSwipedListener()

        setOnItemClickListener()

        setOnItemLongClickListener()
    }
}
package com.ilya.myapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ilya.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val shopListAdapter by lazy { ShopListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        setOnButtonClickListener()

        observerMainVM()
    }

    private fun observerMainVM() {
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    private fun setOnButtonClickListener() {
        addShopItemButton.setOnClickListener {
            val intent = ShopItemActivity.newIntentAdd(this)
            startActivity(intent)
        }
    }

    private fun setOnItemLongClickListener() {
        shopListAdapter.shopItemLongClickListener = {
            viewModel.editShopList(it)
        }
    }

    private fun setOnItemClickListener() {
        shopListAdapter.setOnShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEdit(this, it.id)
            startActivity(intent)
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

    private fun setRecyclerView() {
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
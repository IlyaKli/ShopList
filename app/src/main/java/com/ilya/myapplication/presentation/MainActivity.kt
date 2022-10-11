package com.ilya.myapplication.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ilya.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val shopListAdapter by lazy { ShopListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        setOnButtonClickListener()

        observerMainVM()
    }

    private fun isHorizontalOrientation(): Boolean {
        return shopItemContainer != null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shopItemContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observerMainVM() {
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(
            this@MainActivity,
            "Готово",
            Toast.LENGTH_SHORT
        ).show()
        supportFragmentManager.popBackStack()
    }

    private fun setOnButtonClickListener() {
        addShopItemButton.setOnClickListener {
            if (isHorizontalOrientation()) {
                launchFragment(ShopItemFragment.newInstanceAddMode())
            } else {
                val intent = ShopItemActivity.newIntentAdd(this)
                startActivity(intent)
            }
        }
    }

    private fun setOnItemLongClickListener() {
        shopListAdapter.shopItemLongClickListener = {
            viewModel.editShopList(it)
        }
    }

    private fun setOnItemClickListener() {
        shopListAdapter.setOnShopItemClickListener = {
            if (isHorizontalOrientation()) {
                launchFragment(ShopItemFragment.newInstanceEditMode(it.id))
            } else {
                val intent = ShopItemActivity.newIntentEdit(this, it.id)
                startActivity(intent)
            }
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

    override fun onEditingFinished() {
        Toast.makeText(this, "Ready", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}
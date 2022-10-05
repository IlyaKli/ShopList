package com.ilya.myapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilya.myapplication.R
import com.ilya.myapplication.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var shopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null

    var setOnShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = when (viewType) {
            ENABLED_VIEW -> LayoutInflater
                .from(parent.context)
                .inflate(R.layout.enabled_shop_item, parent, false)
            DISABLED_VIEW -> LayoutInflater
                .from(parent.context)
                .inflate(R.layout.disabled_shop_item, parent, false)
            else -> throw RuntimeException("Unknown view type")
        }
        return ShopListViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)

        val viewType = when (shopItem.used) {
            true -> ENABLED_VIEW
            else -> DISABLED_VIEW
        }

        return viewType
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        var shopItem = getItem(position)

        holder.itemTextView.text = shopItem.name

        holder.countTextView.text = shopItem.count.toString()

        holder.itemView.setOnClickListener {
            setOnShopItemClickListener?.invoke(shopItem)
        }

        holder.itemView.setOnLongClickListener {
            shopItemLongClickListener?.invoke(shopItem)
            true
        }
    }

    companion object {
        const val ENABLED_VIEW = 1

        const val DISABLED_VIEW = 0

        const val MAX_POOL_SIZE = 10
    }
}
package com.ilya.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.ilya.shoplist.domain.ShopItem
import shoplist.R
import shoplist.databinding.DisabledShopItemBinding
import shoplist.databinding.EnabledShopItemBinding

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {

    var shopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null

    var setOnShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            ENABLED_VIEW -> R.layout.enabled_shop_item
            DISABLED_VIEW -> R.layout.disabled_shop_item
            else -> throw RuntimeException("Unknown view type")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopListViewHolder(binding)
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
        val shopItem = getItem(position)
        val binding = holder.binding

        when (binding) {
            is EnabledShopItemBinding -> {
                binding.shopItemTextView.text = shopItem.name
                binding.countShopItemTextView.text = shopItem.count.toString()
            }
            is DisabledShopItemBinding -> {
                binding.shopItemTextView.text = shopItem.name
                binding.countShopItemTextView.text = shopItem.count.toString()
            }
        }

        binding.root.setOnClickListener {
            setOnShopItemClickListener?.invoke(shopItem)
        }

        binding.root.setOnLongClickListener {
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
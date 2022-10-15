package com.ilya.myapplication.presentation

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ilya.myapplication.databinding.DisabledShopItemBinding
import com.ilya.myapplication.databinding.EnabledShopItemBinding
import kotlinx.android.synthetic.main.enabled_shop_item.view.*

class ShopListViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
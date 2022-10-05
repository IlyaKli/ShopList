package com.ilya.myapplication.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.enabled_shop_item.view.*

class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemTextView = itemView.shopItemTextView

    val countTextView = itemView.countShopItemTextView
}
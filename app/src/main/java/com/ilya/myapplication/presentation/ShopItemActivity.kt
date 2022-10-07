package com.ilya.myapplication.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilya.myapplication.R
import com.ilya.myapplication.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private var screenMode = UNKNOWN_MODE

    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        launchRightMode()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddMode()
            MODE_EDIT -> ShopItemFragment.newInstanceEditMode(shopItemId)
            else -> throw RuntimeException("Unknown screen mode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shopItemContainer, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param extra screen mode is absent")
        }

        screenMode = intent.getStringExtra(EXTRA_SCREEN_MODE).toString()

        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param extra shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"

        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"

        private const val MODE_EDIT = "mode_edit"

        private const val MODE_ADD = "mode_add"

        private const val UNKNOWN_MODE = ""

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}
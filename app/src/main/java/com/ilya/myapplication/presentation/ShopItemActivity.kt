package com.ilya.myapplication.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ilya.myapplication.R
import com.ilya.myapplication.domain.ShopItem
import kotlinx.android.synthetic.main.activity_shop_item.*

class ShopItemActivity : AppCompatActivity() {

    private val shopItemViewModel by lazy { ViewModelProvider(this)[ShopItemViewModel::class.java] }

    private var screenMode = UNKNOWN_MODE

    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        textChangedListener()
        observerOnSIViewModel()
        launchRightMode()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun textChangedListener() {
        nameTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        countTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun observerOnSIViewModel() {
        shopItemViewModel.isLoad.observe(this) {
            finish()
        }

        shopItemViewModel.errorInputName.observe(this) {
            if (it) {
                nameTIL.error = getString(R.string.error_input_name)
            } else {
                nameTIL.error = null
            }
        }

        shopItemViewModel.errorInputCount.observe(this) {
            if (it) {
                countTIL.error = getString(R.string.error_input_count)
            } else {
                countTIL.error = null
            }
        }
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

    private fun launchAddMode() {
        saveButton.setOnClickListener {
            val name = nameTextInputEditText.text.toString()
            val count = countTextInputEditText.text.toString()
            shopItemViewModel.addShopItem(name, count)
        }
    }

    private fun launchEditMode() {
        setEditInfo()
        saveButton.setOnClickListener {
            val name = nameTextInputEditText.text.toString()
            val count = countTextInputEditText.text.toString()
            shopItemViewModel.editShopItem(name, count)
        }

    }

    private fun setEditInfo() {
        shopItemViewModel.getShopItem(shopItemId)
        shopItemViewModel.shopItem.observe(this) {
            nameTextInputEditText.setText(it?.name)
            countTextInputEditText.setText(it?.count.toString())
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
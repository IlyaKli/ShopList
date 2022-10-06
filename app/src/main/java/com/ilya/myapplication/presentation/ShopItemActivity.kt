package com.ilya.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilya.myapplication.R
import kotlinx.android.synthetic.main.activity_shop_item.*

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        saveButton.setOnClickListener {

        }
    }
}
package com.ilya.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ilya.myapplication.R

class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getShopList()

        viewModel.getShopListLD().observe(this) {
            Log.d("List", it.toString())
        }
    }
}
package com.ilya.shoplist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ilya.shoplist.R

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("name")
fun bindName(textInputEditText: TextInputEditText, name: String?) {
    val message = name ?: ""
    textInputEditText.setText(message)
}
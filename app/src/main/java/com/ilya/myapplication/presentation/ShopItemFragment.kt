package com.ilya.myapplication.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ilya.myapplication.R
import com.ilya.myapplication.databinding.FragmentShopItemBinding
import com.ilya.myapplication.domain.ShopItem
import kotlinx.android.synthetic.main.fragment_shop_item.*

class ShopItemFragment : Fragment() {

    val shopItemViewModel by lazy { ViewModelProvider(this)[ShopItemViewModel::class.java] }

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    private var screenMode = UNKNOWN_MODE

    private var shopItemId = ShopItem.UNDEFINED_ID

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity doesn't implement interface OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = shopItemViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        launchRightMode()
        textChangedListener()
        observerOnSIViewModel()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun parsArgs() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Screen mode key is absent")
        } else {
            screenMode = args.getString(SCREEN_MODE).toString()
        }

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item id key is absent")
            } else {
                shopItemId = args.getInt(SHOP_ITEM_ID)
            }
        }
    }

    private fun textChangedListener() {
        binding.nameTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                shopItemViewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.countTextInputEditText.addTextChangedListener(object : TextWatcher {
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
        shopItemViewModel.isLoad.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            val name = nameTextInputEditText.text.toString()
            val count = countTextInputEditText.text.toString()
            shopItemViewModel.addShopItem(name, count)
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItem(shopItemId)
        binding.saveButton.setOnClickListener {
            val name = nameTextInputEditText.text.toString()
            val count = countTextInputEditText.text.toString()
            shopItemViewModel.editShopItem(name, count)
        }

    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {
        const val SCREEN_MODE = "screen_mode"

        const val SHOP_ITEM_ID = "shop_item_id"

        const val MODE_ADD = "mode_add"

        const val MODE_EDIT = "mode_edit"

        const val UNKNOWN_MODE = ""


        fun newInstanceAddMode(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply { putString(SCREEN_MODE, MODE_ADD) }
            }
        }

        fun newInstanceEditMode(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}
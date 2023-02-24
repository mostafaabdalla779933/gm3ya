package com.gm3ya.gm3ya.features.customalertdialog

import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.BaseFragmentDialog
import com.gm3ya.gm3ya.databinding.CustomAlertDialogBinding

class CustomAlertDialog : BaseFragmentDialog<CustomAlertDialogBinding>() {
    override fun initBinding() = CustomAlertDialogBinding.inflate(layoutInflater)

    override fun onDialogCreated() {
        observeOkButton()
    }

    private fun observeOkButton() {
        binding.btnOk.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun getTheme(): Int {
        super.getTheme()
        return R.style.DialogStyle
    }
}
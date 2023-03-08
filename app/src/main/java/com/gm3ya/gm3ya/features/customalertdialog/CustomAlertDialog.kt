package com.gm3ya.gm3ya.features.customalertdialog

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.BaseFragmentDialog
import com.gm3ya.gm3ya.databinding.CustomAlertDialogBinding

class CustomAlertDialog : BaseFragmentDialog<CustomAlertDialogBinding>() {

    val args : CustomAlertDialogArgs  by navArgs()
    override fun initBinding() = CustomAlertDialogBinding.inflate(layoutInflater)

    override fun onDialogCreated() {
        isCancelable = true
        observeOkButton()
    }

    private fun observeOkButton() {
        binding.apply {
            tvUserNumber.text = args.number.toString()
            btnOk.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "key",
                    "ok"
                )
                findNavController().popBackStack()
            }
        }
    }

    override fun getTheme(): Int {
        super.getTheme()
        return R.style.DialogStyle
    }
}
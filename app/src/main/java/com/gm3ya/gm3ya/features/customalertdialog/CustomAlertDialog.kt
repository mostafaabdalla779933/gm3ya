package com.gm3ya.gm3ya.features.customalertdialog

import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.BaseFragmentDialog
import com.gm3ya.gm3ya.databinding.CustomAlertDialogBinding

class CustomAlertDialog : BaseFragmentDialog<CustomAlertDialogBinding>() {
    override fun initBinding() = CustomAlertDialogBinding.inflate(layoutInflater)

    override fun onDialogCreated() {
        dialog?.show()
        observeOkButton()
    }

    private fun observeOkButton() {
        binding.btnOk.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigate(CustomAlertDialogDirections.actionCustomAlertDialogToClientDashboardFragment())
        }
    }
}
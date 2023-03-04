package com.gm3ya.gm3ya.features.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.BaseFragmentDialog
import com.gm3ya.gm3ya.databinding.CustomAlertDialogBinding
import com.gm3ya.gm3ya.databinding.SuccessDialogBinding


class SuccessDialog : BaseFragmentDialog<SuccessDialogBinding>() {
    override fun initBinding()=SuccessDialogBinding.inflate(layoutInflater)

    override fun onDialogCreated() {

        isCancelable = false
        binding.btnOk.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun getTheme(): Int {
        super.getTheme()
        return R.style.DialogStyle
    }

}
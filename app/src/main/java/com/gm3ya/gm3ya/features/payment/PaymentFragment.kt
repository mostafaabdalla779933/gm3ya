package com.gm3ya.gm3ya.features.payment


import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment<FragmentPaymentBinding, AnyViewModel>() {
    override fun initBinding() = FragmentPaymentBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

    }

}
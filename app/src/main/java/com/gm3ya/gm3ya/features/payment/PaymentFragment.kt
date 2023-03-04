package com.gm3ya.gm3ya.features.payment


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment<FragmentPaymentBinding, AnyViewModel>() {
    val args:PaymentFragmentArgs by navArgs()
    override fun initBinding() = FragmentPaymentBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {


        binding.apply {
            toolbarAllAccountsFragment.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            tvTotalAmount.text = args.association.amountPerMonth

            btnConfirm.setOnClickListener {
                findNavController().navigate(
                    PaymentFragmentDirections.actionPaymentFragmentToKnetFragment(
                        association = args.association,
                        month = args.month
                    )
                )
            }
        }
    }

}
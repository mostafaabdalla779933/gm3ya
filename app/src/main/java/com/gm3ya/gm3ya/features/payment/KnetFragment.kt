package com.gm3ya.gm3ya.features.payment


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.isStringEmpty
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.databinding.FragmentKnetBinding


class KnetFragment : BaseFragment<FragmentKnetBinding, AnyViewModel>() {

    private val args:KnetFragmentArgs  by navArgs()
    override fun initBinding()=FragmentKnetBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        binding.apply {
            tvTotalAmount.text = args.association.amountPerMonth
            btnConfirm.setOnClickListener {
                validate()
            }
        }
    }

    private fun validate(){
        binding.apply {
            when{
                etSelectBank.isStringEmpty() ->{
                    showErrorMsg("fill bank")
                }
                etCardNumber.isStringEmpty() ->{
                    showErrorMsg("fill card number")
                }
                else->{
                    pay()
                }
            }
        }
    }

    private fun pay(){
        val association  = args.association.copy()
        val month = args.month
        association.months?.find { e -> e?.date == month.date }?.paidMonths?.find { e -> e.userModel?.userId == FirebaseHelp.user?.userId }?.state = true
        showLoading()
        FirebaseHelp.addObject<AssociationModel>(
            association,
            FirebaseHelp.ASSOCIATION,
            association.hashed ?: "",
            {
                hideLoading()
                findNavController().navigate(KnetFragmentDirections.actionKnetFragmentToSuccessDialog())
            },
            {
                hideLoading()
                showErrorMsg(it)
            })
    }

}
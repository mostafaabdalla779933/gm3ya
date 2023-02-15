package com.gm3ya.gm3ya.features.associationsdetails


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getMonthAndYear
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentAssociationDetailForeignBinding



class AssociationDetailForeignFragment : BaseFragment<FragmentAssociationDetailForeignBinding, AnyViewModel>(){

    private val args: AssociationDetailForeignFragmentArgs by navArgs()
    override fun initBinding() = FragmentAssociationDetailForeignBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        setupView()

        binding.apply {

            tb.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            rvAssociations.adapter = args.association.months?.get(0)?.let {
                it.paidMonths?.let { paidMonths ->
                    DetailAssociationAdapter(paidMonths){
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            args.association.let {
                tb.title = it.name
                tvAssociationTotalNumber.text = it.totalAmount
                tvAssociationMonthsNumber.text = it.months?.count().toString()
                tvAssociationMembersNumber.text = "${it.users?.count()}/${it.maxSize}"
                tvAssociationIndividualAmount.text = it.amountPerMonth
                tvAssociationStartDate.text = it.startDate?.getMonthAndYear()
                tvAssociationEndDate.text = it.endDate?.getMonthAndYear()
            }
        }
    }

}
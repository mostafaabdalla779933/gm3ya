package com.gm3ya.gm3ya.features.associationsdetails


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getMonth
import com.buildingmaterials.buildingmaterials.common.getMonthAndYear
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentAssociationsDetailsBinding
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.*

class AssociationsDetailsFragment : BaseFragment<FragmentAssociationsDetailsBinding, AnyViewModel>(){
    private val args: AssociationsDetailsFragmentArgs by navArgs()


    override fun initBinding() = FragmentAssociationsDetailsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }



    override fun onFragmentCreated() {


        setNavigationButton()
        setupView()

        binding.apply {
            args.association.months?.forEach {
                tabLayout.addTab(tabLayout.newTab().setId(1).setText(it?.date?.getMonth()))
            }

            rvAssociations.adapter = args.association.months?.get(0)?.let {
                it.paidMonths?.let { paidMonths ->
                    DetailAssociationAdapter(paidMonths){
                    }
                }
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                   showErrorMsg("${tab?.id} +  ${tab?.text}")
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setupView() {
        binding.apply {
            args.association.let {
                toolbarAllAssociationsDetails.title = it.name
                tvAssociationTotalNumber.text = it.totalAmount
                tvAssociationMonthsNumber.text = it.months?.count().toString()
                tvAssociationMembersNumber.text = "${it.users?.count()}/${it.maxSize}"
                tvAssociationIndividualAmount.text = it.amountPerMonth
                tvAssociationStartDate.text = it.startDate?.getMonthAndYear()
                tvAssociationEndDate.text = it.endDate?.getMonthAndYear()
            }
        }
    }

    private fun setNavigationButton() {
        binding.toolbarAllAssociationsDetails.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


}




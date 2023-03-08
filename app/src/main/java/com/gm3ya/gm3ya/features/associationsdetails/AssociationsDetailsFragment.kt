package com.gm3ya.gm3ya.features.associationsdetails


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getMonth
import com.buildingmaterials.buildingmaterials.common.getMonthAndYear
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.NotificationModel
import com.gm3ya.gm3ya.common.firebase.data.NotificationType
import com.gm3ya.gm3ya.common.firebase.data.PaidMonthModel
import com.gm3ya.gm3ya.databinding.FragmentAssociationsDetailsBinding
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat

class AssociationsDetailsFragment : BaseFragment<FragmentAssociationsDetailsBinding, AnyViewModel>(){
    private val args: AssociationsDetailsFragmentArgs by navArgs()

    private lateinit var adapter: DetailAssociationAdapter

    override fun initBinding() = FragmentAssociationsDetailsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }



    override fun onFragmentCreated() {
        setNavigationButton()
        setupView()
        binding.apply {
            args.association.months?.forEachIndexed { index, it ->
                tabLayout.addTab(tabLayout.newTab().setId(index).setText(it?.date?.getMonth()))
            }

            args.association.months?.getOrNull(0)?.let {
                it.paidMonths?.let { paidMonths ->
                    adapter = DetailAssociationAdapter(list = paidMonths, payNow = {
                        args.association.months?.get(tabLayout.selectedTabPosition)?.let { month ->
                            findNavController().navigate(
                                AssociationsDetailsFragmentDirections.actionAssociationsDetailsFragmentToPaymentFragment(
                                    association = args.association,
                                    month =month
                                )
                            )
                        }
                    }, sendWarning = {
                        sendWarning(it)
                    })
                }
            }

            rvAssociations.adapter = adapter

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    adapter.list = args.association.months?.getOrNull(tab?.id ?: 0)?.paidMonths ?: emptyList()
                    adapter.notifyDataSetChanged()
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }


    private fun sendWarning(paidMonthModel: PaidMonthModel){
        val notificationModel = NotificationModel(
            title = "Warning, you are late paying!",
            type = NotificationType.WarningLate.value,
            from = FirebaseHelp.user,
            fromId = FirebaseHelp.user?.userId,
            hash = System.currentTimeMillis(),
            date = SimpleDateFormat("dd MMM yyyy").format(System.currentTimeMillis()),
            toUserId = paidMonthModel.userModel?.userId,
            associationModel = args.association
        )

        showLoading()
        FirebaseHelp.addObject<NotificationModel>(
            notificationModel,
            FirebaseHelp.NOTIFICATION,
            notificationModel.hash.toString(),
            {
                hideLoading()
            },
            {
                hideLoading()
                showErrorMsg(it)
            })
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




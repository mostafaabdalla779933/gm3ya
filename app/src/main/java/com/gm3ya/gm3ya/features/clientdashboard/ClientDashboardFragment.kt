package com.gm3ya.gm3ya.features.clientdashboard


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.databinding.FragmentClientDashboardBinding
import com.google.android.material.tabs.TabLayout


class ClientDashboardFragment: BaseFragment<FragmentClientDashboardBinding, AnyViewModel>() {

    private var associations: List<AssociationModel>? = null

    private val adapter: AssociationAdapter by lazy {
        AssociationAdapter { association ->
            binding.tabLayout.selectedTabPosition.let {
                when(it){
                    0 ->{
                        findNavController().navigate(ClientDashboardFragmentDirections.actionClientDashboardFragmentToAssociationDetailForeignFragment(association))
                    }
                    1 ->{
                        findNavController().navigate(ClientDashboardFragmentDirections.actionClientDashboardFragmentToAssociationsDetailsFragment(association))
                    }
                }
            }

        }
    }

    override fun initBinding()=FragmentClientDashboardBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.apply {
            ivNotification.setOnClickListener {
                findNavController().navigate(ClientDashboardFragmentDirections.actionClientDashboardFragmentToNotificationFragment())
            }

            ivAdd.setOnClickListener {
                findNavController().navigate(ClientDashboardFragmentDirections.actionClientDashboardFragmentToNewAssociationFragment())
            }
            ivLogout.setOnClickListener {
                FirebaseHelp.logout()
                requireActivity().finish()
                requireActivity().startActivity(requireActivity().intent)
            }
            binding.rvAssociations.adapter = adapter
        }
        addTabListener()
        getAllAssociations()
    }

    private fun getAllAssociations() {
        showLoading()
        FirebaseHelp.getAllObjects<AssociationModel>(FirebaseHelp.ASSOCIATION,{ allAssociations ->
            associations = allAssociations
            hideLoading()
            associations?.let {
                adapter.submitList(associations?.filter {
                        e -> e.creatorId != FirebaseHelp.getUserID()
                        && e.users?.none { e -> e.userId == FirebaseHelp.getUserID() } ?: true
                })
            }
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }

    private fun addTabListener(){
        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 ->{
                        adapter.submitList(associations?.filter {
                                e -> e.creatorId != FirebaseHelp.getUserID()
                                && e.users?.none { e -> e.userId == FirebaseHelp.getUserID() } ?: true
                        })

                    }
                    1 ->{
                        adapter.submitList(associations?.filter {
                                e -> e.creatorId == FirebaseHelp.getUserID()
                                || e.users?.none { e -> e.userId == FirebaseHelp.getUserID() }?.not() ?: false
                        })
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}
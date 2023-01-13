package com.gm3ya.gm3ya.features.admindashboard

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAdminDashboardBinding

class AdminDashboardFragment : BaseFragment<FragmentAdminDashboardBinding, AnyViewModel>() {
    override fun initBinding() = FragmentAdminDashboardBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        setNavigationButtons()
        showLoading()
        getAllAccounts()
        getAllAssociations()
        hideLoading()
    }

    private fun setNavigationButtons() {
        binding.ivAdd.isEnabled = false

        binding.accountsCard.setOnClickListener {
            findNavController().navigate(AdminDashboardFragmentDirections.actionAdminDashboardFragmentToAllAccountsFragment())
        }

        binding.associationsCard.setOnClickListener {
            findNavController().navigate(AdminDashboardFragmentDirections.actionAdminDashboardFragmentToAllAssociationsFragment())
        }

        binding.ivNotification.setOnClickListener {
            findNavController().navigate(AdminDashboardFragmentDirections.actionAdminDashboardFragmentToNotificationFragment())
        }
    }

    private fun getAllAccounts() {
        FirebaseHelp.getAllObjects<UserModel>(FirebaseHelp.USERS,{ allUsers ->
            val users = allUsers.filter { it.isAdmin == false }
            binding.tvAccountsNumber.text = users.count().toString()
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }

    private fun getAllAssociations() {
        FirebaseHelp.getAllObjects<UserModel>(FirebaseHelp.ASSOCIATION,{
            binding.tvAssociationsNumber.text = it.count().toString()
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }
}
package com.gm3ya.gm3ya.features.admindashboard

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAdminDashboardBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardFragment : BaseFragment<FragmentAdminDashboardBinding, AnyViewModel>() {
    override fun initBinding() = FragmentAdminDashboardBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        showLoading()
        getAllAccounts()
        hideLoading()
    }

    private fun getAllAccounts() {
        FirebaseHelp.getAllObjects<UserModel>(FirebaseHelp.USERS,{
            binding.tvAllAccounts.text = it.count().toString()
            getAllAssociations()
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }

    private fun getAllAssociations() {
        // Associations instead users
        FirebaseHelp.getAllObjects<UserModel>(FirebaseHelp.USERS,{
            binding.tvAllAccounts.text = it.count().toString()
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }
}
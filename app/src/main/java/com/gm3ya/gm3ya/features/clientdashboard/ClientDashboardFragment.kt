package com.gm3ya.gm3ya.features.clientdashboard


import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentClientDashboardBinding



class ClientDashboardFragment: BaseFragment<FragmentClientDashboardBinding, AnyViewModel>() {
    override fun initBinding()=FragmentClientDashboardBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

    }

}
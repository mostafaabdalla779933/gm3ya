package com.gm3ya.gm3ya.features.clientdashboard


import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentClientDashboardBinding
import com.google.android.material.tabs.TabLayout


class ClientDashboardFragment: BaseFragment<FragmentClientDashboardBinding, AnyViewModel>() {
    override fun initBinding()=FragmentClientDashboardBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        addTabListener()

    }


    private fun addTabListener(){
        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 ->{

                        showErrorMsg("all")
                    }
                    1 ->{
                        showErrorMsg("my")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}
package com.gm3ya.gm3ya.features.associationsdetails

import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentAssociationsDetailsBinding
import com.google.android.material.tabs.TabLayout

class AssociationsDetailsFragment : BaseFragment<FragmentAssociationsDetailsBinding, AnyViewModel>(){
    override fun initBinding() = FragmentAssociationsDetailsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.apply {
            list.forEach {
                tabLayout.addTab(tabLayout.newTab().setId(1).setText(it))
            }

            rvAssociations.adapter = DetailAssociationAdapter(listOf("ahmed","alaa","ahmed","alaa","ahmed","alaa")){

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
}


val list = listOf("mostafa","mina","alaa","mostafa","mina","alaa","mostafa","mina","alaa","mostafa","mina","alaa")
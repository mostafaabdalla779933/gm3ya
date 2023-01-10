package com.gm3ya.gm3ya.features.associationsdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentAllAccountsBinding
import com.gm3ya.gm3ya.databinding.FragmentAssociationsDetailsBinding

class AssociationsDetailsFragment : BaseFragment<FragmentAssociationsDetailsBinding, AnyViewModel>(){
    override fun initBinding() = FragmentAssociationsDetailsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.rvAssociations.adapter = DetailAssociationAdapter(listOf("ahmed","alaa","ahmed","alaa","ahmed","alaa")){

        }
    }
}
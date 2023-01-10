package com.gm3ya.gm3ya.features.allassociations

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentAllAccountsBinding
import com.gm3ya.gm3ya.databinding.FragmentAllAssociationsBinding

class AllAssociationsFragment : BaseFragment<FragmentAllAssociationsBinding, AnyViewModel>() {
    override fun initBinding() = FragmentAllAssociationsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {


        findNavController().navigate(AllAssociationsFragmentDirections.actionAllAssociationsFragmentToAssociationsDetailsFragment())

    }
}
package com.gm3ya.gm3ya.features.joinassociation


import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentJoinAssociationBinding

class JoinAssociationFragment : BaseFragment<FragmentJoinAssociationBinding, AnyViewModel>() {
    override fun initBinding()=FragmentJoinAssociationBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

    }

}
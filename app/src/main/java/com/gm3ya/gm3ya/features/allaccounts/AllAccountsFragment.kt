package com.gm3ya.gm3ya.features.allaccounts

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentAllAccountsBinding
import com.gm3ya.gm3ya.databinding.FragmentLoginBinding
import com.gm3ya.gm3ya.features.login.LoginFragmentDirections

class AllAccountsFragment : BaseFragment<FragmentAllAccountsBinding, AnyViewModel>(){
    override fun initBinding() = FragmentAllAccountsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

    }
}
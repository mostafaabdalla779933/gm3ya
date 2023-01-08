package com.gm3ya.gm3ya.features.login


import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentLoginBinding


class LoginFragment  : BaseFragment<FragmentLoginBinding, AnyViewModel>() {
    override fun initBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

    }

}
package com.gm3ya.gm3ya.features


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentSplashBinding


class SplashFragment  : BaseFragment<FragmentSplashBinding, AnyViewModel>() {
    override fun initBinding()=FragmentSplashBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        binding.apply {
            btnAdmin.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
            btnUser.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }

}
package com.gm3ya.gm3ya.features


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.databinding.FragmentSplashBinding


class SplashFragment  : BaseFragment<FragmentSplashBinding, AnyViewModel>() {
    override fun initBinding()=FragmentSplashBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        binding.apply {
            btnAdmin.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment(true))
            }
            btnUser.setOnClickListener {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
        check()
    }


    private fun check() {

        if (FirebaseHelp.getUserID().isNotEmpty()) {
            showLoading()
            FirebaseHelp.getUser({
                hideLoading()
                FirebaseHelp.user = it
                if (it.isAdmin == true) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAdminDashboardFragment())
                }else{
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToClientDashboardFragment())
                }
            }, {
                hideLoading()
                showErrorMsg(it)
            })
        }
    }

}
package com.gm3ya.gm3ya.features.notification


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.databinding.FragmentNotificationBinding


class NotificationFragment :  BaseFragment<FragmentNotificationBinding, AnyViewModel>() {
    override fun initBinding()=FragmentNotificationBinding.inflate(layoutInflater)
    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.tb.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
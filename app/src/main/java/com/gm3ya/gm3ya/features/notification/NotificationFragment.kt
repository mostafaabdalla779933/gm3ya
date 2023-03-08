package com.gm3ya.gm3ya.features.notification


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.NotificationModel
import com.gm3ya.gm3ya.common.firebase.data.NotificationType
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
        getData()
    }

    private fun getData() {
        showLoading()
        FirebaseHelp.getAllObjects<NotificationModel>(FirebaseHelp.NOTIFICATION, { list ->
            hideLoading()
            binding.rvNotifications.adapter = NotificationAdapter(list.filter { e -> e.toUserId == FirebaseHelp.getUserID() }.sortedBy { e -> e.hash ?: 0 }){
                when(it.type){
                    NotificationType.RequestAssociation.value ->{
                        findNavController().navigate(NotificationFragmentDirections.actionNotificationFragmentToRequestToJoinFragment(it))
                    }
                }
            }

        }, {
            hideLoading()
            showErrorMsg(it)
        })
    }

}
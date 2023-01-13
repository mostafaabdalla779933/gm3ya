package com.gm3ya.gm3ya.features.userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAllAccountsBinding
import com.gm3ya.gm3ya.databinding.FragmentUserProfileBinding

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding, AnyViewModel>(){
    private val args: UserProfileFragmentArgs by navArgs()
    var user: UserModel? = null

    override fun initBinding() = FragmentUserProfileBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        user = args.user
        setNavigationButton()
        setupView()
    }

    private fun setupView() {
        binding.apply {
            tvUsername.text = user?.userName
            tvUserIdNumber.text = user?.userId
            tvUserEmail.text = user?.email
        }
    }

    private fun setNavigationButton() {
        binding.toolbarJoinAssociationsUserProfile.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
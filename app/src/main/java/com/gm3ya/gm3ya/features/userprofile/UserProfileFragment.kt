package com.gm3ya.gm3ya.features.userprofile


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getDayMonthAndYear
import com.bumptech.glide.Glide
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
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

            Glide.with(requireContext())
                .load(args.user.profileUrl)
                .into(ivUserPicture)
            tvUsername.text = user?.userName
            tvUserIdNumber.text = user?.hash
            tvUserEmail.text = user?.email

            tvUserBirthDate.text = user?.birthDate?.getDayMonthAndYear()
            tvUserNationality.text =  user?.nationality
            //tvAddress.text =
            tvUserPhoneNumber.text = user?.phone

            btnDelete.setOnClickListener {
                val user = args.user
                user.isDeleted = true
                showLoading()
                FirebaseHelp.addObject<UserModel>(user,FirebaseHelp.USERS,user.userId ?: "",{
                    hideLoading()
                    findNavController().popBackStack()
                },{
                    hideLoading()
                    showErrorMsg(it)

                })
            }
        }
    }

    private fun setNavigationButton() {
        binding.toolbarJoinAssociationsUserProfile.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}
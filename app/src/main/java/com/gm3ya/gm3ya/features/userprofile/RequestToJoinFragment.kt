package com.gm3ya.gm3ya.features.userprofile


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getDayMonthAndYear
import com.bumptech.glide.Glide
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.common.firebase.data.NotificationModel
import com.gm3ya.gm3ya.common.firebase.data.PaidMonthModel
import com.gm3ya.gm3ya.databinding.FragmentRequestToJoinBinding

class RequestToJoinFragment  : BaseFragment<FragmentRequestToJoinBinding, AnyViewModel>(){

    val args : RequestToJoinFragmentArgs by navArgs()
    override fun initBinding()= FragmentRequestToJoinBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.apply {
            tvAssociationName.text =args.notification.associationModel?.name
            tvUserNumber.text =
                if (args.notification.isChoosePlace == true){
                    args.notification.place.toString()
                }else{
                    ((args.notification.associationModel?.users?.size ?: 0) + 1 ).toString()
                }
                args.notification.from?.apply {
                Glide.with(requireContext())
                    .load(profileUrl)
                    .into(ivUserPicture)

                tvUsername.text = userName
                tvUserIdNumber.text = hash
                tvUserEmail.text = email
                tvUserBirthDate.text = birthDate?.getDayMonthAndYear()
                tvUserNationality.text =  nationality
                //tvAddress.text =
                tvUserPhoneNumber.text = phone

            }

            btnRefuse.setOnClickListener {
                showLoading()
                deleteNotification()
            }

            btnAccept.setOnClickListener {
                addUserToAssociation()
            }

        }
    }


    fun addUserToAssociation() {
        val user = args.notification.from
        val association = args.notification.associationModel
        val notificationModel = args.notification
        if (notificationModel.isChoosePlace == true){
            user?.place = notificationModel.place
        }else{
            user?.place = (association?.users?.size ?: 0) + 1
        }
        user?.let {
            association?.users?.add(user)
            association?.months?.forEach {
                it?.paidMonths?.add(PaidMonthModel(user,false))
            }
        }
        association?.let { updateAssociation(it) }

    }

    private fun updateAssociation(associationModel: AssociationModel){
        showLoading()
        FirebaseHelp.addObject<AssociationModel>(associationModel,FirebaseHelp.ASSOCIATION,associationModel.hashed.toString(),{
            deleteNotification()
        },{

            hideLoading()
            showErrorMsg(it)
        })
    }

    private fun deleteNotification() {
        FirebaseHelp.deleteObject<NotificationModel>(
            FirebaseHelp.NOTIFICATION,
            args.notification.hash.toString(),
            {
                hideLoading()
                findNavController().popBackStack()
            },
            {
                hideLoading()
            })
    }

}
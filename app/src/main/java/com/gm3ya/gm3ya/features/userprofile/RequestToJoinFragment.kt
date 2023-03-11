package com.gm3ya.gm3ya.features.userprofile


import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getDayMonthAndYear
import com.buildingmaterials.buildingmaterials.common.showMessage
import com.bumptech.glide.Glide
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.common.firebase.data.AssociationState
import com.gm3ya.gm3ya.common.firebase.data.NotificationModel
import com.gm3ya.gm3ya.common.firebase.data.PaidMonthModel
import com.gm3ya.gm3ya.databinding.FragmentRequestToJoinBinding

class RequestToJoinFragment  : BaseFragment<FragmentRequestToJoinBinding, AnyViewModel>(){

    private val args : RequestToJoinFragmentArgs by navArgs()
    var association : AssociationModel? = null
    override fun initBinding()= FragmentRequestToJoinBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.apply {
            tvAssociationName.text =args.notification.associationModel?.name
            tvUserNumber.text =args.notification.place.toString()

            args.notification.from?.apply {
                Glide.with(requireContext())
                    .load(profileUrl)
                    .into(ivUserPicture)

                tvUsername.text = userName
                tvUserIdNumber.text = hash
                tvUserEmail.text = email
                tvUserBirthDate.text = birthDate?.getDayMonthAndYear()
                tvUserNationality.text = nationality
                tvUserAddress.text = getAddress()
                tvUserHomeAddress.text = getHomeAddress()
                tvUserPhoneNumber.text = phone

            }

            toolbarJoinAssociationsUserProfile.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnRefuse.setOnClickListener {
                showLoading()
                deleteNotification()
            }

            tvUserIdBack.setOnClickListener {
                findNavController().navigate(R.id.imageFragment, bundleOf("url" to (args.notification.from?.backUrl ?: "")))
            }

            tvUserIdFront.setOnClickListener {
                findNavController().navigate(R.id.imageFragment, bundleOf("url" to (args.notification.from?.frontUrl ?: "")))
            }

            btnAccept.setOnClickListener {
                addUserToAssociation()
            }

        }
        getAssociation()
    }


    private fun getAssociation() {
        showLoading()
        FirebaseHelp.getObject<AssociationModel>(
            FirebaseHelp.ASSOCIATION,
            args.notification.associationModel?.hashed ?: "",
            {
                hideLoading()
                association = it

            },
            {
                hideLoading()
                requireContext().showMessage(it)
                findNavController().popBackStack()
            })
    }

    private fun addUserToAssociation() {
        val user = args.notification.from
        val notificationModel = args.notification

        val size = association?.maxSize ?: 0
        val list:MutableList<Int> = (1..size).toMutableList()
        association?.users?.forEach { user ->
            user.place?.let {
                list.remove(it)
            }
        }
        if (
            association?.users?.none { e -> e.userId == user?.userId } == false
            || association?.users?.size == association?.maxSize
            || association?.state == AssociationState.Completed.value
        ) {
            requireContext().showMessage("can't add this user")
            showLoading()
            deleteNotification()
            return
        }
        if (notificationModel.choosePlace == true&& association?.users?.none { e-> e.place == notificationModel.place } == true){
            user?.place = notificationModel.place
        }else{
            user?.place = list.firstOrNull() ?: 1
        }
        user?.let {
            association?.users?.add(user)
            association?.months?.forEach {
                it?.paidMonths?.add(PaidMonthModel(user,false))
            }
            if(association?.users?.size == association?.maxSize){
                association?.state =  AssociationState.Completed.value
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
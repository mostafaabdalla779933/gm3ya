package com.gm3ya.gm3ya.features.userprofile


import android.app.AlertDialog
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
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentUserProfileBinding

class UserProfileFragment : BaseFragment<FragmentUserProfileBinding, AnyViewModel>(){
    private val args: UserProfileFragmentArgs by navArgs()
    var user: UserModel? = null

    private var associationsCreatedByUser : MutableList<AssociationModel> = mutableListOf()
    private var associationsMemberIn : MutableList<AssociationModel> = mutableListOf()
    override fun initBinding() = FragmentUserProfileBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        user = args.user
        setNavigationButton()
        setupView()
        getData()
    }

    private fun getData(){
        showLoading()
        FirebaseHelp.getAllObjects<AssociationModel>(FirebaseHelp.ASSOCIATION,{
            hideLoading()
            associationsCreatedByUser = it.filter {
                    e -> e.creatorId == user?.userId
            }.toMutableList()

            associationsMemberIn =  it.filter {
                    e -> e.users?.none { e -> e.userId == user?.userId }?.not() ?: false
            }.toMutableList()
        },{
            hideLoading()
            requireContext().showMessage(it)
            findNavController().popBackStack()
        })
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
            tvUserAddress.text = user?.getAddress()
            tvUserHomeAddress.text = user?.getHomeAddress()
            tvUserPhoneNumber.text = user?.phone

            btnDelete.setOnClickListener {
                if(associationsCreatedByUser.isEmpty()){
                    deleteUser()
                }else{
                    showDialog()
                }
            }

            tvUserIdBack.setOnClickListener {
                user?.backUrl?.let { url ->
                    findNavController().navigate(R.id.imageFragment, bundleOf("url" to url))
                }

            }

            tvUserIdFront.setOnClickListener {
                user?.frontUrl?.let { url ->
                    findNavController().navigate(R.id.imageFragment, bundleOf("url" to url))
                }

            }
        }
    }

    private fun setNavigationButton() {
        binding.toolbarJoinAssociationsUserProfile.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun deleteUser(){
        val user = args.user
        user.isDeleted = true
        showLoading()
        deleteAssociations()
        deleteUserFromAssociations()
        FirebaseHelp.addObject<UserModel>(user,FirebaseHelp.USERS,user.userId ?: "",{
            hideLoading()
            findNavController().popBackStack()
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure?\n" +
                "This Account created Association if you delete it association will be deleted")
        builder.setPositiveButton("ok") { dialog, which ->
            dialog.dismiss()
        }
        builder.setNegativeButton("cancel") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }


    fun deleteAssociations(){
        associationsCreatedByUser.forEach {
            FirebaseHelp.deleteObject<AssociationModel>(FirebaseHelp.ASSOCIATION,it.hashed?:"",{
            },{})
        }
    }

    private fun deleteUserFromAssociations(){
        associationsMemberIn.forEach {  associationModel ->
            associationModel.users?.removeAll { e-> e.userId == user?.userId }
            associationModel.months?.forEach { month ->
                month?.paidMonths?.removeAll { e-> e.userModel?.userId == user?.userId }
            }
            FirebaseHelp.addObject<AssociationModel>(
                associationModel,
                FirebaseHelp.ASSOCIATION,
                associationModel.hashed ?: "",{

                },{

                }
            )
        }
    }
}
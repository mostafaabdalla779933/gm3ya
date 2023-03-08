package com.gm3ya.gm3ya.features.joinassociation


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getString
import com.buildingmaterials.buildingmaterials.common.isStringEmpty
import com.buildingmaterials.buildingmaterials.common.setImageFromUri
import com.buildingmaterials.buildingmaterials.common.showMessage
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.base.DateFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentJoinAssociationBinding
import java.text.SimpleDateFormat
import java.util.*


class JoinAssociationFragment : BaseFragment<FragmentJoinAssociationBinding, AnyViewModel>(),
    DatePickerDialog.OnDateSetListener {

    private val args:JoinAssociationFragmentArgs by navArgs()

    var selectedProfileUri : Uri?  =null
    var selectedFrontUri : Uri?  =null
    var selectedBackUri : Uri?  =null
    var selectedFlag = ""
    var selectedDate : String? = null

    var user : UserModel? = null

    override fun initBinding()=FragmentJoinAssociationBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        user =  FirebaseHelp.user?.copy()
        binding.apply {

            tb.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            spinner.visibility = if(args.isChoosePlace) View.VISIBLE else View.GONE

            val size = args.association.maxSize ?: 0
            val list:MutableList<Int> = (1..size).toMutableList()
            args.association.users?.forEach { user ->
                user.place?.let {
                    list.remove(it)
                }
            }
            val arr = list.map { e -> e.toString() }.toMutableList()
            arr.add(0,"")
            spinner.adapter = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                arr
            )

            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")
                ?.observe(
                    viewLifecycleOwner
                ) { result ->
                    sendRequest()
                }
            ivUserPicture.setOnClickListener {
                selectedFlag = PROFILE
                chooseUserPhotoFromGallery()
            }
            ivUserIdBack.setOnClickListener {
                selectedFlag = BACK
                chooseUserPhotoFromGallery()
            }
            ivUserIdFront.setOnClickListener {
                selectedFlag = FRONT
                chooseUserPhotoFromGallery()
            }
            btnSendRequest.setOnClickListener {
                validate()
            }
            layoutChooseBirthDate.setOnClickListener {
                DateFragment(this@JoinAssociationFragment).also {
                    it.dialog?.window?.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
                }.show(parentFragmentManager, "date")
            }
            tb.title = args.association.name
        }

    }

    private fun validate(){
        binding.apply {
            when {
                args.isChoosePlace && spinner.selectedItem.toString().isEmpty() ->{
                    showErrorMsg("select number")
                }
                selectedProfileUri == null ->{
                    showErrorMsg("select profile picture")
                }
                etFullName.isStringEmpty() ->{
                    showErrorMsg("fill full name")
                }
                etIdNumber.isStringEmpty() ->{
                    showErrorMsg("fill id number")
                }
                selectedDate == null ->{
                    showErrorMsg("fill birthdate")
                }
                etNationality.isStringEmpty() ->{
                    showErrorMsg("fill nationality")
                }
                etChooseGovernorate.isStringEmpty() ->{
                    showErrorMsg("fill Governorate")
                }
                etChooseCity.isStringEmpty() ->{
                    showErrorMsg("fill city")
                }
                etChooseBlock.isStringEmpty() ->{
                    showErrorMsg("fill Block")
                }
                etChooseStreet.isStringEmpty() ->{
                    showErrorMsg("fill Street")
                }
                etChooseBuilding.isStringEmpty() ->{
                    showErrorMsg("fill Building")
                }
                etChooseRole.isStringEmpty() ->{
                    showErrorMsg("fill Role")
                }
                etChooseApartment.isStringEmpty() ->{
                    showErrorMsg("fill Apartment")
                }
                etPhoneNumber.isStringEmpty() ->{
                    showErrorMsg("fill Phone Number")
                }
//                etEmail.isStringEmpty() ->{
//                    showErrorMsg("fill Email")
//                }
                selectedFrontUri == null ->{
                    showErrorMsg("select front ID")
                }
                selectedBackUri == null  ->{
                    showErrorMsg("select back ID")
                }
                etPassword.isStringEmpty() ->{
                    showErrorMsg("fill password")
                }

                etPassword.getString() != FirebaseHelp.user?.password ->{
                    showErrorMsg("wrong password")
                }
                etPassword.getString() != etConfirmPassword.getString() ->{
                    showErrorMsg("invalid confirmation password")
                }
                else ->{
                    val number  = if(args.isChoosePlace) spinner.selectedItem.toString().toIntOrNull() else ((args.association.users?.size ?: 0) + 1)
                    findNavController().navigate(JoinAssociationFragmentDirections.actionJoinAssociationFragmentToCustomAlertDialog(number = number ?: 1))
                }
            }
        }
    }

    private fun sendRequest(){
        binding.apply {
            val intent = Intent(requireContext(), JoinAssociationService::class.java)
            user?.apply {
                profileUri = selectedProfileUri
                backUri = selectedBackUri
                frontUri = selectedFrontUri
                fullName  = etFullName.getString()
                idNumber = etIdNumber.getString()
                birthDate = selectedDate
                nationality =  etNationality.getString()
                gov = etChooseGovernorate.getString()
                city = etChooseCity.getString()
                block =  etChooseBlock.getString()
                street = etChooseStreet.getString()
                building =  etChooseBuilding.getString()
                role = etChooseRole.getString()
                apartment = etChooseApartment.getString()
                phone = etPhoneNumber.getString()
                place = spinner.selectedItem.toString().toIntOrNull()
            }
            intent.putExtra(FirebaseHelp.USERS, user)
            intent.putExtra(FirebaseHelp.ASSOCIATION, args.association)
            intent.putExtra(isChoosePlace,args.isChoosePlace)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requireContext().startForegroundService(intent)
            } else {
                requireContext().startService(intent)
            }
            requireContext().showMessage("uploading your data")

            findNavController().popBackStack()

        }
    }


    private fun chooseUserPhotoFromGallery() {
        try {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                )
            } else {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                startForGallery.launch(intent)
            }
        } catch (e: Exception) {
            showErrorMsg("something went wrong")
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var accept = false
            permissions.entries.forEach {
                accept = it.value
            }
            if (accept) {
                startForGallery.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                showErrorMsg("permission denied")
            }
        }


    private val startForGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    binding.apply {
                        when (selectedFlag) {
                            FRONT -> {
                                selectedFrontUri = uri
                                ivUserIdFront.setImageFromUri(uri, requireContext())
                            }
                            BACK -> {
                                selectedBackUri = uri
                                ivUserIdBack.setImageFromUri(uri, requireContext())
                            }
                            PROFILE -> {
                                selectedProfileUri = uri
                                ivUserPicture.setImageFromUri(uri, requireContext())
                            }
                        }
                    }

                }
            }
        }


    companion object {
        const val FRONT = "front"
        const val BACK = "back"
        const val PROFILE = "profile"
        const val isChoosePlace = "isChoosePlace"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar  = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        val dateSelected = Date(calendar.timeInMillis)
        selectedDate = dateSelected.toString()
        val date = SimpleDateFormat("dd/MM/yyyy").format(dateSelected)
        binding.etChooseDay.text = date.split("/")[0]
        binding.etChooseMonth.text = date.split("/")[1]
        binding.etChooseYear.text = date.split("/")[2]
    }

}
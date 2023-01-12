package com.gm3ya.gm3ya.features.newassociation


import android.app.DatePickerDialog
import android.text.format.DateUtils
import android.util.Log
import android.view.Window
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buildingmaterials.buildingmaterials.common.getInt
import com.buildingmaterials.buildingmaterials.common.getString
import com.buildingmaterials.buildingmaterials.common.isStringEmpty
import com.buildingmaterials.buildingmaterials.common.showMessage
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.base.DateFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.common.firebase.data.AssociationState
import com.gm3ya.gm3ya.common.firebase.data.MonthModel
import com.gm3ya.gm3ya.databinding.FragmentNewAssociationBinding
import java.util.*

class NewAssociationFragment : BaseFragment<FragmentNewAssociationBinding, AnyViewModel>() ,
    DatePickerDialog.OnDateSetListener{

    var selectedDate: Date? = null
    var selectedCalendar:Calendar?=null

    override fun initBinding()=FragmentNewAssociationBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.apply {

            tvAssociationStartDate.setOnClickListener {
                DateFragment(this@NewAssociationFragment).also {
                    it.dialog?.window?.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
                }.show(parentFragmentManager, "date")
            }

            btnCreateAssociation.setOnClickListener {
                validate()
            }
        }
    }


    private fun validate() {
        binding.apply {
            when {
                etAssociationName.isStringEmpty() -> {
                    showErrorMsg("fill Association Name")
                }
                tvAssociationStartDate.text.isNullOrEmpty() -> {
                    showErrorMsg("select start date")
                }
                etAssociationMembers.getInt() == 0 -> {
                    showErrorMsg("fill members number")
                }

                etAssociationMonthsNumber.getInt() == 0 -> {
                    showErrorMsg("fill Number of months")
                }
                etAssociationPayment.getInt() == 0 -> {
                    showErrorMsg("fill payment for one month")
                }
                else -> {
                    createAssociation()
                }
            }
        }
    }


    private fun createAssociation() {
        binding.apply {
            showLoading()
            val association = AssociationModel(
                name = etAssociationName.getString(),
                creatorId = FirebaseHelp.getUserID(),
                users = emptyList(),
                startDate = binding.tvAssociationStartDate.text.toString(),
                hashed = System.currentTimeMillis().toString(),
                maxSize = etAssociationMembers.getInt(),
                state = AssociationState.Ongoing.value,
                amountPerMonth = etAssociationPayment.getString(),
                months = (0 until etAssociationMonthsNumber.getInt()).map { e ->
                    selectedCalendar?.let {
                        it.add(Calendar.MONTH, if(e == 0)0 else 1).toString()
                        MonthModel(Date(it.timeInMillis).toString())
                    }
                },
                endDate = Date(selectedCalendar?.timeInMillis ?: 0L).toString(),
                totalAmount = (etAssociationPayment.getInt() * etAssociationMonthsNumber.getInt()).toString(),
            )


            FirebaseHelp.addObject<AssociationModel>(
                association,
                FirebaseHelp.ASSOCIATION,
                association.hashed ?: "",{
                    hideLoading()
                    requireContext().showMessage("success")
                    findNavController().popBackStack()
                },{
                    hideLoading()
                    showErrorMsg(it)
                })
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar  = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        selectedCalendar = calendar
        selectedDate = Date(calendar.timeInMillis)
        binding.tvAssociationStartDate.text = Date(calendar.timeInMillis).toString()
    }

}
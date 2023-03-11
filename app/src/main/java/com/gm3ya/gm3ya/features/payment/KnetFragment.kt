package com.gm3ya.gm3ya.features.payment


import android.app.DatePickerDialog
import android.view.Window
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.isStringEmpty
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.base.DateFragmentTo
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.databinding.FragmentKnetBinding
import java.text.SimpleDateFormat
import java.util.*


class KnetFragment : BaseFragment<FragmentKnetBinding, AnyViewModel>(), DatePickerDialog.OnDateSetListener {

    private val args:KnetFragmentArgs  by navArgs()
    var selected = false
    override fun initBinding()=FragmentKnetBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        binding.apply {
            tvTotalAmount.text = args.association.amountPerMonth
            btnConfirm.setOnClickListener {
                validate()
            }

            tvChooseMonth.setOnClickListener {
                DateFragmentTo(this@KnetFragment).also {
                    it.dialog?.window?.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
                }.show(parentFragmentManager, "date")
            }
            etYear.setOnClickListener {
                DateFragmentTo(this@KnetFragment).also {
                    it.dialog?.window?.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
                }.show(parentFragmentManager, "date")
            }
        }
    }

    private fun validate(){
        binding.apply {
            when{
                etCardNumber.isStringEmpty() ->{
                    showErrorMsg("fill card number")
                }
                selected.not() ->{
                    showErrorMsg("fill Expiration date")
                }
                else->{
                    pay()
                }
            }
        }

    }

    private fun pay(){
        val association  = args.association.copy()
        val month = args.month
        association.months?.find { e -> e?.date == month.date }?.paidMonths?.find { e -> e.userModel?.userId == FirebaseHelp.user?.userId }?.state = true
        showLoading()
        FirebaseHelp.addObject<AssociationModel>(
            association,
            FirebaseHelp.ASSOCIATION,
            association.hashed ?: "",
            {
                hideLoading()
                findNavController().navigate(KnetFragmentDirections.actionKnetFragmentToSuccessDialog())
            },
            {
                hideLoading()
                showErrorMsg(it)
            })
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selected = true
        val calendar  = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        val dateSelected = Date(calendar.timeInMillis)
        val date = SimpleDateFormat("dd/MM/yyyy").format(dateSelected)
        binding.tvChooseMonth.text = date.split("/")[1]
        binding.etYear.text = date.split("/")[2]
    }

}
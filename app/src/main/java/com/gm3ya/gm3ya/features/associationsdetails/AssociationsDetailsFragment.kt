package com.gm3ya.gm3ya.features.associationsdetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.databinding.FragmentAssociationsDetailsBinding
import com.google.android.material.tabs.TabLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class AssociationsDetailsFragment : BaseFragment<FragmentAssociationsDetailsBinding, AnyViewModel>(){
    private val args: AssociationsDetailsFragmentArgs by navArgs()
    var association: AssociationModel? = null

    override fun initBinding() = FragmentAssociationsDetailsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onFragmentCreated() {
        association = args.association

        setNavigationButton()
        setupView()
        list = getDateList(association?.startDate, association?.endDate)
        binding.apply {
            list.forEach {
                tabLayout.addTab(tabLayout.newTab().setId(1).setText(it))
            }

            rvAssociations.adapter = association!!.months?.get(0)?.let {
                it.paidMonths?.let { paidMonths ->
                    DetailAssociationAdapter(paidMonths){
                    }
                }
            }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                   showErrorMsg("${tab?.id} +  ${tab?.text}")
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setupView() {
        binding.apply {
            association?.let {
                toolbarAllAssociationsDetails.title = it.name
                tvAssociationTotalNumber.text = it.totalAmount
                tvAssociationMonthsNumber.text = it.months?.count().toString()
                tvAssociationMembersNumber.text = "${it.users?.count()}/${it.maxSize}"
                tvAssociationIndividualAmount.text = it.amountPerMonth
                tvAssociationStartDate.text = setDateToView(it.startDate)
                tvAssociationEndDate.text = setDateToView(it.endDate)
            }
        }
    }

    private fun setNavigationButton() {
        binding.toolbarAllAssociationsDetails.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setDateToView(strDate: String?): String{
        var monthString: String? = null
        var year: String? = null
        strDate?.let {
            val date = strDate.split(" ")
            monthString = date[1] // Jun
            year = date[date.size-1] // 2013
        }
        return "$monthString $year"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateList(strStartDate: String?, strEndDate: String?): List<String> {
        // Formatter for the input
        val inputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss 'GMT+02:00' yyyy", Locale.US)

        // Formatter for the output
        val outputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM")

        // Parse strings to LocalDate instances
        val startDate: LocalDate = LocalDate.parse(strStartDate, inputFormatter)
        val endDate: LocalDate = LocalDate.parse(strEndDate, inputFormatter)
        return Stream.iterate(startDate.withDayOfMonth(1)) { date -> date.plusMonths(1) }
            .limit(ChronoUnit.MONTHS.between(startDate, endDate.plusMonths(1)))
            .map { date -> date.format(outputFormatter) }
            .collect(Collectors.toList())
    }
}


var list = listOf("mostafa","mina","alaa","mostafa","mina","alaa","mostafa","mina","alaa","mostafa","mina","alaa")
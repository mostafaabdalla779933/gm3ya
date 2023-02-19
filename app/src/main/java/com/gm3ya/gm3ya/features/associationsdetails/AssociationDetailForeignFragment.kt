package com.gm3ya.gm3ya.features.associationsdetails


import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getMonthAndYear
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.data.AssociationState
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAssociationDetailForeignBinding



class AssociationDetailForeignFragment : BaseFragment<FragmentAssociationDetailForeignBinding, AnyViewModel>(){

    private val args: AssociationDetailForeignFragmentArgs by navArgs()
    override fun initBinding() = FragmentAssociationDetailForeignBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        setupView()

        binding.apply {

            tb.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            btnInstantJoin.setOnClickListener {
                findNavController().navigate(
                    AssociationDetailForeignFragmentDirections.actionAssociationDetailForeignFragmentToJoinAssociationFragment(
                        isChoosePlace = false,
                        association = args.association
                    )
                )
            }
            btnChoosePlace.setOnClickListener {
                findNavController().navigate(
                    AssociationDetailForeignFragmentDirections.actionAssociationDetailForeignFragmentToJoinAssociationFragment(
                        isChoosePlace = true,
                        association = args.association
                    )
                )
            }

            val list : MutableList<UserModel?>  =  args.association.users?.toMutableList() ?: mutableListOf()
            val space  = (args.association.maxSize ?: 0) - (args.association.users?.size ?: 0)
            repeat(space){
                list.add(null)
            }
            rvAssociations.adapter = MembersForForeignAdapter(list) {
            }

            if(args.association.state == AssociationState.Completed.value){
                btnChoosePlace.visibility = View.GONE
                btnInstantJoin.visibility = View.GONE
            }
        }
    }

    private fun setupView() {
        binding.apply {
            args.association.let {
                tb.title = it.name
                tvAssociationTotalNumber.text = it.totalAmount
                tvAssociationMonthsNumber.text = it.months?.count().toString()
                tvAssociationMembersNumber.text = "${it.users?.count()}/${it.maxSize}"
                tvAssociationIndividualAmount.text = it.amountPerMonth
                tvAssociationStartDate.text = it.startDate?.getMonthAndYear()
                tvAssociationEndDate.text = it.endDate?.getMonthAndYear()
            }
        }
    }

}
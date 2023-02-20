package com.gm3ya.gm3ya.features.associationsdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.PaidMonthModel
import com.gm3ya.gm3ya.databinding.AssociationDetailsAssociationItemBinding

class DetailAssociationAdapter(var list:List<PaidMonthModel>, val payNow:(PaidMonthModel)->Unit, val sendWarning:(PaidMonthModel)->Unit): RecyclerView.Adapter<DetailAssociationAdapter.ViewHolder>() {

    inner class ViewHolder(private val rowView: AssociationDetailsAssociationItemBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(paidMonthModel: PaidMonthModel, position: Int){
            rowView.apply {
                tvUserAssociationNumber.text = paidMonthModel.userModel?.place.toString()
                tvUsername.text = paidMonthModel.userModel?.userName
                tvUserid.text = "ID:${paidMonthModel?.userModel?.hash ?: ""}"
                tvPayNow.setOnClickListener {
                    payNow(paidMonthModel)
                }
                tvSendWarning.setOnClickListener {
                    sendWarning(paidMonthModel)
                }

                when(paidMonthModel.state){
                    true->{
                        tvPaidState.text = "Paid"
                        tvPaidState.isSelected = false
                        tvSendWarning.visibility = View.GONE
                        tvPayNow.visibility = View.GONE
                        tvPaidState.visibility = View.VISIBLE
                    }
                    else -> {
                        tvPaidState.text = "Not Paid"
                        tvPaidState.isSelected = true
                        when{
                            FirebaseHelp.user?.userId == paidMonthModel.userModel?.userId ->{
                                tvPaidState.visibility = View.GONE
                                tvSendWarning.visibility = View.GONE
                                tvPayNow.visibility = View.VISIBLE
                            }
                            FirebaseHelp.user?.isAdmin == true ->{
                                tvPaidState.visibility = View.VISIBLE
                                tvSendWarning.visibility = View.VISIBLE
                                tvPayNow.visibility = View.GONE
                            }

                            else ->{
                                tvSendWarning.visibility = View.GONE
                                tvPaidState.visibility = View.VISIBLE
                                tvPayNow.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AssociationDetailsAssociationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],position)
    }
}
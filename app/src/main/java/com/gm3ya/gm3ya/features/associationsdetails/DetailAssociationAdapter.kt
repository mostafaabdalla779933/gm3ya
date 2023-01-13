package com.gm3ya.gm3ya.features.associationsdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.common.firebase.data.PaidMonthModel
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.AssociationDetailsAssociationItemBinding


class DetailAssociationAdapter(val list:List<PaidMonthModel>, val onClick:(String)->Unit): RecyclerView.Adapter<DetailAssociationAdapter.ViewHolder>() {

    var number = 0

    inner class ViewHolder(private val rowView: AssociationDetailsAssociationItemBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(user: PaidMonthModel, position: Int){
            rowView.apply {
                number++
                tvUserAssociationNumber.text = number.toString()
                tvUsername.text = user.userName
                tvUserid.text = user.userId
//                root.setOnClickListener {
//                    onClick(str)
//                }

                when(user.state){
                    true->{
                        tvDate.visibility = View.VISIBLE
                        tvSendWarning.visibility = View.INVISIBLE
                        tvPaidState.isSelected = true
                    }
                    else -> {
                        tvSendWarning.visibility = View.VISIBLE
                        tvDate.visibility = View.INVISIBLE
                        tvPaidState.isSelected = false
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
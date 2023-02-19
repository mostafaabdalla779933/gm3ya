package com.gm3ya.gm3ya.features.associationsdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.ItemAssociationMemberForForgeinBinding


class MembersForForeignAdapter(val list:MutableList<UserModel?>, val onClick:(UserModel?)->Unit): RecyclerView.Adapter<MembersForForeignAdapter.ViewHolder>() {

    inner class ViewHolder(private val rowView: ItemAssociationMemberForForgeinBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(user: UserModel?, position: Int){
            rowView.apply {

                tvUserAssociationNumber.text = (position + 1).toString()
                if(user == null){
                    tvWaiting.visibility = View.VISIBLE
                    tvUsername.visibility = View.GONE
                    tvUserid.visibility = View.GONE

                }else{
                    tvWaiting.visibility = View.GONE
                    tvUsername.visibility = View.VISIBLE
                    tvUserid.visibility = View.VISIBLE
                }
                tvUsername.text = user?.userName
                tvUserid.text = "ID:${user?.hash ?: ""}"
                root.setOnClickListener {
                    onClick(user)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAssociationMemberForForgeinBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],position)
    }
}
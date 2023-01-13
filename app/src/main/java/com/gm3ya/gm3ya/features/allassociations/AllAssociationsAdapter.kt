package com.gm3ya.gm3ya.features.allassociations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.common.firebase.data.AssociationModel
import com.gm3ya.gm3ya.common.firebase.data.AssociationState
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.AllAccountsUsernameItemBinding
import com.gm3ya.gm3ya.databinding.AllAssociationsAssociationItemBinding

class AllAssociationsAdapter(val onClick:(AssociationModel)->Unit): ListAdapter<AssociationModel, AllAssociationsAdapter.ViewHolder>(
    UserDiffCallback()){

    inner class ViewHolder(private val rowView: AllAssociationsAssociationItemBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(association: AssociationModel, position: Int){
            rowView.apply {
                tvAssociationName.text = association.name
                tvMembersNumber.text = "${association.users?.count()}/${association.maxSize} Members"
                tvTotalAmount.text = association.totalAmount
                associationItemCard.setOnClickListener {
                    onClick(association)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AllAssociationsAssociationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class UserDiffCallback : DiffUtil.ItemCallback<AssociationModel>() {
        override fun areItemsTheSame(oldItem: AssociationModel, newItem: AssociationModel): Boolean {
            return oldItem.hashed == newItem.hashed
        }

        override fun areContentsTheSame(oldItem: AssociationModel, newItem: AssociationModel): Boolean {
            return oldItem == newItem
        }
    }
}
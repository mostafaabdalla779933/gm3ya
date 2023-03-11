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
import com.gm3ya.gm3ya.databinding.ItemAssociationBinding

class AllAssociationsAdapter(val onClick:(AssociationModel)->Unit): ListAdapter<AssociationModel, AllAssociationsAdapter.ViewHolder>(
    UserDiffCallback()){

    inner class ViewHolder(private val rowView: ItemAssociationBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(association: AssociationModel, position: Int){
            rowView.apply {
                tvName.text= association.name
                tvNumber.text = "${association.users?.count()}/${association.maxSize} Members"
                tvAmount.text = association.totalAmount
                tvState.text = association.state
                root.setOnClickListener {
                    onClick(association)
                }

                when(association.state){
                    AssociationState.Ongoing.value,AssociationState.Available.value->{
                        tvState.isActivated = true
                        tvState.isSelected = true
                    }
                    AssociationState.Completed.value ->{
                        tvState.isActivated = false
                        tvState.isSelected = false
                    }
                    AssociationState.Finished.value->{
                        tvState.isActivated = true
                        tvState.isSelected = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemAssociationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
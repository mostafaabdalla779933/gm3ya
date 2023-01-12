package com.gm3ya.gm3ya.features.allaccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.AllAccountsUsernameItemBinding

class AllAccountsAdapter(val onClick:(UserModel)->Unit): ListAdapter<UserModel, AllAccountsAdapter.ViewHolder>(
        UserDiffCallback()){

    inner class ViewHolder(private val rowView: AllAccountsUsernameItemBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(user:UserModel, position: Int){
            rowView.apply {
                tvUserName.text = user.userName
                onClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AllAccountsUsernameItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position),position)
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
}
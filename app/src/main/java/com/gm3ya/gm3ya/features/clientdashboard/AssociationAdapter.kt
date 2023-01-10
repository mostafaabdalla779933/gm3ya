package com.gm3ya.gm3ya.features.clientdashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.databinding.ItemAssociationBinding


class AssociationAdapter(val list:List<String>,val onClick:(String)->Unit): RecyclerView.Adapter<AssociationAdapter.ViewHolder>() {

    inner class ViewHolder(val rowView: ItemAssociationBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(str:String,position: Int){
            rowView.apply {

                tvName.text= str
                root.setOnClickListener {
                    onClick(str)
                }

                when(position){
                    0->{
                        tvState.isActivated = true
                        tvState.isSelected = true
                    }
                    1 ->{
                        tvState.isActivated = false
                        tvState.isSelected = false
                    }
                    2 ->{
                        tvState.isActivated = true
                        tvState.isSelected = false
                    }
                    3->{
                        tvState.isActivated = true
                        tvState.isSelected = false
                    }
                    else ->{
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

    override fun getItemCount(): Int =list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],position)

    }


}
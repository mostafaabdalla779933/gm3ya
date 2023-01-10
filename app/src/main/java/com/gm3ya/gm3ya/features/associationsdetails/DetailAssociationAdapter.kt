package com.gm3ya.gm3ya.features.associationsdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.databinding.AssociationDetailsAssociationItemBinding


class DetailAssociationAdapter(val list:List<String>,val onClick:(String)->Unit): RecyclerView.Adapter<DetailAssociationAdapter.ViewHolder>() {

    inner class ViewHolder(val rowView: AssociationDetailsAssociationItemBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(str:String,position: Int){
            rowView.apply {

                tvPaidState.text= str
                root.setOnClickListener {
                    onClick(str)
                }

                when(position){
                    0->{
                        tvPaidState.isSelected = true
                    }
                    1 ->{
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
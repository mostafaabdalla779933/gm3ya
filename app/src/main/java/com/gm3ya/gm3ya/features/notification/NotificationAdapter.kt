package com.gm3ya.gm3ya.features.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gm3ya.gm3ya.common.firebase.data.NotificationModel
import com.gm3ya.gm3ya.databinding.NotificationItemUserBinding


class NotificationAdapter(val list:List<NotificationModel>, val onClick:(NotificationModel)->Unit): RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(private val rowView: NotificationItemUserBinding): RecyclerView.ViewHolder(rowView.root){
        fun onBind(notification: NotificationModel, position: Int){
            rowView.apply {
                tvNotificationMessage.text = notification.title
                tvNotificationDate.text = notification.date
                root.setOnClickListener {
                    onClick(notification)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NotificationItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position],position)

    }
}
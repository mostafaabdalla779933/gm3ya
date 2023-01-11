package com.gm3ya.gm3ya.common.firebase.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val email:String? = "",
    val userName:String? = "",
    val password:String? ="",
    var userId :String? ="",
    var isAdmin:Boolean?=false
) : Parcelable

enum class UserState(val value:String){
    Pending("Pending"),Accepted("Accepted"),Rejected("Rejected")
}

@Parcelize
data class AssociationModel(
    val users:List<UserModel>?=null,
    val startDate:String? = null,
    val endDate:String? = null,
    val hashed:String?=null,
    val state:String?= null,
    val maxSize:Int?=null,
    val amountPerMonth:String?=null,
    val months:List<MonthModel>?=null,
    val totalAmount:String?=null
): Parcelable


enum class AssociationState(val value:String){
    Available("Available"),Completed("Completed"),Ongoing("Ongoing"),Finished("Finished")
}

@Parcelize
data class MonthModel(
    val name:String?=null,
    val paidMonths:List<PaidMonthModel>?=null
): Parcelable


@Parcelize
data class PaidMonthModel(
    val userId:String?=null,
    val userName:String?=null,
    val state:Boolean?=null
): Parcelable








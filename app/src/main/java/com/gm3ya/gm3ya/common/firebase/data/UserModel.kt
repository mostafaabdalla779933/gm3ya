package com.gm3ya.gm3ya.common.firebase.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val email:String? = "",
    val userName:String? = "",
    val password:String? ="",
    var userId :String? ="",
    var isAdmin:Boolean?=false,
    var hash:String?=null,
    var place:Int? = null,
    var profileUri: Uri?=null,
    var profileUrl:String?=null,
    var backUri:Uri?=null,
    var frontUri:Uri?= null,
    var fullName:String?="",
    var idNumber:String?="",
    var birthDate:String?="",
    var nationality:String?="",
    var gov:String?="",
    var city:String?="",
    var block:String?="",
    var street:String?="",
    var building:String?="",
    var role:String?="",
    var apartment:String?="",
    var phone:String?="",
    var isDeleted:Boolean? = false
) : Parcelable

enum class UserState(val value:String){
    Pending("Pending"),Accepted("Accepted"),Rejected("Rejected")
}

@Parcelize
data class AssociationModel(
    val name:String?=null,
    val creatorId:String?=null,
    val users:MutableList<UserModel>?=null,
    val startDate:String? = null,
    val endDate:String? = null,
    val hashed:String?=null,
    val state:String?= null,
    val maxSize:Int?=null,
    val amountPerMonth:String?=null,
    val months:MutableList<MonthModel?>?= mutableListOf(),
    val totalAmount:String?=null,
    val approved:Boolean?=false
): Parcelable


enum class AssociationState(val value:String){
    Available("Available"),Completed("Completed"),Ongoing("Ongoing"),Finished("Finished")
}

@Parcelize
data class MonthModel(
    val date:String?=null,
    val paidMonths:MutableList<PaidMonthModel>?= mutableListOf()
): Parcelable


@Parcelize
data class PaidMonthModel(
    val userModel: UserModel?=null,
    val state:Boolean?=false
): Parcelable

@Parcelize
data class NotificationModel(
    val title :String?=null,
    val hash:Long?=null ,
    val date:String?=null,
    val type:String?=null,
    val fromId:String?=null,
    var from:UserModel?=null,
    val toUserId:String?=null,
    val associationModel: AssociationModel?=null,
    val isChoosePlace:Boolean?=false,
    val place:Int?=null
) : Parcelable

enum class NotificationType(val value: String) {
    RequestAssociation("RequestAssociation"),
    NEW_IDEA("NEW_IDEA"),
    Duplicate_Idea("Duplicate_Idea"),
    Accept_Idea("Accept_Idea"),
    Refuse_Idea("Refuse_Idea"),
    GeneralMessage("GeneralMessage")
}









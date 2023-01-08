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
) : Parcelable

enum class UserState(val value:String){
    Pending("Pending"),Accepted("Accepted"),Rejected("Rejected")
}



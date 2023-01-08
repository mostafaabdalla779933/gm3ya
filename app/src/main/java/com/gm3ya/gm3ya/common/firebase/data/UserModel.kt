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
    val companyNumber:String?="",
    val userType:String? ="",
    val userState: String? = "",
    val mobile:String? ="",
    val governorate:String? ="",
    val city:String? ="",
    val block:String? ="",
    val street:String? ="",
    val building:String? ="",
    var profileUrl:String? ="",
    var licenceUrl:String? ="",
    var profileUri:Uri? =null,
    var licenceUri: Uri? =null,
    val avenues:String? = "",
    val floor:String?="",
    val flat:String?=""
) : Parcelable {




    companion object{
        const val USER ="user"
        const val CLIENT = "client"
        const val COMPANY ="COMPANY"
        const val ADMIN ="admin"
    }
}

enum class UserState(val value:String){
    Pending("Pending"),Accepted("Accepted"),Rejected("Rejected")
}



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

 //   fun getAddress()= "${governorate ?: ""} ${city?: ""} ${block?: ""} ${street?: ""} ${building?: ""} ${avenues?: ""} ${floor?: ""} ${flat?: ""}"

    fun getAddress():String{
        val str:StringBuilder = StringBuilder()
        if(governorate.isNullOrEmpty().not())
            str.append( "Governorate : $governorate \n")

        if(city.isNullOrEmpty().not())
            str.append( "City : $city \n")

        if(block.isNullOrEmpty().not())
            str.append( "Block : $block \n")

        if(street.isNullOrEmpty().not())
            str.append( "Street : $street \n")

        if(building.isNullOrEmpty().not())
            str.append( "Building : $building \n")

        if(avenues.isNullOrEmpty().not())
            str.append( "Avenues : $avenues \n")

        if(floor.isNullOrEmpty().not())
            str.append( "Floor : $floor \n")

        if(flat.isNullOrEmpty().not())
            str.append( "Flat : $flat \n")

        return str.toString().trim()
    }


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



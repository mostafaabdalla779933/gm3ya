package com.gm3ya.gm3ya.common.firebase.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ProductModel(
    val name:String?="",
    val id:String?="",
    val price:String?="",
    val userID:String?="",
    var height:String?="_",
    var width:String?="_",
    var length:String?="_",
    var weight:String?="_",
    var weightType:String?="",
    val stock:String?="",
    val companyName:String?="",
    val country:String?="",
    val quantity:String?="",
    val hashing:String?="",
    var companyProfileUrl:String?="",
    var Other:Boolean?=false,
    var IronSolid:Boolean?=false,
    var Wooden:Boolean?=false,
    var Painting:Boolean?=false,
    var SandBricksCement:Boolean?=false,
    val colors : List<String?>? = emptyList(),
    var images :List<String?>?= emptyList(),
    var uris:List<Uri>? = null
): Parcelable {

    fun getPriceKWD():String= "$price KWD"

    fun getCategories():String{
        var str = ""
        if(IronSolid == true) str += ProductCategory.ironSolid.value + "\n"
        if(SandBricksCement == true) str += ProductCategory.sandBricksCement.value + "\n"
        if(Wooden == true) str += ProductCategory.wooden.value + "\n"
        if(Painting == true) str += ProductCategory.painting.value+ "\n"
        if(Other == true) str += ProductCategory.other.value
        return str.trim()
    }

    companion object {

        const val PRODUCT ="product"
    }
}



enum class ProductCategory(val value:String){
    ironSolid("Iron Solid"),
    wooden("Wooden"),
    painting("Painting"),
    sandBricksCement("Cement, sand and stone"),
    other("other")
}

enum class ProductColors {
        Red,Black,Yellow,Blue
}

enum class WeightType{
    Kg,L
}

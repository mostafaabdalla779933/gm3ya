package com.buildingmaterials.buildingmaterials.common.firebase.data.calculate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class ResultCost(
    val height:String,
    val width:String,
    val length:String,
    val buildingType:String,
    val material:String,
    val cost:String,
    val message:String,
    val isPainting:Boolean
): Parcelable



enum class Building(val value:String){
    Brick("Brick"),
    Mosaic("Mosaic")
}
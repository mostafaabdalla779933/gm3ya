package com.gm3ya.gm3ya.common.firebase.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CartModel(
    val productModel: ProductModel?= null,
    var productNumber:Int = 1
): Parcelable

@Parcelize
data class OrderModel(
    var client: UserModel?= null,
    var clientID:String?="",
    var totalAmount:String?="",
    var date:String?="",
    var paymentType:String?="",
    var carts:List<CartModel?>?= emptyList(),
    var address :String? = ""
): Parcelable



enum class PaymentMethods {
    Knet,Cash
}

object CartsHolder{

    val cartsList :MutableList<CartModel> = mutableListOf()
}

@Parcelize
data class PaymentCard(
    val cvv:String?="",
    val cardNumber:String?="",
    val expireDate:String?="",
    val bank:String?=""
): Parcelable{

}

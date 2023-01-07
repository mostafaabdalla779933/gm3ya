package com.gm3ya.gm3ya.common.base



interface BaseView {


    fun showLoading()

    fun hideLoading()

    fun showSuccessMsg(msg: String)

    fun showErrorMsg(msg: String)
}
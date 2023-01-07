package com.gm3ya.gm3ya.common.base

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.databinding.SnackBarBinding
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView {


    open lateinit var binding: V
    open lateinit var viewModel: VM
    var snack: Snackbar? =null

    lateinit var progressDialog: ProgressDialog


    abstract fun initBinding(): V

    abstract fun initViewModel()

    abstract fun onActivityCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        binding = initBinding()
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)



        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        onActivityCreated()
    }




    override fun showLoading() {
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun showSuccessMsg(msg: String) {
        showCustomSnackBar(msg)
    }

    override fun showErrorMsg(msg: String) {
        showCustomSnackBar(msg)
    }

    fun returnError(error: String): Boolean {
        showCustomSnackBar(error)
        return false
    }

    fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboard() {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showCustomSnackBar(msg :String){
        snack = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG)
        SnackBarBinding.bind(View.inflate(this, R.layout.snack_bar, null)).let { snackBarBinding ->
            (snack?.view as ViewGroup).removeAllViews()
            (snack?.view as ViewGroup).addView(snackBarBinding.root)

            snack?.view?.setPadding(20, 10, 20, 30)
            snack?.view?.elevation = 0f
            snack?.setBackgroundTint(
                ContextCompat.getColor(
                    this,
                    android.R.color.transparent
                )
            )
            snackBarBinding.text.text = msg
            snack?.show()
        }
    }

    override fun onDestroy() {
        snack?.dismiss()
        super.onDestroy()
    }
}




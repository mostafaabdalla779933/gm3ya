package com.gm3ya.gm3ya.common.base


import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gm3ya.gm3ya.R
import com.gm3ya.gm3ya.databinding.SnackBarBinding
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel>
    : Fragment(), BaseView {




    var snack: Snackbar? =null

    private var _binding: V? = null
    val binding get() = _binding!!

    open lateinit var viewModel: VM

    open lateinit var progressDialog: ProgressDialog

    abstract fun initBinding(): V

    abstract fun initViewModel()

    abstract fun onFragmentCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreate(savedInstanceState)


        initViewModel()
        _binding = initBinding()

        if (context != null) progressDialog = ProgressDialog(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreated()
    }

    override fun showLoading() {
        progressDialog.show()
    }

    override fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onDestroyView() {
        hideLoading()
        snack?.dismiss()
        _binding = null
        super.onDestroyView()
    }

    fun returnError(error: String): Boolean {
        showErrorMsg(error)
        return false
    }

    override fun showSuccessMsg(msg: String) {
        if (context != null)
            showCustomSnackBar(msg)
    }

    override fun showErrorMsg(msg: String) {
        if (context != null)
            showCustomSnackBar(msg)
    }



    private fun showCustomSnackBar(msg :String){
        snack = Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG)
        SnackBarBinding.bind(View.inflate(requireContext(), R.layout.snack_bar, null)).let { snackBarBinding ->
            (snack?.view as ViewGroup).removeAllViews()
            (snack?.view as ViewGroup).addView(snackBarBinding.root)

            snack?.view?.setPadding(20, 10, 20, 30)
            snack?.view?.elevation = 0f
            snack?.setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.transparent
                )
            )
            snackBarBinding.text.text = msg
            snack?.show()
        }
    }



    fun handleCustomBack(backAction: () -> Unit) {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                if (isAdded) backAction.invoke()
                else requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,  // LifecycleOwner
            callback
        )

    }

}
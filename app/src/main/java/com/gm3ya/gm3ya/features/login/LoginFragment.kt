package com.gm3ya.gm3ya.features.login


import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buildingmaterials.buildingmaterials.common.getString
import com.buildingmaterials.buildingmaterials.common.isStringEmpty
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentLoginBinding


class LoginFragment  : BaseFragment<FragmentLoginBinding, AnyViewModel>() {

    val args:LoginFragmentArgs by navArgs()
    override fun initBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.btnSignin.setOnClickListener {
            validate()
        }
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }

        if (args.isAdmin){
            binding.btnSignup.visibility = View.GONE
        }

    }


    private fun validate(){
        if (binding.etEmail.isStringEmpty()){
            showErrorMsg("fill mail")
        }else if(binding.etPass.isStringEmpty()){
            showErrorMsg("fill password")
        }else{
            signIn()
        }
    }


    private fun signIn(){
        showLoading()
        FirebaseHelp.auth
            .signInWithEmailAndPassword(binding.etEmail.getString(), binding.etPass.getString())
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    hideLoading()
                    checkUser()
                } else {
                    hideLoading()
                    showErrorMsg(task.exception?.localizedMessage ?:  "something wrong")
                }
            }
    }


    private fun checkUser(){
        FirebaseHelp.getObject<UserModel>(FirebaseHelp.USERS,FirebaseHelp.getUserID(),{
            if(it.isAdmin == true){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAdminDashboardFragment())
            }else{
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToClientDashboardFragment())
            }
        },{
            showErrorMsg(it)
        })
    }


}
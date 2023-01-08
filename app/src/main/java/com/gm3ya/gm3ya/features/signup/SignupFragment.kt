package com.gm3ya.gm3ya.features.signup


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buildingmaterials.buildingmaterials.common.getString
import com.buildingmaterials.buildingmaterials.common.isStringEmpty
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentSignupBinding


class SignupFragment  : BaseFragment<FragmentSignupBinding, AnyViewModel>() {
    override fun initBinding()=FragmentSignupBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {

        binding.btnSignup.setOnClickListener {
            validate()
        }

        binding.tvHaveAcc.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validate() {
        binding.apply {
            when {
                etUserName.isStringEmpty() -> {
                    showErrorMsg("fill UserName")
                }
                etEmail.isStringEmpty() -> {
                    showErrorMsg("fill email")
                }
                etPass.isStringEmpty() -> {
                    showErrorMsg("fill password")
                }
                etPassConfirm.getString() != binding.etPass.getString() -> {
                    showErrorMsg("inValid confirmation password")
                }
                else -> {
                    signup()
                }
            }
        }
    }

    private fun signup() {
        showLoading()
        FirebaseHelp.auth.createUserWithEmailAndPassword(
            binding.etEmail.getString(),
            binding.etPass.getString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                addUser(task.result.user?.uid ?: "")
            } else {
                hideLoading()
                showErrorMsg(task.exception?.localizedMessage ?: "something wrong")
            }
        }
    }

    private fun addUser(id: String) {
        binding.apply {
            val user = UserModel(
                userName = etUserName.getString(),
                email = etEmail.getString(),
                userId = id,
                password = etPass.getString()
            )
            FirebaseHelp.addObject<UserModel>(
                user,
                FirebaseHelp.USERS,
                id, {
                    hideLoading()
                    showSuccessMsg("success")
                    FirebaseHelp.logout()
                    findNavController().popBackStack()

                }, { str ->
                    showErrorMsg(str)
                }
            )
        }
    }

}
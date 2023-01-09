package com.gm3ya.gm3ya.features.admindashboard

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAdminDashboardBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardFragment : BaseFragment<FragmentAdminDashboardBinding, AnyViewModel>() {
    override fun initBinding() = FragmentAdminDashboardBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        showLoading()
        getAllAccounts()
    }

    private fun getAllAccounts() {
        FirebaseHelp.getAllObjects<UserModel>(FirebaseHelp.USERS,{

        },{
            
        })
        FirebaseFirestore.getInstance().collection("all_users").get()
            .addOnCompleteListener { task ->

                Log.e(this.javaClass.simpleName, task.result.documents.toString())
                hideLoading()

                if (task.isSuccessful) {
                    val usersList: ArrayList<UserModel> = ArrayList()
                    for (i in task.result.documents) {
                        val user = i.toObject(UserModel::class.java)
                        //                    if(user.isAdmin) {
                        //                        continue
                        //                    }
                        user?.let {
                            usersList.add(it)
                        }
                    }
                } else {
                    showErrorMsg(task.exception?.localizedMessage ?:  "something wrong")
                }
            }
    }
}
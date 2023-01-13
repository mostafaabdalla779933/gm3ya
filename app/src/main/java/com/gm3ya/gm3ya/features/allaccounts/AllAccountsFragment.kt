package com.gm3ya.gm3ya.features.allaccounts

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gm3ya.gm3ya.common.base.AnyViewModel
import com.gm3ya.gm3ya.common.base.BaseFragment
import com.gm3ya.gm3ya.common.firebase.FirebaseHelp
import com.gm3ya.gm3ya.common.firebase.data.UserModel
import com.gm3ya.gm3ya.databinding.FragmentAllAccountsBinding

class AllAccountsFragment : BaseFragment<FragmentAllAccountsBinding, AnyViewModel>(){
    private var users: List<UserModel>? = null

    private val adapter: AllAccountsAdapter by lazy {
        AllAccountsAdapter { user ->
            findNavController().navigate(AllAccountsFragmentDirections.actionAllAccountsFragmentToUserProfileFragment(user))
        }
    }

    override fun initBinding() = FragmentAllAccountsBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
        setNavigationButton()
        binding.rvUsernames.adapter = adapter
        showLoading()
        getAllAccounts()
        binding.etSearchUsername.addTextChangedListener(textWatcher)
    }

    private fun setNavigationButton() {
        binding.toolbarAllAccountsFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getAllAccounts() {
        FirebaseHelp.getAllObjects<UserModel>(FirebaseHelp.USERS,{ allUsers ->
            users = allUsers.filter { it.isAdmin == false }
            hideLoading()
            users?.let {
                adapter.submitList(users)
            }
        },{
            hideLoading()
            showErrorMsg(it)
        })
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            Log.d("onTextChanged", binding.etSearchUsername.text.toString())
            if (binding.etSearchUsername.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                val searchedUsers = users?.filter { it.userName!!.lowercase().contains(binding.etSearchUsername.text.toString().lowercase()) }
                adapter.submitList(searchedUsers)
            } else {
                adapter.submitList(users)
                binding.rvUsernames.layoutManager?.scrollToPosition(0)
            }
        }
        override fun afterTextChanged(s: Editable) {}
    }
}